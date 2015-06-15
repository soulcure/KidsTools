/**
 * 
 */
package com.mykj.net.distributor;

import java.util.Hashtable;

import android.util.Log;


/**
 * 网络数据分发器
 * 
 */
public class NetDistributor {
	private static final String TAG = "NetDistributor";

	/** 穿透信息监听器在，map中的固定key **/
	private final String ProxyInfoKey = "ProxyInfo";

	private boolean isClosed = true;

	/** 当前网络监听器，此监听器专门处理由于底层网络异常所抛出的异常信息 **/
	private NetErrorListener mNetErrorListener = null;

	/** 当前缺省网络监听器，此监听器只存在一个 **/
	private NetDefaultListener mNetDefaultListener = null;

	/** 网络分发监听器map，为了提高查找效率使用map来存储网络监听器 **/
	private Hashtable<String, NetPrivateListener> mPrivateMap;

	public NetDistributor() {
		mPrivateMap = new Hashtable<String, NetPrivateListener>();
	}

	/**
	 * 添加一个指定的NetPrivateListener私有监听器,此监听器将根据主，子命令码，自动生成映射对象，便于数据分发时快速查找和响应
	 * 
	 * @param listener
	 */
	public void addPrivateListener(NetPrivateListener listener) {

		if (listener == null || mPrivateMap.contains(listener)) {
			Log.e(TAG, "listener 添加失败");
			return;
		}
		final String mdms[] = getListenerKey(listener);
		for (int i = 0; i < mdms.length; i++) {
			mPrivateMap.put(mdms[i], listener); // 多命令组，映射同一监听器
		}
	}

	/**
	 * 设置网络错误监听器，此监听当前只有一个，重复设置将会替换上一个已经设置的监听器
	 * 
	 * @param listener
	 */
	public void setErrorListener(NetErrorListener listener) {
		mNetErrorListener = listener;
	}

	public void handleSocketException(Exception e) {
		netErrorParse(e);

	}

	public void handleSocketPak(NetSocketPak pak) {
		if (pak == null || isClosed) {
			return;
		}

		if (netPrivateParse(pak)) { // 轻量级事件分发
			return;
		}
		if (netDefaultParse(pak)) { // 事件分发
			return;
		}

	}

	/**
	 * 打开分发器
	 */
	public void open() {
		Log.e(TAG, "distributor is open");
		isClosed = false;
	}

	/**
	 * 关闭分发器
	 */
	public void close() {
		Log.e(TAG, "distributor is closed");
		isClosed = true;
	}

	/**
	 * 设置当前缺省监听器，此监听器当前只有一个，重复设置将会替换上一个已经设置的监听器
	 * 
	 * @param lis
	 */
	public void setDefaultNetListener(NetDefaultListener lis) {
		mNetDefaultListener = lis;
	}

	/**
	 * 清理当前所有监听器
	 */
	public void clearListener() {
		if (mPrivateMap != null) {
			mPrivateMap.clear();
		}
		mNetErrorListener = null;
		mNetDefaultListener = null;
	}

	/**
	 * 网络错误信息处理函数，如果数据分发和处理过程中出现异常信息，将通知当前监听器doError方法处理当前错误
	 * 
	 * @param Exception
	 * @return 当网络信息为Exception时就返回true
	 */
	private boolean netErrorParse(Exception e) {
		if (mNetErrorListener == null) {
			return false;
		} else {
			mNetErrorListener.doNetError(e);
			return true;
		}
	}

	/**
	 * 网络私有监听器处理函数，如果数据分发和处理过程中出现异常信息，将通知当前监听器doError方法处理当前错误
	 * 
	 * @param msg
	 * @return Message.obj为NetSocketPak或者byte[]对象，并且已经被监听器实现函数正确处理，此函数才返回true
	 * @throws Exception
	 */
	private boolean netPrivateParse(NetSocketPak data) {
		if (data == null) {
			return false;
		}
		boolean isReceived = false;
		boolean isFind = false;
		NetPrivateListener listener = null;

		try {
			final String curListenerKey = getTableKey(data.getMdm_gr(),
					data.getSub_gr());
			listener = mPrivateMap.get(curListenerKey);
			if (listener != null) {
				isFind = true;

				if (isReceived = listener.receive(data)) {
					return true;
				} else {
					data.getDataInputStream().reset();
				}
			}
		} finally {
			if (isFind) {
				Log.v(TAG, "find PrivateHandler listener");
			} else {
				Log.e(TAG, "can not find PrivateHandler listener");
			}
			if (isReceived && listener != null && listener.isOnce()) {
				removeTcpListener(listener);
			}
		}
		return false;
	}

	/**
	 * 根据主，子命令组装私有监听器map内的通用key
	 * 
	 * @param data
	 * @return
	 */
	private String getTableKey(final short mdm, final short subMdm) {
		return mdm + "_" + subMdm;
	}

	/**
	 * 根据私有监听器的主，子命令数组，生成一个私有监听器map内的key数组
	 * 
	 * @param listener
	 * @return
	 */
	private String[] getListenerKey(NetPrivateListener listener) {
		final short mdms[][] = listener.getMdms();
		if (mdms == null) { // 穿透信息没有命令码
			if (listener.isProxyInfo()) { // 如果是穿透监听器设置特殊key
				return new String[] { ProxyInfoKey };
			}
			return new String[] {};
		}
		String[] mdmstr = new String[mdms.length];
		for (int i = 0; i < mdmstr.length; i++) {
			mdmstr[i] = getTableKey(mdms[i][0], mdms[i][1]);
		}
		return mdmstr;
	}

	/**
	 * 缺省监听器数据分发处理函数，如果数据分发和处理过程中出现异常信息，将通知当前监听器doError方法处理当前错误
	 * 
	 * @param msg
	 * @return Message.obj为NetSocketPak或者byte[]对象，并且已经被监听器实现函数正确处理，此函数才返回true
	 */
	private boolean netDefaultParse(NetSocketPak data) {
		if (mNetDefaultListener == null) {
			return false;
		} else {
			mNetDefaultListener.receive(data);
		}
		return true;
	}

	/**
	 * 移除指定的私有监听器，此方法移除的依据是私有监听器的主，子命令码
	 * 
	 * @param listener
	 */
	private void removeTcpListener(NetPrivateListener listener) {
		final String mdms[] = getListenerKey(listener);
		for (int i = 0; i < mdms.length; i++) {
			if (mdms[i] != null) {
				mPrivateMap.remove(mdms[i]);
			}
		}
	}

}
