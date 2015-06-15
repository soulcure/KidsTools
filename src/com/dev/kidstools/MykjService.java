package com.dev.kidstools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.dev.kidstools.uitls.AppUtils;
import com.dev.kidstools.uitls.HttpConnector;
import com.dev.kidstools.uitls.IGetListener;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MykjService extends Service {

	private static final String TAG = "MykjService";
	private static final long WATCHDOG_DELAY = 10 * 60 * 1000; // 十分钟定时器启动

	private AlarmManager mAlarmManager;
	private Context mContext;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(TAG, "GameLobbyService is onCreate");
		mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		mContext = this;

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.v(TAG, "MykjService is onStartCommand");
		String action = "";
		if (intent != null) {
			action = intent.getAction();
		}
		Log.v(TAG, "onStartCommand action=" + action);

		if (action.equals("mykj.service.BOOT_SERVICE")) {
			setWatchdog(mAlarmManager);
			if (AppUtils.isNetworkConnected(this) && isActionTime()) {
				doSomething();
			}
		}
		return START_NOT_STICKY;
	}

	/**
	 * 定时创建服务
	 * */
	private void setWatchdog(AlarmManager alarmMgr) {
		Intent intent = new Intent();
		intent.setClass(mContext, MykjReceiver.class);
		intent.setAction("mykj.intent.action.ALARM_BOOT_BROADCAST");
		PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		long timeNow = SystemClock.elapsedRealtime();
		long nextCheckTime = timeNow + WATCHDOG_DELAY; // 下次启动的时间
		alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, nextCheckTime, pi);
		Log.v(TAG, "定时注册广播发送PendingIntent");
	}

	private void doSomething() {
		String url = "http://vote9.scwmw.gov.cn/index.php?m=Vote_2015xdy&a=add&callback=jQuery19109296464654617012_1434349750088&check_name=xdy_093&_=1434349750091";
		HttpConnector.httpGet(url, new IGetListener() {

			@Override
			public void httpReqResult(String response) {
				// TODO Auto-generated method stub
				Log.e("colin", "httpReqResult : " + response);
			}

		});
	}

	
	private static final String startTime = "2015-06-15";
	private static final String endTime = "2015-06-20";

	public static String getCurTime() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.CHINA);

		return dateFormat.format(new Date(System.currentTimeMillis()));
	}

	private boolean isActionTime() {
		boolean res = false;
		String curTime = getCurTime();

		if (curTime.compareTo(startTime) >= 0
				&& curTime.compareTo(endTime) <= 0) {
			 res = true;
		}
		return res;
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "GameLobbyService is onDestroy");
		super.onDestroy();
	}

}
