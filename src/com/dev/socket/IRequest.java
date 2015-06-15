package com.dev.socket;

import org.apache.http.entity.mime.MultipartEntity;

import android.os.Message;

/**
 * Htpp连接地址，参数，返回数据管理接口
 * 
 */
public abstract class IRequest {

	/** http get方式常量 **/
	public final static int HTTP_GET = 0;

	/** http post方式常量 **/
	public final static int HTTP_POST = 1;

	private int httpState = HTTP_GET;

	/**
	 * htpp连接的主url注：url的host和参数的分割符“？”底层会自定添加
	 * 
	 * @return
	 */
	public abstract String getHttpUrl();

	/**
	 * 传回的数据处理
	 * 
	 * @param statusCode
	 *            HttpStatus.SC_OK...
	 * @param buf
	 */

	public abstract void httpReqResult(final int statusCode, final byte[] buf);

	/**
	 * URL附带的参数
	 * 
	 * @return
	 */
	public String getParam() {
		return null;
	};

	/**
	 * 所有错误处理，包括网络错误，解析错误
	 * 
	 * @param msg
	 */
	public void doError(Message msg) {

	}

	public MultipartEntity getMultipartEntity() {
		// MultipartEntity multipartEntity = new MultipartEntity();
		return null;
	}

	public void setHttpState(int state) {
		httpState = state;
	}

	public int getHttpState() {
		return httpState;
	}

	protected final String getPrivateParam() {
		String str = getParam();
		if (str != null) {
			str = str.replace(" ", "");
		}
		return str;
	}

	protected final String getPrivateUrl() {
		String str = getHttpUrl();
		if (str != null) {
			str = str.replace(" ", "");
		}
		return str;
	}

}
