package com.dev.socket;

import android.util.Log;

public class HttpShared {

	private final String TAG = HttpShared.class.getSimpleName();

	private static HttpShared instance = null;

	private HttpConnector mHttpConnector;

	public static HttpShared getInstance() {
		if (instance == null) {
			instance = new HttpShared();
		}
		return instance;
	}

	/**
	 * 私有构造函数
	 */
	private HttpShared() {
		if (mHttpConnector == null) {
			mHttpConnector = new HttpConnector();
			mHttpConnector.setName("http-thread");
			mHttpConnector.start();
			Log.v(TAG, "HttpConnector is create");
		}
	}

	/**
	 * http请求
	 * 
	 * @param event
	 */
	public void reqHttpConnect(IRequest event) {
		mHttpConnector.addEvent(event);
	}

	/**
	 * 关闭http请求
	 */
	public void shutdown() {
		mHttpConnector.shutdown();
		mHttpConnector = null;
		instance = null;
	}

}
