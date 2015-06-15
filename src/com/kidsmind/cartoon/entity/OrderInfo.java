package com.kidsmind.cartoon.entity;

/**
 * Created by colin on 2015/5/21.
 */
public class OrderInfo {

    private boolean tradeResult; // 交易状态结果
    private double amount; // 交易猫币数量, 单位：元, 精确到分
    private String vipName; // 购买的套餐名称（可选）


    public boolean isTradeResult() {
        return tradeResult;
    }

    public double getAmount() {
        return amount;
    }

    public String getVipName() {
        return vipName;
    }
}
