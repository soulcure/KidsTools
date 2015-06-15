package com.kidsmind.cartoon.uitls;

/**
 * Htpp连接地址，参数，返回数据管理接口
 * 
 */
public abstract class IGetListener {

	public static final String TAG = IGetListener.class.getSimpleName();

	/**
	 * 传回的数据处理
	 * 
	 * @param response
	 *            HttpStatus.SC_OK...
	 */

	public abstract void httpReqResult(final String response);

	/**
	 * 所有错误处理，包括网络错误，解析错误
	 * 
	 * @param e
	 */
	public void doError(Exception e) {
		Log.e(TAG, e.toString());
	}

}
