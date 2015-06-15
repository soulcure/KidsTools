/**
 *
 */
package com.kidsmind.cartoon.entity;

/**
 * 支付交易状态查询接口响应结果封装基础类
 */
public class PayStatusResponse {


    private int code; // 错误类型
    private String message; // 错误信息


    private OrderInfo data;  //订单信息


    public boolean isSuccess() {
        boolean res = false;
        if (code == 200) {
            res = true;
        }
        return res;
    }


    public boolean isTradeResult() {
        return data.isTradeResult();
    }
    
    public String getVipName() {
        return data.getVipName();
    }

}
