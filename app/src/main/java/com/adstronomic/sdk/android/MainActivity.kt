package com.adstronomic.sdk.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Button
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    private lateinit var bannerAdImage: ImageView
    private lateinit var bannerAdButton: Button

    private lateinit var interstitialAdVideo: VideoView
    private lateinit var interstitialAdButton: Button

    private lateinit var rewardedAdVideo: VideoView
    private lateinit var rewardedAdButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Adstronomic.init(this, "01234567-89AB-CDEF-0123-456789ABCDEF")

        bannerAdImage = findViewById(R.id.bannerAdImage)
        bannerAdButton = findViewById(R.id.bannerAdButton)

        bannerAdButton.setOnClickListener {
            Adstronomic.loadBannerIntoImageView(this, bannerAdImage)
        }

        interstitialAdVideo = findViewById(R.id.interstitialAdVideo)
        interstitialAdButton = findViewById(R.id.interstitialAdButton)

        interstitialAdButton.setOnClickListener {
            interstitialAdVideo.visibility = View.VISIBLE

            Adstronomic.loadInterstitialIntoVideoView(this, interstitialAdVideo)
        }

        rewardedAdVideo = findViewById(R.id.interstitialAdVideo)
        rewardedAdButton = findViewById(R.id.interstitialAdButton)

        rewardedAdButton.setOnClickListener {
            rewardedAdButton.visibility = View.VISIBLE

            Adstronomic.loadRewardedIntoVideoView(this, rewardedAdVideo)
        }

    }
}