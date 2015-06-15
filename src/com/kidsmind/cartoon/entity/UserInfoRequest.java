package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.uitls.GsonUtil;

/**
 * Created by colin on 2015/5/25.
 */
public class UserInfoRequest extends ProtocolRequest {


    private String token;

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toJsonString() {
        return GsonUtil.format(this);
    }


}
