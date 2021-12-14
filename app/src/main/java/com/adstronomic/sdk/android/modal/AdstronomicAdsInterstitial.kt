package com.adstronomic.sdk.modal

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.adstronomic.sdk.android.Adstronomic
import com.adstronomic.sdk.android.R
import com.adstronomic.sdk.extension.hasHttpSign
import com.adstronomic.sdk.android.helper.GenerateJson
import com.adstronomic.sdk.listener.AdListener
import com.adstronomic.sdk.models.AdModel
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class AdstronomicAdsInterstitial(private val context: Context) {

    fun setAdListener(adListener: AdListener): AdstronomicAdsInterstitial {
        mAdListener = adListener
        return this
    }

    fun loadAd(): AdstronomicAdsInterstitial {
        configureAds(GenerateJson.INTERSTITIAL_JSON)
        return this
    }

    fun isAdLoaded(): Boolean {
        return isAdLoaded
    }

    private fun configureAds(jsonResponse: String) {
        val modalArrayList = ArrayList<AdModel>()

        try {
            val rootObject = JSONObject(jsonResponse)
            val array = rootObject.optJSONArray("apps")

            for (childObject in 0 until array.length()) {
                val jsonObject = array.getJSONObject(childObject)

                if (jsonObject.optString("app_adType") == "interstitial") {
                    val interstitialModal = AdModel()
                    interstitialModal.fileUrl = jsonObject.optString("app_fileURL")
                    interstitialModal.packageOrUrl = jsonObject.optString("app_uri")
                    interstitialModal.adId = jsonObject.optString("app_ad_id")
                    interstitialModal.appId = jsonObject.optString("app_id")
                    modalArrayList.add(interstitialModal)
                }
            }

        } catch (jsonException: JSONException) {
            jsonException.printStackTrace()
        }

        val modal = modalArrayList[lastLoaded]
        if (modalArrayList.size > 0) {
            if (lastLoaded == modalArrayList.size - 1) lastLoaded = 0
            else lastLoaded++

            when {
                modal.fileUrl.trim().hasHttpSign -> Log.d(TAG, "ImageUrl starts with `http://`")
                else -> throw IllegalArgumentException(context.getString(R.string.error_raw_resource_interstitial_image_null))
            }

            if (modal.fileUrl.endsWith(".mp4")
                    || modal.fileUrl.endsWith(".m4a")) {
                videoUrl = modal.fileUrl
                mAdListener?.onAdLoaded()
                isAdLoaded = true
            }
            else if (modal.fileUrl.endsWith(".png")
                    || modal.fileUrl.endsWith(".jpg")
                    || modal.fileUrl.endsWith(".webp")) {
                Picasso.get().load(modal.fileUrl).into(picassoTarget);
            }
            else {
                Log.w(TAG, "Format unsupported")
            }

            packageName = modal.packageOrUrl
            adId = modal.adId
        }
    }

    fun show(): AdstronomicAdsInterstitial {
        val intent = Intent(context, InterstitialActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        context.startActivity(intent)
        if (context is AppCompatActivity) context.overridePendingTransition(0, 0)
        return this
    }

    class InterstitialActivity : Activity() {

        private fun sendToBackendImpression() {
            val url = Adstronomic.API_URL + "MetricsService/InterstitialImpressionEvent"

            val params: Map<String, String> = mapOf("campaignId" to Adstronomic.campaignId, "promoId" to adId!!)
            val jsonObj = JSONObject(params)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.PUT, url, jsonObj, { _ -> {} }) { error -> Log.e("sendToBackendImpression", error.toString()) }

            Adstronomic.queueVolley.add(jsonObjectRequest)
        }

        private fun sendToBackendClick() {
            val url = Adstronomic.API_URL + "MetricsService/ClickEvent"

            val params: Map<String, String> = mapOf("campaignId" to Adstronomic.campaignId, "promoId" to adId!!)
            val jsonObj = JSONObject(params)
            val jsonObjectRequest = JsonObjectRequest(Request.Method.PUT, url, jsonObj, { _ -> {} }) { error -> Log.e("sendToBackendClick", error.toString()) }

            Adstronomic.queueVolley.add(jsonObjectRequest)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            mAdListener?.onAdShown()

            setContentView(R.layout.house_ads_interstitial_layout)
            val videoContainer = findViewById<ConstraintLayout>(R.id.videoContainer)
            val videoView = findViewById<VideoView>(R.id.video)
            val imageView = findViewById<ImageView>(R.id.image)
            val exitButton = findViewById<ImageButton>(R.id.button_close)

            if (bitmap != null) {
                imageView.setImageBitmap(bitmap)
                imageView.visibility = View.VISIBLE
                sendToBackendImpression()

                imageView.setOnClickListener {
                    if (packageName!!.hasHttpSign) {
                        val packageIntent = Intent(Intent.ACTION_VIEW, Uri.parse(packageName))
                        packageIntent.setPackage("com.android.chrome")

                        if (packageIntent.resolveActivity(packageManager) != null)
                            startActivity(packageIntent)
                        else
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(packageName)))

                        sendToBackendClick();
                        mAdListener?.onApplicationLeft()
                        finish()
                    }
                    else {
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName!!)))
                            sendToBackendClick();
                            mAdListener?.onApplicationLeft()
                            finish()
                        } catch (e: ActivityNotFoundException) {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName!!)))
                            sendToBackendClick()
                            mAdListener?.onApplicationLeft()
                            finish()
                        }
                    }
                }
            }
            else {
                val uri = Uri.parse(videoUrl)
                videoView.setVideoURI(uri)
                videoView.requestFocus()
                videoView.keepScreenOn = true
                videoContainer.visibility = View.VISIBLE

                videoView.setOnPreparedListener { mp ->
                    mp.isLooping = true
                    videoView.start()
                    sendToBackendImpression()
                }

                videoView.setOnClickListener {
                    if (packageName!!.hasHttpSign) {
                        val packageIntent = Intent(Intent.ACTION_VIEW, Uri.parse(packageName))
                        packageIntent.setPackage("com.android.chrome")

                        if (packageIntent.resolveActivity(packageManager) != null)
                            startActivity(packageIntent)
                        else
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(packageName)))

                        sendToBackendClick();
                        mAdListener?.onApplicationLeft()
                        finish()
                    }
                    else {
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName!!)))
                            sendToBackendClick()
                            mAdListener?.onApplicationLeft()
                            finish()
                        } catch (e: ActivityNotFoundException) {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName!!)))
                            sendToBackendClick()
                            mAdListener?.onApplicationLeft()
                            finish()
                        }
                    }
                }
            }

            exitButton.setOnClickListener {
                finish()
                isAdLoaded = false
                mAdListener?.onAdClosed()
            }
        }
    }

    private companion object {
        private var lastLoaded = 0
        private var mAdListener: AdListener? = null
        private var isAdLoaded = false
        private var bitmap: Bitmap? = null
        private var videoUrl: String? = null
        private var adId: String? = null
        private var packageName: String? = null
        private val TAG = AdstronomicAdsInterstitial::class.java.simpleName.toString()

        private val picassoTarget = object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(resource: Bitmap, from: Picasso.LoadedFrom) {
                bitmap = resource
                mAdListener?.onAdLoaded()
                isAdLoaded = true
            }

            override fun onBitmapFailed(exception: Exception, errorDrawable: Drawable?) {
                mAdListener?.onAdLoadFailed(exception)
                isAdLoaded = false
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        }
    }
}
