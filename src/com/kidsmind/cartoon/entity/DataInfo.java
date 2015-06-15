package com.kidsmind.cartoon.entity;

/**
 * Created by colin on 2015/5/25.
 */
public class DataInfo {

    private String token; // 用户token
    private String mobile; // 手机号
    private UserInfo.VipType vipLevel; // 用户等级
    private String vipExpiresTime; // Vip过期时间
    private boolean firstRegister; //是否第一次注册
    private String username; //用户名


    public String getToken() {
        return token;
    }

    public String getMobile() {
        return mobile;
    }

    public UserInfo.VipType getVipLevel() {
        return vipLevel;
    }

    public String getVipExpiresTime() {
        return vipExpiresTime;
    }

    public boolean isFirstRegister() {
        return firstRegister;
    }

    public String getUsername() {
        return username;
    }
}
