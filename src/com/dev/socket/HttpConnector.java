package com.dev.socket;

import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 此类为socket连接管理类
 * 
 * @author Jason
 * 
 */
public class HttpConnector extends Thread {

	private final static String TAG = HttpConnector.class.getSimpleName();

	private Vector<IRequest> mEventVector;

	private Handler mHandler;

	/** 当前网络收发线程是否运行 **/
	private boolean isStart = false;

	/** http连接管理外部接口对象 **/
	private IRequest mCurReq = null;

	/** 连接标记 **/
	private String mTarget = null;

	/**
	 * 私有构造函数 一个具有指定处理handler的HttpConnector,handler可以为空， 如果为空将会自动创建Handler对象，
	 * 但是此构造函数不能在非android主线程调用因为可能会创建handler， 如果要在非主线程创建就必须由外部传入Handler对象
	 * 
	 * @param handler
	 */
	protected HttpConnector() {
		mEventVector = new Vector<IRequest>();
		mHandler = new Handler();
		isStart = true;
	}

	/**
	 * 添加http请求处理单元
	 * 
	 * @param _eventBody
	 */
	public synchronized final void addEvent(IRequest eventBody) {
		mEventVector.add(eventBody);
		synchronized (this) {
			notify();
		}
	}

	/**
	 * 关闭http请求
	 */
	protected void shutdown() {
		isStart = false;
		synchronized (this) {
			notify();
		}
	}

	/**
	 * http请求线程函数
	 */
	@Override
	public final void run() {

		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		HttpClient httpClient = new DefaultHttpClient(httpParams);

		while (isStart) {

			// 没有事件并且没有得到退出指令的时候就等待
			if (mEventVector.size() == 0) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				mCurReq = mEventVector.get(0);
				if (mCurReq == null) {
					continue;
				}
				String httpUrl = mCurReq.getPrivateUrl();
				if (httpUrl == null) {
					Log.v(TAG, "mCurReq=" + mCurReq + "http url is null");
					continue;
				}

				final String param = mCurReq.getPrivateParam();
				if (param != null) {
					httpUrl = httpUrl + "?" + param;
				}

				// 请求
				HttpResponse httpResponse = null;

				if (mCurReq.getHttpState() == IRequest.HTTP_POST) { // http get
					HttpPost httpPost = new HttpPost(httpUrl);
					MultipartEntity multipartEntity = mCurReq
							.getMultipartEntity();
					if (multipartEntity != null) {
						httpPost.setEntity(multipartEntity);
					}

					httpResponse = httpClient.execute(httpPost);

				} else { // default http get

					HttpGet httpGet = new HttpGet(httpUrl);

					httpResponse = httpClient.execute(httpGet);

				}

				final int statusCode = httpResponse.getStatusLine()
						.getStatusCode();
				final byte[] byteResult = EntityUtils.toByteArray(httpResponse
						.getEntity());

				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mCurReq.httpReqResult(statusCode, byteResult);
					}
				});
			} catch (Exception e) {
				netErrMsgSend(e);
			} finally {
				mEventVector.removeElementAt(0);
			}
		}
		Log.v(TAG, "httpClient shutdown");
		try {
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
				httpClient = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * http错误信息转发函数,此处所有错需信息都会post到已经创建的Handler由外部处理
	 * 
	 * @param errMsg
	 * @param errType
	 */
	private void netErrMsgSend(Exception errMsg) {
		final Message msg = mHandler.obtainMessage();
		msg.obj = errMsg;
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				if (mCurReq != null) {
					mCurReq.doError(msg);
				}
			}
		});
	}

	/**
	 * 获得此连接已经设置的唯一标识
	 * 
	 * @return
	 */
	public String getTarget() {
		return mTarget;
	}
}