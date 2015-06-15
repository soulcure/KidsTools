package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.uitls.GsonUtil;

/**
 * Created by colin on 2015/5/25.
 */
public class MobileBindRequest extends ProtocolRequest {


    private String token;
    private String mobile;   //手机号码
    private String validateCode; //短信验证码

    public void setToken(String token) {
        this.token = token;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }


    @Override
    public String toJsonString() {
        return GsonUtil.format(this);
    }
}
