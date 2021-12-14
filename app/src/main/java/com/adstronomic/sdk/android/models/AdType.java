package com.adstronomic.sdk.android.models;

public enum AdType {
    Banner("BannerID"),
    Interstitial("InterstitialID"),
    Rewarded("RewardedID");

    private String idName;

    AdType(String idName) {
        this.idName = idName;
    }

    public String getIdName() {
        return idName;
    }
}