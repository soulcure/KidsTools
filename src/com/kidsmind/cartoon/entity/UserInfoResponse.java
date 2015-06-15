/**
 *
 */
package com.kidsmind.cartoon.entity;


/**
 * 支付交易状态查询接口响应结果封装基础类
 */
public class UserInfoResponse {


    private int code; // 错误类型
    private String message; // 错误信息


    private Object data;  //订单信息


    /**
     * 118表示需要支付
     *
     * @return
     */
    public boolean success() {
        boolean res = false;
        if (code == 200) {
            res = true;
        }
        return res;
    }

    public String getMessage() {
        return message;
    }
}
