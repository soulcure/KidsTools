package com.dev.kidstools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MykjReceiver extends BroadcastReceiver {
	private static final String TAG = "MykjReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {

		Log.v(TAG, "MykyReceiver is onReceive");
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")
				|| intent.getAction().equals("mykj.intent.action.ALARM_BOOT_BROADCAST")) {
			Log.v(TAG, intent.getAction());
			Intent in = new Intent();// intent对象 用于启动服务
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			in.setAction("mykj.service.BOOT_SERVICE");
			in.setClass(context.getApplicationContext(), MykjService.class);
			context.startService(in);// 开机 启动服务
		}

	}

}