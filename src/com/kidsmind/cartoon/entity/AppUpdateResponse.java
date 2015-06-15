/**
 *
 */
package com.kidsmind.cartoon.entity;


import com.kidsmind.cartoon.KidsMindApplication;

public class AppUpdateResponse {

    public int code; // 错误类型
    public String message; // 错误信息

    public AppUpdateInfo data; // 详细信息

    public boolean isSucess() {
        return code == 200 ? true : false;
    }


    public String getMessage() {
        return message;
    }


    public String getUpgradeUrl() {
        return data.getUpgradeUrl();
    }

    public String getDescription() {
        return data.getDescription();
    }

    public String getAppVersion() {
        return data.getAppVersion();
    }

    public KidsMindApplication.Severity getSeverity() {
        return data.getSeverity();
    }

}
