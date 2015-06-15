/**
 *
 */
package com.kidsmind.cartoon.entity;


/**
 * 支付 接口响应结果封装基础类
 *
 * @author Roseox Hu
 */
public class ProtocolResponse {

    private int code; // 错误类型
    private String message; // 错误信息

    private DataInfo data; // 详细信息


    public boolean isSucess() {
        return code == 200 ? true : false;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return data.getToken();
    }

    public String getMobile() {
        return data.getMobile();
    }

    public UserInfo.VipType getVipLevel() {
        return data.getVipLevel();
    }

    public String getVipExpiresTime() {
        return data.getVipExpiresTime();
    }

    public boolean isFirstRegister() {
        return data.isFirstRegister();
    }

    public String getUsername() {
        return data.getUsername();
    }


}
