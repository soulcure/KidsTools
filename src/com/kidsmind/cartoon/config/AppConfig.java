package com.kidsmind.cartoon.config;

import android.content.Context;

import com.kidsmind.cartoon.uitls.DeviceUtils;
import com.kidsmind.cartoon.uitls.StringUtils;

public class AppConfig {

    /*服务器协议主域名*/
    public static final String MAIN_HOST = "http://api.ikidsmind.com";
    //public static final String MAIN_HOST = "http://192.168.40.22:8280";

    public static Context AppContext;
    public static final boolean debug = true;

    public static final String AK = "asXW7E4FdwKVapULxMtickc7";
    public static final String SK = "3T1gs027Baz8kh73";

    /**
     * pref文件名定义
     */
    public static final String SHARED_PREFERENCES = "KidsMind";

    public static final String mToken = "%27%3F**%235%17p%7Bcxw%7D%0F%29%28.%22%27%2F.%0F%7Bh%7F%7E%7D%19%7Bd%7Bt%7B%60pw%7Ecxvy";
    public static final Double mPrice = 0.01;
    public static final String mVipGuid = "daad3f4a-61de-4342-97a4-88fdb4e1094b";

    public static final String mVipTitle = "40元/月";


    /* 1.1 一键注册(自动分配用户名)*/
    public static final String AUTO_LOGIN = MAIN_HOST + "/user/autoRegister.do";

    /* 1.2 用户登录（随机密码登陆）*/
    public static final String MOBILE_LOGIN = MAIN_HOST + "/user/login.do";

    /* 1.3 手机号是否已注册*/
    public static final String MOBILE_NUM_REGISTERED = MAIN_HOST + "/user/mobile.do";

    /*1.4 获取短信验证码*/
    public static final String SMS_CODE = MAIN_HOST + "/user/sms.do";

    /*1.5 绑定手机号*/
    public static final String MOBILE_BIND = MAIN_HOST + "/user/bindMobile.do";

    /*1.6 注销登录*/
    public static final String LOGIN_OUT = MAIN_HOST + "/user/logout.do";   

    /*1.7 获取profile信息列表*/
    public static final String PROFILE_LIST = MAIN_HOST + "/user/profile/list.do";

    /*1.8 创建profile*/
    public static final String CREATE_PROFILE = MAIN_HOST + "/user/profile/create.do";

    /*1.9 更新profile*/
    public static final String UPDATE_PROFILE = MAIN_HOST + "/user/profile/update.do";

    /*1.10 获取推荐内容（智能推荐）*/
    public static final String SMART_RECOMMEND = MAIN_HOST + "/user/recommendation.do";


    /*1.11 获取播放地址*/
    public static final String PLAY_URL = MAIN_HOST + "/user/play.do";


    /*1.12 剧集投票（喜欢或不喜欢）*/
    public static final String DO_YOU_LIKE = MAIN_HOST + "/user/rating.do";

    /*1.13 session/start*/
    public static final String SESSION_START = MAIN_HOST + "/user/start.do";  //暂未使用

    /*1.14 session/stop*/
    public static final String SESSION_STOP = MAIN_HOST + "/user/stop.do";   //暂未使用

    /*1.15 session/pulse*/
    public static final String SESSION_PAUSE = MAIN_HOST + "/user/pulse.do"; //暂未使用

    /*1.16 获取首页卡通人物*/
    public static final String CARTOON_ROLE_ = MAIN_HOST + "/index/interest.do";

    /*1.17 获取首页剧集列表*/
    public static final String MAIN_PLAYLIST = MAIN_HOST + "/index/series.do";

    /*1.18 获取总剧集详细内容*/
    public static final String SERIE_INFO = MAIN_HOST + "/index/serie.do";

    /*1.19 收藏剧集*/
    public static final String ADD_FAVORITE = MAIN_HOST + "/favorite/add.do";

    /*1.20 取消收藏*/
    public static final String REMOVE_FAVORITE = MAIN_HOST + "/favorite/remove.do";

    /*1.21 获取收藏列表*/
    public static final String FAVORITE_LIST = MAIN_HOST + "/favorite/list.do";

    /*1.22 获取历史播放记录*/
    public static final String WATCH_HISTORY = MAIN_HOST + "/history/watch.do";

    /*1.23 应用安装,首次运行*/
    public static final String APP_FIRST_RUN = MAIN_HOST + "/app/setup.do";

    /*1.24 应用启动 广告展示*/
    public static final String APP_LAUNCH_ADV = MAIN_HOST + "/app/launch.do";

    /*1.25 应用自升级*/
    public static final String APP_UPDATE = MAIN_HOST + "/app/upgrade.do";

    /*1.26 是否启用节目订制(收藏)*/
    public static final String FAVORITE_ENABLE = MAIN_HOST + "/favorite/enable.do"; //暂未使用

    /*1.27 获取应用配置项*/
    public static final String APP_CONFIG = MAIN_HOST + "/app/config.do";

    /*1.28 获取用户信息*/
    public static final String USER_INFO = MAIN_HOST + "/user/userinfo.do";

    /*1.29 获取历史播放记录*/
    public static final String USER_FEEDBACK = MAIN_HOST + "/user/feedback.do";

    /*1.30 获取VIP列表*/
    public static final String VIP_LIST = MAIN_HOST + "/vip/list.do";

    /*1.31 非会员心跳*/
    public static final String HEART_BEAT = MAIN_HOST + "/user/heartbeat.do";

    /*1.32 支付宝预支付*/
    public static final String PAY_ALIPAY_PREPARE = MAIN_HOST + "/payment/alipay/preparePay.do";

    /*1.33 支付宝订单查询*/
    public static final String PAY_ALIPAY_QUERY = MAIN_HOST + "/payment/alipay/tradeQuery.do";
    
    /*1.34 支付宝二维码扫描*/
    public static final String PAY_QR_ALIPAY_NOTIFY = MAIN_HOST + "/payment/alipay/QRCode.do";
    
    /*1.35 支付宝二维码扫描订单查询*/
    public static final String PAY_QR_ALIPAY_QUERY = MAIN_HOST + "/payment/alipay/tradeQRCodeQuery.do";
    
    /*1.36 微信预二维码扫描*/
    public static final String PAY_WX_PREPARE = MAIN_HOST + "/payment/tenpay/publicNativePay.do";
    
    /*1.37 微信二维码扫描订单查询*/
    public static final String PAY_WX_QUERY = MAIN_HOST +"/payment/tenpay/publicPayTradeQuery.do";
    
    /*1.38 微信预支付*/
    public static final String PAY_QR_WX_NOTIFY = MAIN_HOST + "";
    
    /*1.39 微信订单查询*/
    public static final String PAY_QR_WX_QUERY = MAIN_HOST +"";


    public enum Client {  //客户端类型,不在枚举范围内时默认为web  ,Enum("android","ios","web","stb")
        android, ios, web, stb
    }

    public static final String LANG = "zh-cn";  //语言，默认“zh-cn”

    public static final String PARTNER = "kidsmind"; //  合作商，分配给第三方集成的唯一标志, 可传kidsmind


    public static String deviceDRMId;  //设备编号

    public static String getDeviceDRMId(Context context) {
        if (StringUtils.isEmpty(deviceDRMId)) {
            deviceDRMId = DeviceUtils.getDeviceDRMId(context);
        }
        return deviceDRMId;
    }


}
