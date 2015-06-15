package com.kidsmind.cartoon.entity;

public class GoodsInfo {
    private String subject; // 商品名称
    private String body; // 商品描述
    private String outTradeNo; // 交易号
    private double totalFee; // 交易金额, 单位：元, 精确到分
    private String notifyURL; // 异步通知URL: 相对URL，域名与接口一致


    private boolean tradeResult; // 交易状态
    private double points; // 交易金额

    /*支付宝二维码支付字段*/
    private String qrcode; // 二维码URL
    private String qrcodeImgUrl;


    /*微信二维码支付字段*/
    private String codeUrl;
    private String prepayid; // 微信开放平台接口生成预支付订单号 String
    private String nonceStr; // 32位内的随机串，防重发 String
    private Integer timestamp; // 时间戳, 整数表示 ?
    private String packageValue; // 根据文档填写的数据和签名
    private String sign; // 根据微信开放平台文档对数据做的签名
    private String accessToken; // 微信开放平台APP 的全局唯一票据 ,返回空值


    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public String getNotifyURL() {
        return notifyURL;
    }

    public boolean isTradeResult() {
        return tradeResult;
    }

    public Double getPoints() {
        return points;
    }

    public String getQrcodeURL() {
        return qrcode;
    }

    public String getQrcodeImgURL() {
        return qrcodeImgUrl;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public String getSign() {
        return sign;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
