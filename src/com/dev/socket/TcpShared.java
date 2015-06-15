/**
 * 
 */
package com.dev.socket;

import java.io.IOException;
import java.io.InputStream;

import android.util.Log;

import com.mykj.comm.io.TDataOutputStream;
import com.mykj.net.distributor.NetDefaultListener;
import com.mykj.net.distributor.NetDistributor;
import com.mykj.net.distributor.NetErrorListener;
import com.mykj.net.distributor.NetPrivateListener;
import com.mykj.net.distributor.NetSocketPak;

/**
 * 网络数据封包，网络分发器创建和管理
 * 
 */
public class TcpShared {

	private final String TAG = TcpShared.class.getSimpleName();

	private static TcpShared instance = null;

	/** 网络数据风发器 **/
	private NetDistributor mDistributor = null;

	/** socket连接 **/
	private TcpConnector mTcpConnector = null;

	public static TcpShared getInstance() {
		if (instance == null) {
			instance = new TcpShared();
		}
		return instance;
	}

	protected TcpShared() {
		mDistributor = new NetDistributor();
	}

	/**
	 * 创建一个socket连接并不会自动启动
	 * 
	 * @param address
	 */
	public void createTcpConnector(AddressStrategyImpl address) {
		// 保证网络数据监听器不会重复创建
		UnitProcess unitProcess = new UnitProcess() {

			private boolean isClosed = false;

			@Override
			public void socketExceptionProcess(Exception e) {
				if (isClosed) {
					return;
				}

				if (mDistributor != null) {
					mDistributor.handleSocketException(e);
				}

			}

			@Override
			public void socketInputStreamProcess(InputStream in) {
				if (isClosed) {
					return;
				}

				NetSocketPak recCommand = new NetSocketPak(in);

				// 网络数据包发送
				if (mDistributor != null) {
					mDistributor.handleSocketPak(recCommand);
				}

			}

			@Override
			public void openSocketProcess() {
				if (mDistributor != null) {
					mDistributor.open();
				}
			}

			@Override
			public void closeSocketProcess() {
				mDistributor.close();
				mDistributor = null;
				isClosed = true;
			}
		};

		if (mTcpConnector == null) {
			mTcpConnector = TcpConnector.createTcpConnection(unitProcess,
					address);
		}
	}

	/**
	 * 添加私有监听器对象到分发器
	 * 
	 * @param listener
	 */
	public void addTcpListener(NetPrivateListener listener) {
		mDistributor.addPrivateListener(listener);
	}

	/**
	 * 网络数据发送
	 * 
	 * @param data
	 */
	public void reqNetData(NetSocketPak data) {
		if (mTcpConnector == null || data == null) {
			return;
		}
		Log.v(TAG, "reqNetData" + data);
		final byte[] buf = data.getSendBuffer();
		if (buf != null && buf.length > 0) {
			reqNetData(buf);
		} else {
			new Exception("NetSocketPak bytes is null").printStackTrace();
		}
	}

	/**
	 * 移除所有分发器内已注册的监听器
	 */
	public void clearListener() {
		mDistributor.clearListener();
	}

	/**
	 * 设置分发器的缺省监听器，此监听器每个分发器只有一个，重复设置会覆盖上一个
	 * 
	 * @param listener
	 */
	public void setTcpDefaultListener(NetDefaultListener listener) {
		mDistributor.setDefaultNetListener(listener);
	}

	/**
	 * 设置分发器的网络错误监听器，此监听器每个分发器只有一个，重复设置会覆盖上一个
	 * 
	 * @param listener
	 */
	public void setNetErrorListener(NetErrorListener listener) {
		mDistributor.setErrorListener(listener);
	}

	/**
	 * 发送网络数据,已字节数组形式
	 * 
	 * @param buf
	 */
	public void reqNetData(final byte[] buf) {
		if (mTcpConnector == null || buf == null) {
			Log.e(TAG, "reqNetData-mTcpConnector is null=" + buf);
			return;
		}
		mTcpConnector.sendData(buf);
	}

	/**
	 * 发送穿透
	 * 
	 * @param url
	 */
	protected void reqGetProxyInfo(String url) {
		if (mTcpConnector == null || url == null) {
			Log.e(TAG, "reqNetData-mTcpConnector is null=" + url);
			return;
		}
		mTcpConnector.sendData(TDataOutputStream.utf8toBytes(url));
	}

	/**
	 * 连接已经创建的socket
	 */
	public void connect(TcpConnector.IConnectCallBack _callBack,
			int reConnectCount) {
		if (mTcpConnector != null) {
			mTcpConnector.connectSocket(_callBack, reConnectCount);
		}
	}

	/**
	 * 链路层关闭网络
	 */
	public void closeSocket() {
		Log.v(TAG, "TcpShareder closeSocket");
		if (mTcpConnector != null) {
			try {
				if (mTcpConnector.isLive()) {
					mTcpConnector.shutDown();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			mTcpConnector = null;
		}
	}

	/**
	 * 清理分发器内的所有监听器，关闭socket连接,释放此单例对象
	 */
	public void closeTcp() {
		Log.v(TAG, "TcpShareder closeTcp");
		clearListener(); // 清理监听器
		closeSocket();
	}
}
