package com.adstronomic.sdk.android.helper;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


public class GenerateJson {

    public static String BANNER_JSON = "";
    public static String INTERSTITIAL_JSON = "";
    public static String REWARDED_JSON = "";

    @SuppressWarnings("unchecked")
    public static void generateInterstitialJson(String appId, String storeURL, String id, String fileURL) {
        JSONObject obj = new JSONObject();
        JSONArray ads = new JSONArray();

        JSONObject interstitial = new JSONObject();
        interstitial.put("app_ad_id", id);
        interstitial.put("app_fileURL", fileURL);
        interstitial.put("app_uri", storeURL);
        interstitial.put("app_id", appId);
        interstitial.put("app_adType", "interstitial");


        ads.add(interstitial);

        obj.put("apps", ads);

        INTERSTITIAL_JSON = obj.toJSONString();
    }

    @SuppressWarnings("unchecked")
    public static void generateRewardedJson(String appId, String storeURL, String id, String fileURL) {
        JSONObject obj = new JSONObject();
        JSONArray ads = new JSONArray();

        JSONObject rewarded = new JSONObject();
        rewarded.put("app_ad_id", id);
        rewarded.put("app_fileURL", fileURL);
        rewarded.put("app_uri", storeURL);
        rewarded.put("app_id", appId);
        rewarded.put("app_adType", "rewarded");

        ads.add(rewarded);

        obj.put("apps", ads);

        REWARDED_JSON = obj.toJSONString();
    }

    @SuppressWarnings("unchecked")
    public static void generateBannerJson(String appId, String storeURL, String id, String fileURL) {
        JSONObject obj = new JSONObject();

        JSONArray ads = new JSONArray();

        JSONObject banner = new JSONObject();
        banner.put("app_ad_id", id);
        banner.put("app_fileURL", fileURL);
        banner.put("app_uri", storeURL);
        banner.put("app_id", appId);
        banner.put("app_adType", "banner");

        ads.add(banner);

        obj.put("apps", ads);

        BANNER_JSON = obj.toJSONString();
    }
}
