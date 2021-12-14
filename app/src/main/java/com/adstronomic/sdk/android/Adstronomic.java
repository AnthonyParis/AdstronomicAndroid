package com.adstronomic.sdk.android;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.adstronomic.sdk.android.R;
import com.adstronomic.sdk.android.Configuration;
import com.adstronomic.sdk.android.helper.GenerateJson;
import com.adstronomic.sdk.modal.AdstronomicAdsBanner;
import com.adstronomic.sdk.modal.AdstronomicAdsInterstitial;
import com.adstronomic.sdk.modal.AdstronomicAdsRewarded;
import com.adstronomic.sdk.android.models.AdType;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response;
import com.android.volley.Request;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.Timer;
import java.util.TimerTask;

public class Adstronomic {

    public static String campaignId = "";
    public static String API_URL = "";
    public static RequestQueue queueVolley;

    public static void init(Context applicationContext, String campaignId) {
        if(Configuration.SDK_VERSION == "DEV.0.1") {
            if (Configuration.API_VERSION == "DEV.0.1") {
                System.out.println("Adstronomic Ready !");

                Adstronomic.campaignId = campaignId;
                Adstronomic.API_URL = Configuration.API;
                queueVolley = Volley.newRequestQueue(applicationContext);
            } else {
                System.out.println("Please Update API Version");
            }
        } else {
            System.out.println("Please Update SDK Version");
        }
    }

    public static boolean ifAdForThisTypeAvailable(AdType adType) {
        switch (adType) {
            case Banner: return !GenerateJson.BANNER_JSON.isEmpty();
            case Interstitial: return !GenerateJson.INTERSTITIAL_JSON.isEmpty();
            case Rewarded: return !GenerateJson.REWARDED_JSON.isEmpty();
        }
        return false;
    }

    public static void loadAndShow(AppCompatActivity activity, AdstronomicAdsBanner bannerAd) {
        Adstronomic.loadBanner(activity);

        Timer t1 = new Timer();
        t1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Adstronomic.ifAdForThisTypeAvailable(AdType.Banner)) {
                    t1.cancel();

                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            bannerAd.loadAd();

                            Timer t2 = new Timer();
                            t2.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    if (bannerAd.isAdLoaded()) {
                                        activity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                bannerAd.show(activity.getSupportFragmentManager());
                                            }
                                        });
                                        t2.cancel();
                                    }
                                }
                            }, 1000, 1000);
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

    public static void loadAndShow(AppCompatActivity activity, AdstronomicAdsRewarded ad) {
        Adstronomic.loadRewarded(activity);

        Timer t1 = new Timer();
        t1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Adstronomic.ifAdForThisTypeAvailable(AdType.Rewarded)) {
                    t1.cancel();

                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            ad.loadAd();

                            Timer t2 = new Timer();
                            t2.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    if (ad.isAdLoaded()) {
                                        activity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                ad.show();
                                            }
                                        });
                                        t2.cancel();
                                    }
                                }
                            }, 1000, 1000);
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

    public static void loadAndShow(AppCompatActivity activity, AdstronomicAdsInterstitial ad) {
        Adstronomic.loadInterstitial(activity);

        Timer t1 = new Timer();
        t1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Adstronomic.ifAdForThisTypeAvailable(AdType.Interstitial)) {
                    t1.cancel();

                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            ad.loadAd();

                            Timer t2 = new Timer();
                            t2.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                    if (ad.isAdLoaded()) {
                                        activity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                ad.show();
                                            }
                                        });
                                        t2.cancel();
                                    }
                                }
                            }, 1000, 1000);
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

    public static void loadBanner(Context context) {
        if(Adstronomic.campaignId != "") {
            String url = Adstronomic.API_URL + "AdsService/BannerAd";

            url += "?campaignId=" + Adstronomic.campaignId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String appId = response.get("appId").toString();
                                String storeURL = response.get("storeURL").toString();
                                String id = response.get("id").toString();
                                String fileURL = response.get("fileURL").toString();

                                GenerateJson.generateBannerJson(appId, storeURL, id, fileURL);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("loadBanner", error.toString());
                        }
                    });

            queueVolley.add(jsonObjectRequest);
        }
    }

    public static void loadBannerIntoImageView(Context context, ImageView myImageView) {
        if(Adstronomic.campaignId != "") {
            String url = Adstronomic.API_URL + "AdsService/BannerAd";

            url += "?campaignId=" + Adstronomic.campaignId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String appId = response.get("appId").toString();
                                String storeURL = response.get("storeURL").toString();
                                String id = response.get("id").toString();
                                String fileURL = response.get("fileURL").toString();

                                GenerateJson.generateBannerJson(appId, storeURL, id, fileURL);

                                Picasso.get().load(Adstronomic.parseURL(fileURL)).into(myImageView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("loadBanner", error.toString());
                        }
                    });

            queueVolley.add(jsonObjectRequest);
        }
    }

    public static void loadRewarded(Context context) {
        if(Adstronomic.campaignId != "") {
            String url = Adstronomic.API_URL + "AdsService/RewardedAd";

            url += "?campaignId=" + Adstronomic.campaignId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String appId = response.get("appId").toString();
                                String storeURL = response.get("storeURL").toString();
                                String id = response.get("id").toString();
                                String fileURL = response.get("fileURL").toString();

                                GenerateJson.generateRewardedJson(appId, storeURL, id, fileURL);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("loadRewarded", error.toString());
                        }
                    });

            queueVolley.add(jsonObjectRequest);
        }
    }

    public static void loadRewardedIntoVideoView(Context context, VideoView myVideoView) {
        if(Adstronomic.campaignId != "") {
            String url = Adstronomic.API_URL + "AdsService/RewardedAd";

            url += "?campaignId=" + Adstronomic.campaignId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String appId = response.get("appId").toString();
                                String storeURL = response.get("storeURL").toString();
                                String id = response.get("id").toString();
                                String fileURL = response.get("fileURL").toString();

                                GenerateJson.generateRewardedJson(appId, storeURL, id, fileURL);

                                myVideoView.setVideoPath(Adstronomic.parseURL(fileURL));
                                myVideoView.setOnPreparedListener(
                                    new MediaPlayer.OnPreparedListener() {
                                        @Override
                                        public void onPrepared(MediaPlayer myMediaPlayer) {
                                            myVideoView.start();
                                        }
                                    }
                                );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("loadRewarded", error.toString());
                        }
                    });

            queueVolley.add(jsonObjectRequest);
        }
    }


    public static void loadInterstitial(Context context) {
        if(Adstronomic.campaignId != "") {
            String url = Adstronomic.API_URL + "AdsService/InterstitialAd";

            url += "?campaignId=" + Adstronomic.campaignId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String appId = response.get("appId").toString();
                                String storeURL = response.get("storeURL").toString();
                                String id = response.get("id").toString();
                                String fileURL = response.get("fileURL").toString();

                                GenerateJson.generateInterstitialJson(appId, storeURL, id, fileURL);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("loadInterstitial", error.toString());
                        }
                    });

            queueVolley.add(jsonObjectRequest);
        }
    }

    public static void loadInterstitialIntoVideoView(Context context, VideoView myVideoView) {
        if(Adstronomic.campaignId != "") {
            String url = Adstronomic.API_URL + "AdsService/InterstitialAd";

            url += "?campaignId=" + Adstronomic.campaignId;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String appId = response.get("appId").toString();
                                String storeURL = response.get("storeURL").toString();
                                String id = response.get("id").toString();
                                String fileURL = response.get("fileURL").toString();

                                GenerateJson.generateInterstitialJson(appId, storeURL, id, fileURL);

                                myVideoView.setVideoPath(Adstronomic.parseURL(fileURL));
                                myVideoView.setOnPreparedListener(
                                        new MediaPlayer.OnPreparedListener() {
                                            @Override
                                            public void onPrepared(MediaPlayer myMediaPlayer) {
                                                myVideoView.start();
                                            }
                                        }
                                );
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("loadInterstitial", error.toString());
                        }
                    });

            queueVolley.add(jsonObjectRequest);
        }
    }

    public static void loadAdsData(AppCompatActivity activity) {
        loadInterstitial(activity);
        loadBanner(activity);
        loadRewarded(activity);
    }

    public static String parseURL(String link) {
        return link.replace(" ", "%20");
    }

}
