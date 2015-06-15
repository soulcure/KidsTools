package com.kidsmind.cartoon.entity;


/**
 * Created by john on 2015/5/29.
 */
public class VipListItem {

    private String vipName;
    private String vipDesc;
    private double vipPrice;
    private double listPrice;
    private String vipGuid;
    private int vipExpireHours;
    private String vipExpireDesc;
    private String vipImg;
    private String partnerProductId;


    public String getVipName() {
        return vipName;
    }

    public String getVipDesc() {
        return vipDesc;
    }

    public double getVipPrice() {
        return vipPrice;
    }

    public String getVipPriceStr() {
        if (vipPrice % 1.0 == 0) {
            return (int) vipPrice + "";
        } else {
            return vipPrice + "";
        }


    }


    public double getListPrice() {
        return listPrice;
    }

    public String getVipGuid() {
        return vipGuid;
    }

    public int getVipExpireHours() {
        return vipExpireHours;
    }

    public String getVipExpireDesc() {
        return vipExpireDesc;
    }

    public String getVipImg() {
        return vipImg;
    }

    public String getPartnerProductId() {
        return partnerProductId;
    }
}
