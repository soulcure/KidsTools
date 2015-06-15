package com.kidsmind.cartoon.entity;


/**
 * Created by john on 2015/5/28.
 */
public class UserInfo {
    private VipType vipLevel;    //用户等级 device=匿名,register=注册,paid=付费
    private String vipExpiresTime;  //Vip过期时间  yyyy-MM-dd HH:mm:ss


    public VipType getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(VipType vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getVipExpiresTime() {
        return vipExpiresTime;
    }

    public void setVipExpiresTime(String vipExpiresTime) {
        this.vipExpiresTime = vipExpiresTime;
    }

    public enum VipType {
        device, register, paid
    }
}
