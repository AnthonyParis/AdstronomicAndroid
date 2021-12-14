package com.adstronomic.sdk.android.models;

public class AdObject {
    // App
    private String appId;
    private String storeURL;

    // Ad
    private String id;
    private String fileURL;

    public AdObject(String appId, String storeURL, String id, String fileURL) {
        this.appId = appId;
        this.storeURL = storeURL;
        this.id = id;
        this.fileURL = fileURL;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getStoreURL() {
        return storeURL;
    }

    public void setStoreURL(String storeURL) {
        this.storeURL = storeURL;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
