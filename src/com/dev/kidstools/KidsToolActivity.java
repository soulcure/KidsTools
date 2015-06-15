package com.dev.kidstools;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.widget.Toast;

import com.dev.kidstools.R;
import com.dev.kidstools.uitls.Log;
import com.nostra13.universalimageloader.core.ImageLoader;

public class KidsToolActivity extends Activity {

	private static final String TAG = KidsToolActivity.class.getSimpleName();

	public static final int REQUEST_MODE_A = 0;
	public static final int RESULT_MODEL_A_BACK = 1;

	/* 保存返回键被按下的时间 */
	private long mPressedTime;

	private static final int UITHREAD_INIT_COMPLETE = 0;
	private static final int HANDLERTHREAD_INIT_CONFIG_START = 1;

	private UIHandler mUIHandler;
	private ProcessHandler mProcessHandler;

	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		initHandler();
		setBootWatchdog(5000);

	}

	/**
	 * 主线程处理handler
	 * 
	 * @author Administrator
	 */
	private class UIHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case UITHREAD_INIT_COMPLETE:
				break;

			default:
				break;
			}
		}
	}

	/**
	 * 子线程handler,looper
	 * 
	 * @author Administrator
	 */
	private class ProcessHandler extends Handler {
		private Handler mHandler;

		public ProcessHandler(Looper looper, Handler restoreHandler) {
			super(looper);
			this.mHandler = restoreHandler;
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLERTHREAD_INIT_CONFIG_START:

				mHandler.sendEmptyMessage(UITHREAD_INIT_COMPLETE); // 通知UI线程
				break;
			default:
				break;
			}

		}

	}

	/**
	 * 线程初始化
	 */
	private void initHandler() {
		if (mUIHandler == null) {
			mUIHandler = new UIHandler();
		}
		if (mProcessHandler == null) {
			HandlerThread handlerThread = new HandlerThread(
					"handler looper Thread");
			handlerThread.start();
			mProcessHandler = new ProcessHandler(handlerThread.getLooper(),
					mUIHandler);
		}
	}

	@Override
	public void onBackPressed() {

		if ((System.currentTimeMillis() - mPressedTime) < 2000) {
			ImageLoader.getInstance().stop();
			finish();
		} else {
			mPressedTime = System.currentTimeMillis();
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 定时创建服务
	 * */
	private void setBootWatchdog(long mill) {

		AlarmManager mAlarmManager = (AlarmManager) mContext
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent();
		intent.setClass(mContext, MykjReceiver.class);
		intent.setAction("mykj.intent.action.ALARM_BOOT_BROADCAST");
		PendingIntent pi = PendingIntent.getBroadcast(mContext, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		long timeNow = SystemClock.elapsedRealtime();
		long nextCheckTime = timeNow + mill; // 下次启动的时间
		mAlarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, nextCheckTime,
				pi);
		Log.v(TAG, "注册广播发送PendingIntent");
	}

	@Override
	public void onResume() {
		super.onResume();
	}
}
