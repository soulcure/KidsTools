package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.uitls.GsonUtil;

/**
 * Created by colin on 2015/5/25.
 */
public class SmsCodeRequest extends ProtocolRequest {


    private String mobile;
    private SmsParm usefor;


    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setUsefor(SmsParm usefor) {
        this.usefor = usefor;
    }


    @Override
    public String toJsonString() {
        return GsonUtil.format(this);
    }

    public enum SmsParm {
        login, bind
    }

}
