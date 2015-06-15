package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.uitls.GsonUtil;

/**
 * Created by colin on 2015/5/25.
 */
public class SmartRecommendRequest extends ProtocolRequest {


    private String token;
    private int count; //返回记录数，默认3


    private int profileId;   //


    public void setToken(String token) {
        this.token = token;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    @Override
    public String toJsonString() {
        return GsonUtil.format(this);
    }



}
