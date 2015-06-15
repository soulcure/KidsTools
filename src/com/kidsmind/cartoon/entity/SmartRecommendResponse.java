/**
 *
 */
package com.kidsmind.cartoon.entity;


/**
 * 支付 接口响应结果封装基础类
 *
 * @author Roseox Hu
 */
public class SmartRecommendResponse {

    public int code; // 错误类型
    public String message; // 错误信息

    public RecommendInfo data; // 详细信息


    public boolean isSucess() {
        return code == 200 ? true : false;
    }

    public RecommendInfo getData() {
        return data;
    }


}
