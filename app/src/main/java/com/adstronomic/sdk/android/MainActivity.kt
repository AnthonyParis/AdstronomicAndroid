package com.adstronomic.sdk.android

import android.media.MediaPlayer.OnPreparedListener
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

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

        Adstronomic.init(this, "wJMvF7kouz0lsO4m3d5a")

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

        rewardedAdVideo = findViewById(R.id.rewardedAdVideo)
        rewardedAdButton = findViewById(R.id.rewardedAdButton)

        rewardedAdButton.setOnClickListener {
            rewardedAdVideo.visibility = View.VISIBLE

            Adstronomic.loadRewardedIntoVideoView(this, rewardedAdVideo)
        }

    }
}