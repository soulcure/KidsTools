package com.dev.kidstools.uitls;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class DeviceUtils {

	private static final String TAG = DeviceUtils.class.getSimpleName();

	private DeviceUtils() {
		throw new AssertionError();
	}

	/**
	 * 获取用于注册的设备序列号
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceDRMId(Context context) {
		int DRM_ID_LEN = 60;
		String PROJECT_NAME = "KMNJ";
		String VENDOR = "AD";
		String MODEL = "MB";

		String mac = getLocalMacAddress(context);

		if (mac != null) {
			mac = mac.replace(":", "");
		}

		String serial = getSerialNumber(context);
		String drmId = PROJECT_NAME + VENDOR + MODEL + mac;
		int serial_len = 0;
		String zero = "";
		if (serial != null) {
			serial_len = serial.length();
		}
		for (int i = 0; i < DRM_ID_LEN - drmId.length() - serial_len; i++) {
			zero += "0";
		}
		drmId = drmId + zero + serial;

		return drmId;
	}

	/**
	 * 获取设备的mac地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getLocalMacAddress(Context context) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		return info.getMacAddress();
	}

	/**
	 * 获取设备序列号
	 * 
	 * @param context
	 * @return
	 */
	public static String getSerialNumber(Context context) {
		return android.os.Build.SERIAL;
	}

	/**
	 * 获取android id
	 * 
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context) {
		return Secure
				.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * 获取手机IMSI
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		String imsi = null;
		try {
			TelephonyManager phoneManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = phoneManager.getSubscriberId();
			Log.v(TAG, imsi);
		} catch (Exception e) {
			Log.e(TAG, "getIMSI error!");
			imsi = "";
		}

		if (imsi == null) {
			imsi = "";
		}
		return imsi;
	}

	/**
	 * 获取手机IMEI
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		String imei = null;
		try {
			TelephonyManager phoneManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			imei = phoneManager.getDeviceId();
		} catch (Exception e) {
			Log.e(TAG, "getIMEI error!");
			imei = "";
		}
		if (imei == null) {
			imei = "";
		}
		return imei;
	}

	/**
	 * 获取iccid SIM卡序列号
	 * 
	 * @param context
	 * @return
	 */
	public static String getICCID(Context context) {
		String iccid = "";
		try {
			TelephonyManager phoneManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			iccid = phoneManager.getSimSerialNumber();
		} catch (Exception e) {
			Log.e(TAG, "getIMEI error!");
			iccid = "";
		}
		if (iccid == null) {
			iccid = "";
		}
		return iccid;
	}

}
