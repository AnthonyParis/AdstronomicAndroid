package com.adstronomic.sdk.modal

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
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


@Suppress("unused")
class AdstronomicAdsBanner(private val context: Context) {

    enum class BannerAnchor {
        BOTTOM, TOP
    }

    fun loadAd(): AdstronomicAdsBanner {
        configureAds(GenerateJson.BANNER_JSON)
        return this
    }

    fun isAdLoaded(): Boolean {
        return isAdLoaded
    }

    fun setAnchor(bAnchor: BannerAnchor) {
        anchor = bAnchor;
    }

    private fun configureAds(jsonResponse: String) {
        val modalArrayList = ArrayList<AdModel>()

        try {
            val rootObject = JSONObject(jsonResponse)
            val array = rootObject.optJSONArray("apps")

            for (childObject in 0 until array.length()) {
                val jsonObject = array.getJSONObject(childObject)

                if (jsonObject.optString("app_adType") == "banner") {
                    val bannerModal = AdModel()
                    bannerModal.fileUrl = jsonObject.optString("app_fileURL")
                    bannerModal.packageOrUrl = jsonObject.optString("app_uri")
                    bannerModal.adId = jsonObject.optString("app_ad_id")
                    bannerModal.appId = jsonObject.optString("app_id")
                    modalArrayList.add(bannerModal)
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

            Picasso.get().load(modal.fileUrl).into(picassoTarget);
            packageName = modal.packageOrUrl
            adId = modal.adId
        }
    }

    fun show(fragmentManager: FragmentManager) {
        val ft: FragmentTransaction = fragmentManager.beginTransaction()
        val prev: Fragment? = fragmentManager.findFragmentByTag("adDialog")
        if (prev != null) {
            ft.remove(prev)
        }

        // save transaction to the back stack
        ft.addToBackStack("adDialog")

        val fragment: DialogFragment = BannerActivity()
        fragment.show(ft, "adDialog")

        fragmentManager.executePendingTransactions();
    }

    class BannerActivity() : DialogFragment() {

        private fun sendToBackendImpression() {
            val url = Adstronomic.API_URL + "MetricsService/BannerImpressionEvent"

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

        override fun onCreate(savedInstanceState: Bundle?)
        {
            super.onCreate(savedInstanceState)

            val style = DialogFragment.STYLE_NORMAL
            val theme = R.style.TransparentOverlay
            setStyle(style, theme)
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            dialog!!.window!!.decorView.setOnTouchListener { v, event ->
                requireActivity().dispatchTouchEvent(event)
                false
            }

            val view = inflater!!.inflate(R.layout.house_ads_banner_layout, container, false)

            val imageView = view.findViewById<ImageView>(R.id.image)

            if (anchor == BannerAnchor.TOP) {
                val params: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
                params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
                imageView.layoutParams = params
            }

            imageView.setImageBitmap(bitmap)
            sendToBackendImpression()

            imageView.setOnClickListener {
                if (packageName!!.hasHttpSign) {
                    val packageIntent = Intent(Intent.ACTION_VIEW, Uri.parse(packageName))
                    packageIntent.setPackage("com.android.chrome")
                    if (packageIntent.resolveActivity(requireContext().packageManager) != null)
                        startActivity(packageIntent)
                    else
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(packageName)))

                    sendToBackendClick()
                    mAdListener?.onApplicationLeft()
                }
                else {
                    try {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName!!)))
                        sendToBackendClick()
                        mAdListener?.onApplicationLeft()
                    } catch (e: ActivityNotFoundException) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName!!)))
                        sendToBackendClick()
                        mAdListener?.onApplicationLeft()
                    }
                }
            }

            return view
        }
    }

    private companion object {
        private var anchor: BannerAnchor = BannerAnchor.BOTTOM
        private var lastLoaded = 0
        private var mAdListener: AdListener? = null
        private var isAdLoaded = false
        private var bitmap: Bitmap? = null
        private var adId: String? = null
        private var packageName: String? = null
        private val TAG = AdstronomicAdsBanner::class.java.simpleName.toString()

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
