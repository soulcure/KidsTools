package com.kidsmind.cartoon;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

import com.kidsmind.cartoon.config.AppConfig;
import com.kidsmind.cartoon.dialog.PayVipSecceedDialog;
import com.kidsmind.cartoon.entity.AppUpdateResponse;
import com.kidsmind.cartoon.entity.ConfigItem;
import com.kidsmind.cartoon.entity.ConfigRequest;
import com.kidsmind.cartoon.entity.ConfigResponse;
import com.kidsmind.cartoon.entity.HeartBeatResponse;
import com.kidsmind.cartoon.entity.ProtocolRequest;
import com.kidsmind.cartoon.entity.UserInfo;
import com.kidsmind.cartoon.entity.UserInfoRequest;
import com.kidsmind.cartoon.uitls.AppUtils;
import com.kidsmind.cartoon.uitls.FileAsyncTaskDownload;
import com.kidsmind.cartoon.uitls.GsonUtil;
import com.kidsmind.cartoon.uitls.HttpConnector;
import com.kidsmind.cartoon.uitls.IPostListener;
import com.kidsmind.cartoon.uitls.StringUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * @ClassName: KidsMindApplication
 * @Description: 应用程序全局类，
 */
public class KidsMindApplication extends Application {

	private static final String TAG = KidsMindApplication.class.getSimpleName();

	private Context mContext;

	public static final String DEFAULT_TOKEN = "default_token"; // 匿名账号token
	public static final String MOBILE_TOKEN = "mobile_token"; // 手机号账号token

	public static final String SETUP = "SET_UP"; // 应用是否首次安装

	private Map<String, String> mAppConfigs;

	public static final String ALLOW_COOCAAPAY = "AllowCoocaapay"; // 创维-酷开支付
	public static final String ALLOW_HUANPAY = "AllowHuanpay"; // 欢付宝支付
	public static final String ALLOW_HIVEVIEWPAY = "AllowHiveviewpay"; // 大麦支付
	public static final String ALLOW_MITVPAY = "AllowMitvpay"; // 小米TV
	public static final String ALLOW_ALIPAY_TV = "AllowAlipayTV"; // 阿里云OS TV
	public static final String ALLOW_ALIPAY = "AllowAlipay";
	public static final String ALLOW_ALIPAY_QRCODE = "AllowAlipayQrcode";
	public static final String ALLOW_WECHAT = "AllowWechat";
	public static final String ALLOW_WECHAT_QRCODE = "AllowWechatQrcode";
	public static final String ALLOW_UPDATE = "AllowUpdate";
	public static final String FORCE_HARDWARE_DECODE = "ForceHardWareDecode";
	public static final String ALLOW_WSSDK = "AllowWSSDK";
	public static final String ALLOW_MISTATS = "AllowMiStats";
	public static final String ALLOW_SELF_PROXY = "AllowSelfProxy";
	public static final String FORCE_HIVEVIEW_MAC = "ForceHiveviewMac";
	public static final String ALLOW_KTM = "AllowKTM"; // 允许开通码?
	public static final String HIVEVIEW_TUTO = "HiveviewTuto"; // 产品介绍使用mp4视频格式并走鹏博士CDN
	public static final String FORCE_LOCAL_PLALYER = "ForceLocalPlayer";
	public static final String FREE_CHANGE_ENGLISH = "FreeChangeEnglish";// 免费切换英文
	public static final String HIVEVIEW_CDN = "mnj.domy.huhatv.com";

	// 读取配置文件
	private static final String ASSETS_CONFIG_FILE = "config.properties";

	private Properties mConfigProps = null;

	private LoginType mLoginType;// 用户登录类型
	private String saveToken; // 用户登录TOKEN
	private String moblieNum;
	private int mProfileId;

	private UserInfo.VipType vipLevel; // 用户等级 device=匿名,register=注册,paid=付费
	private String vipExpiresTime; // Vip过期时间 yyyy-MM-dd HH:mm:ss

	/**
	 * 登录类型枚举
	 */
	public enum LoginType {
		defaultLogin, mobileLogin
	}

	/**
	 * 版本升级的严重度
	 */
	public enum Severity { // 严重度，blocker级别需要强制升级应用后使用
		blocker, major, normal
	}

	@Override
	public void onCreate() {
		mContext = this;
		AppConfig.AppContext = this;
		mAppConfigs = new HashMap<String, String>();

		initToken();
		initImageLoader(this);
		reqAppConfig();
		reqUserInfo();

	}

	public void initToken() {
		String mobileToken = AppUtils.getStringSharedPreferences(this,
				MOBILE_TOKEN, "");
		if (!StringUtils.isEmpty(mobileToken)) {
			saveToken = mobileToken;
			mLoginType = LoginType.mobileLogin;
		} else {
			String defaultToken = AppUtils.getStringSharedPreferences(this,
					DEFAULT_TOKEN, "");
			if (!StringUtils.isEmpty(defaultToken)) {
				saveToken = defaultToken;
				mLoginType = LoginType.defaultLogin;
			}
		}
	}

	/**
	 * 获取登录类型
	 * 
	 * @return
	 */
	public LoginType getLoginType() {
		return mLoginType;
	}

	public String getMoblieNum() {
		return moblieNum;
	}

	public void setMoblieNum(String moblieNum) {
		this.moblieNum = moblieNum;
	}

	public int getProfileId() {
		return mProfileId;
	}

	public void setProfileId(int profileId) {
		this.mProfileId = profileId;
	}

	/**
	 * 设置用户登录token
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		saveToken = token;
	}

	/**
	 * 获取用户登录token
	 * 
	 * @return
	 */
	public String getToken() {
		return saveToken;
	}

	/**
	 * 注销登录，清楚手机token
	 */
	public void clearMobileToken() {
		AppUtils.setStringSharedPreferences(this, MOBILE_TOKEN, "");
	}

	/**
	 * 获取渠道号
	 * 
	 * @return
	 */
	public String getPromoter() {
		return getProperty("ChannelNo");
	}

	/**
	 * 获取配置信息
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean getBooleanProperty(String key) {
		boolean value = false;

		if (mConfigProps == null) {
			initConfig();
		}
		if ("true".equals(mConfigProps.getProperty(key, "false"))) {
			value = true;
		}
		return value;
	}

	/**
	 * 获取配置信息
	 * 
	 * @param key
	 * @return string
	 */
	public String getProperty(String key) {
		if (mConfigProps == null) {
			initConfig();
		}
		return mConfigProps.getProperty(key, "false");
	}

	/**
	 * 判断应用是否首次启动
	 * 
	 * @return
	 */
	public boolean isFirstSetUp() {
		boolean res = false;
		boolean isSave = AppUtils.getBooleanSharedPreferences(this, SETUP,
				false);
		if (!isSave) {
			AppUtils.setBooleanSharedPreferences(this, SETUP, true);
			res = true;
		}
		return res;
	}

	public void setVipExpiresTime(String vipTime) {
		vipExpiresTime = vipTime;
	}

	public String getVipExpiresTime() {
		return vipExpiresTime;
	}

	public void setVipLevel(UserInfo.VipType vipLevel) {
		this.vipLevel = vipLevel;
	}

	public boolean isVip() {
		boolean res = false;
		if (vipLevel == UserInfo.VipType.paid) {
			res = true;
		}
		return res;
	}

	private void initConfig() {
		AssetManager am = getAssets();
		mConfigProps = new Properties();
		InputStream is = null;
		try {
			is = am.open(ASSETS_CONFIG_FILE);
			mConfigProps.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取配置项数据
	 * 
	 * @param key
	 * @return
	 */
	public String getAppConfig(String key) {
		return mAppConfigs.get(key);
	}

	/**
	 * 1.27 获取应用配置项
	 */
	private void reqAppConfig() {
		String url = AppConfig.APP_CONFIG;
		ConfigRequest request = new ConfigRequest();

		request.setUniqueKey("");

		String json = request.toJsonString();
		HttpConnector.httpPost(url, json, new IPostListener() {

			@Override
			public void httpReqResult(String response) {
				// TODO Auto-generated method stub
				ConfigResponse resp = GsonUtil.parse(response,
						ConfigResponse.class);
				if (resp.isSucess()) {
					List<ConfigItem> list = resp.getList();

					for (ConfigItem item : list) {
						mAppConfigs.put(item.getUniqueKey(),
								item.getUniqueValue());
					}
				}

			}

		});
	}

	/**
	 * 1.28 获取用户信息
	 */
	private void reqUserInfo() {
		String url = AppConfig.USER_INFO;
		UserInfoRequest request = new UserInfoRequest();

		String token = getToken();
		request.setToken(token);

		String json = request.toJsonString();
		HttpConnector.httpPost(url, json, new IPostListener() {

			@Override
			public void httpReqResult(String response) {
				// TODO Auto-generated method stub
				HeartBeatResponse resp = GsonUtil.parse(response,
						HeartBeatResponse.class);
				if (resp.success()) {
					vipLevel = resp.getVipLevel();
					vipExpiresTime = resp.getVipExpiresTime();
				}

			}

		});
	}

	/**
	 * 1.28 获取用户信息
	 */
	public void reqUserInfo(final String vipName) {
		String url = AppConfig.USER_INFO;
		UserInfoRequest request = new UserInfoRequest();

		String token = getToken();
		request.setToken(token);

		String json = request.toJsonString();
		HttpConnector.httpPost(url, json, new IPostListener() {

			@Override
			public void httpReqResult(String response) {
				// TODO Auto-generated method stub
				HeartBeatResponse resp = GsonUtil.parse(response,
						HeartBeatResponse.class);
				if (resp.success()) {
					String vipTime = resp.getVipExpiresTime();
					// String getMessage = resp.getMessage();

					// 保存到全局信息中
					setVipLevel(resp.getVipLevel());
					setVipExpiresTime(vipTime);

					// 提示用户
					PayVipSecceedDialog dialog = new PayVipSecceedDialog(
							mContext, vipName, vipTime);
					dialog.show();
				}

			}

		});
	}

	public MediaPlayer createMediaPlayer(Context content, int raw) {
		MediaPlayer mediaPlayer = new MediaPlayer().create(content, raw);
		return mediaPlayer;
	}

	/**
	 * 初始化网络图片下载
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
				context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		// config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}
}
