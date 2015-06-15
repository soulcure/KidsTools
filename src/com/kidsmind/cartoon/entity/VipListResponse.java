package com.kidsmind.cartoon.entity;

/**
 * Created by john on 2015/5/29.
 */
public class VipListResponse {

    private int code; // 错误类型
    private String message; // 错误信息

    private VipListInfo data; // 详细信息


    public boolean isSucess() {
        return code == 200 ? true : false;
    }


    public String getMessage() {
        return message;
    }

    public VipListInfo getData() {
        return data;
    }
}
