/**
 *
 */
package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.uitls.GsonUtil;

/**
 * 支付请求参数封装类
 */
public class PayRequest extends  ProtocolRequest{

    // 必传参数
    private String token; // 用户标识

    private double price; // 充值金额, 单位：元, 支付宝对price的参数规定精确到小数点后两位
    // 可选参数
    private String vipGuid; // 会员套餐标识

    private String appId;//   = WeChatPayment.WX_APP_ID; // 微信开放平台APP 唯一凭证
    
    private String outTradeNo; // 交易号


    public void setToken(String token) {
        this.token = token;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setVipGuid(String vipGuid) {
        this.vipGuid = vipGuid;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public void setOutTradeNo(String outTradeNo) {
    	this.outTradeNo = outTradeNo;
    }

    public String toJsonString() {
        return GsonUtil.format(this);
    }
}
