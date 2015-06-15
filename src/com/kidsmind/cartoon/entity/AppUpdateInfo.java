package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.KidsMindApplication;

/**
 * Created by colin on 2015/5/25.
 */
public class AppUpdateInfo {

    private String upgradeUrl;//
    private String description;//
    private String appVersion;//
    private KidsMindApplication.Severity severity; //


    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public KidsMindApplication.Severity getSeverity() {
        return severity;
    }
}
