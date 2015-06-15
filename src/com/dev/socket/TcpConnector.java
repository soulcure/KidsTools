package com.dev.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Vector;

import android.util.Log;


/**
 * socket连接对象
 * 
 */
public class TcpConnector {

	private final static String TAG = TcpConnector.class.getSimpleName();

	/** 连接超时时间30s **/
	private int mConnectTimeout = 30 * 1000;

	/** socket连接对象 **/
	private Socket mSocket;
	/** socket网络接受线程对象 **/

	private TcpReceiveRunnable mReceiver;

	/** socket网络发送线程对象 **/
	private TcpSendRunnable mSender;

	/** socket连接地址策略 **/
	private AddressStrategyImpl mAddress = null;

	private boolean isExited = false;

	/** 网络数据及事件处理单元 **/
	private UnitProcess mUnprocess;

	public interface IConnectCallBack {
		/**
		 * 此方法将会在连接正常，并且成功的情况下被调用，由子类实现连接成功后的功能
		 */
		public void connectSucceed();

		/**
		 * 连接失败
		 * 
		 * @param e
		 */
		public void connectFailed(Exception e);

	}

	/**
	 * 私有构造函数
	 */
	private TcpConnector() {
	}

	/**
	 * 创建一个socket连接,需要传入处理单元，地址策略和唯一标识
	 * 
	 * @param up
	 * @param as
	 * @param target
	 * @return
	 */
	protected static TcpConnector createTcpConnection(final UnitProcess up,
			final AddressStrategyImpl as) {
		if (as == null)
			throw new NullPointerException(
					"TcpConnector createTcpConnection  AddressStrategy is null");
		TcpConnector tcp = new TcpConnector();
		tcp.setUnitProcess(up);
		tcp.setAddressStrategy(as);
		return tcp;
	}

	/**
	 * 创建连接
	 * 
	 * @param callBack
	 *            回调对象
	 * @param reConnectCount
	 *            重连次数
	 */
	public void connectSocket(IConnectCallBack callBack, int reConnectCount) {
		isExited = false;
		Thread thread = new Thread(
				new ConnectRunnable(callBack, reConnectCount));
		thread.setName("connect-thread");
		thread.start();
	}

	/**
	 * 
	 * @param up
	 */
	private void setUnitProcess(UnitProcess up) {
		mUnprocess = up;
		if (mUnprocess != null) {
			mUnprocess.openSocketProcess();
		}
	}

	/**
	 * 设置当前地址策略处理对象
	 * 
	 * @param as
	 */
	private void setAddressStrategy(AddressStrategyImpl as) {
		if (as == null)
			throw new NullPointerException(
					"setAddressStrategy AddressStrategy is null");
		mAddress = as;
	}

	public void sendData(byte[] data) {
		if (mSender == null || isExited) {
			return;
		}
		mSender.send(data);
	}

	public boolean isLive() {
		return !isExited;
	}

	/**
	 * 关闭socket
	 * 
	 * @throws IOException
	 */
	public void shutDown() throws IOException {
		if (isExited) {
			Log.v(TAG, "shutDown-网络已经关闭");
			return;
		}
		isExited = true;
		if (mUnprocess != null) {
			mUnprocess.closeSocketProcess();
			mUnprocess=null;
		}
		if (mSocket != null) {
			if (!mSocket.isOutputShutdown()) {
				try {
					mSocket.shutdownOutput();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!mSocket.isInputShutdown()) {
				try {
					mSocket.shutdownInput();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!mSocket.isClosed()) {
				try {
					mSocket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (mReceiver != null) {
				mReceiver.close();
				mReceiver = null;
			}
			if (mSender != null) {
				mSender.close();
				mSender = null;
			}

			mSocket = null;
			Log.e(TAG, "已执行 socket.close()");
		}

	}

	/**
	 * 唯一标识
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return "tcp-" + this.hashCode();
	}

	/**
	 * socket 链接线程
	 * 
	 */
	private class ConnectRunnable implements Runnable {

		private IConnectCallBack mCallBack = null;

		/** 重连次数 */
		private int mReConnectCount = 1;

		private ConnectRunnable(IConnectCallBack callBack, int reConnectCount) {
			mCallBack = callBack;
			mReConnectCount = reConnectCount;

			if (callBack == null) {
				throw new NullPointerException("New Connect callBack is null");
			}
			if (mReConnectCount <= 0) {
				throw new IllegalArgumentException(
						"New Connect reConnectCount = " + reConnectCount);
			}

		}

		@Override
		public void run() {
			int connectCount = mReConnectCount;
			IpPortObj ipport = null;

			while (true) {
				try {
					if (mSocket == null) {
						mSocket = new Socket();
					}
					ipport = mAddress.getIpPort();
					if (ipport != null) {

						InetSocketAddress isa = new InetSocketAddress(
								ipport.getIp(), ipport.getPort());
						mSocket.connect(isa, mConnectTimeout);

						if (mSocket.isConnected()) {
							Log.v(TAG, "socket connect " + ipport.getIp() + ":"
									+ ipport.getPort() + " succeed");
							mSender = new TcpSendRunnable();
							mSender.start();
							mReceiver = new TcpReceiveRunnable();
							mReceiver.start();

							// 回调
							if (mCallBack != null) {
								mCallBack.connectSucceed();
							}
							return;

						} else {
							throw new IOException("socket is not connected");
						}
					} else {
						mAddress.reset();// 复位地址策略器
						Log.e(TAG, "ip or port is null");
						throw new Exception("ip or port is null");
					}
				} catch (Exception e) {
					Log.e(TAG,
							"connect" + ipport.getIp() + ":" + ipport.getPort()
									+ " failed reson:" + e.getMessage());
					connectCount--;
					if (connectCount > 0) {
						try {
							Thread.sleep(2 * 1000); // 休眠2s
						} catch (InterruptedException e1) {
						}
						continue;
					} else {
						// 回调
						if (mCallBack != null) {
							mCallBack.connectFailed(e);
						}
						return;
					}

				}

			} // end while

		} // end run

	} // end ConnectRunnable

	/**
	 * socket接收线程
	 * 
	 * @author Administrator
	 * 
	 */
	public class TcpReceiveRunnable implements Runnable {
		private InputStream is;

		private boolean isExit = false;

		private TcpReceiveRunnable() throws IOException {
			is = mSocket.getInputStream();
		}

		@Override
		public void run() {
			while (!isExit) {

				if (mUnprocess != null) {
					try {
						mUnprocess.socketInputStreamProcess(is);
					} catch (Exception e) {
						mUnprocess.socketExceptionProcess(e);
					}

				}

			}

		} // end run()

		private void start() {
			Thread thread = new Thread(this);
			thread.setName("tcpReceive-thread");
			thread.start();
		}

		private void close() {
			Log.v(TAG, " tcpReceive-thread close() hashcode=" + hashCode());
			isExit = true;

			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
				is = null;
			}

		}

	} // end TcpReceiveRunnable

	/**
	 * socket发送线程
	 * 
	 * @author Administrator
	 * 
	 */
	private class TcpSendRunnable implements Runnable {

		private OutputStream os;

		private Vector<byte[]> buffer;

		private boolean isExit = false;

		private TcpSendRunnable() throws IOException {
			os = mSocket.getOutputStream();
			buffer = new Vector<byte[]>();
		}

		@Override
		public void run() {
			while (!isExit) {
				byte[] data = buffer.size() > 0 ? buffer.get(0) : null;
				if (data != null) {
					try {
						os.write(data);
						os.flush();
						buffer.remove(0);
					} catch (Exception e) {
						if (mUnprocess != null) {
							mUnprocess.socketExceptionProcess(e);
							break;

						}
					}
				} else {
					try {
						synchronized (this) {
							wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} // end while
		} // end run()

		private void start() {
			Thread thread = new Thread(this);
			thread.setName("tcpSend-thread");
			thread.start();
		}

		private void send(byte[] data) {
			buffer.add(data);
			synchronized (this) {
				notify();
			}
		}

		private void close() throws IOException {
			Log.v(TAG, " tcpSend-thread close() hashcode=" + hashCode());
			isExit = true;
			synchronized (this) { // 激活线程
				notify();
			}
			if (os != null) {
				try {
					os.close();
				} catch (Exception e) {
				}
			}
			os = null;
		}

	} // end //end TcpSendRunnable

}
