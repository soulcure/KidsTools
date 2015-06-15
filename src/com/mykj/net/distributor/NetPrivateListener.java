package com.mykj.net.distributor;

import android.util.Log;

import com.dev.socket.TcpShared;

/**
 * 此类专门处理以主，子命令(支持多组命令{{主命令,子命令},{主命令,子命令}})为单位的网络协议，此类的对象默认为单次有效对象，处理完协议将被自动移除，
 * 如果需要永久处理指定网络协议，必须调用setOnlyRun(false),设置单次执行为false
 * 
 * @author Jason
 * 
 */
public abstract class NetPrivateListener {

	private static final String TAG = "NetPrivateListener";

	/** 需要处理的协议命名码(支持多组命令{{主命令,子命令},{主命令,子命令}}) */
	private short[][] mParseProtocol = null;

	/**
	 * 是否单次解析（默认为true） 单次解析的协议处理后自动移除监听池
	 */
	private boolean isOnce = true;

	/** 标识当前网络监听器是否是穿透监听器，穿透监听器没有主，子命令码 **/
	private boolean mIsProxyInfo = false;

	/**
	 * 此构造函数，由网络穿透专用
	 */
	public NetPrivateListener(boolean bool) {
		mIsProxyInfo = bool;
	}

	/**
	 * 是否穿透信息监听器
	 * 
	 * @return
	 */
	public boolean isProxyInfo() {
		return mIsProxyInfo;
	}

	/**
	 * 设置当前监听器的命令组
	 * 
	 * 
	 * @param mParseProtocol
	 *            记录协议的主命令和子命令，第2维的长度必须是2.
	 */
	public NetPrivateListener(short[][] parseProtocol) {
		mParseProtocol = parseProtocol;
	}

	/**
	 * 自动注册到已经创建的分发器
	 * 
	 * @Title: registerToRepeater
	 * @return
	 * @version: 2011-12-16 上午11:10:36
	 */
	public final boolean registerToRepeater() {
		if (mParseProtocol == null || mParseProtocol.length <= 0) {
			Log.e(TAG, "mParseProtocol 为空，不能注册");
			return false;
		}
		for (int i = 0; i < mParseProtocol.length; i++) {
			if (mParseProtocol[i].length != 2) {
				Log.e(TAG, "mParseProtocol 无效，不能注册");
				return false;
			}
		}
		TcpShared.getInstance().addTcpListener(this);
		return true;
	}

	/**
	 * @Title: isParse
	 * @Description: 判断是否可以解析当前注册协议
	 * @param command
	 * @return
	 * @version: 2011-12-27 下午02:46:48
	 */
	protected final boolean isParse(NetSocketPak command) {
		if (mParseProtocol == null || mParseProtocol.length <= 0) {
			return false;
		}
		short mdm = command.getMdm_gr();
		short msub = command.getSub_gr();
		for (int i = mParseProtocol.length - 1; i >= 0; i--) {
			if (mParseProtocol[i][0] == mdm && mParseProtocol[i][1] == msub) {
				return true;// 可以解析
			}
		}
		return false;
	}

	/**
	 * 判断是否属于指定的协议命令类型
	 * 
	 * @param mdm
	 *            主命令
	 * @param msub
	 *            子命令
	 * @return
	 */
	protected final boolean isEqual(short mdm, short msub) {
		if (mParseProtocol == null || mParseProtocol.length <= 0) {
			return false;
		}
		for (int i = mParseProtocol.length - 1; i >= 0; i--) {
			if (mParseProtocol[i][0] == mdm && mParseProtocol[i][1] == msub) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否属于指定协议的主命令
	 * 
	 * @param mdm
	 * @return
	 */
	protected final boolean isEqual(short mdm) {
		if (mParseProtocol == null || mParseProtocol.length <= 0) {
			return false;
		}
		for (int i = mParseProtocol.length - 1; i >= 0; i--) {
			if (mParseProtocol[i][0] == mdm) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否为相同的监听器，相同依据为具有相等组数的命令组
	 * 
	 * @param listener
	 * @return
	 */
	protected final boolean isEqual(NetPrivateListener listener) {
		if (mParseProtocol == null || mParseProtocol.length <= 0) {
			return false;
		}
		final short mdm[][] = listener.mParseProtocol;
		if (mdm == null || mdm.length <= 0) {
			return false;
		}
		if (mParseProtocol.length != mdm.length) { // 命令码，组数不同，视为不同监听器
			return false;
		}
		for (int i = mParseProtocol.length - 1; i >= 0; i--) {
			int j = 0;
			for (j = mdm.length - 1; j >= 0; j--) {
				if (mParseProtocol[i][0] == mdm[j][0]
						&& mParseProtocol[i][1] == mdm[j][1]) {
					break; // 找到就break
				}
			}
			if (j < 0) { // 如果没有找到匹配命令码，说明是不同监听器
				return false;
			}
		}
		return true;
	}


	/**
	 * 设置此监听器是否只处理一次
	 * 
	 * @param bool
	 *            true：处理一次后自动被移除，false：永久监听
	 */
	public final void setOnlyRun(boolean bool) {
		this.isOnce = bool;
	}

	/**
	 * 获得此监听器的运行状态
	 * 
	 * @return true：单词监听，false：永久监听
	 */
	public final boolean isOnlyOneRun() {
		return isOnce;
	}

	/**
	 * 网络数据处理函数，此函数由分发器调用，子类不能重写此方法
	 * 
	 * @param netSocketPak
	 * @return
	 */
	protected final boolean receive(NetSocketPak netSocketPak) {
		boolean bool = false;
		bool = doReceive(netSocketPak);
		return bool;
	}

	/**
	 * 此方法子类必须重写，实现受到网络数据后的逻辑处理
	 * 
	 * @param netSocketPak
	 * @return 此放回值很重要，只有返回值为true时，此次的网络数据才被认为是有效处理，如果是单次监听，监听器将会自动移除。
	 *         如果返回false将会继续寻找合适的监听器去处理这次网络数据，指导有人返回true，或者被丢弃
	 */
	public abstract boolean doReceive(NetSocketPak netSocketPak);

	public boolean doReceive(byte[] buf) {
		return false;
	};

	/**
	 * 当doReceive过程中出现任何异常信息，将被捕获，并调用doError方法来处理此异常信息，所以如果需要处理异常信息请重写此方法
	 * 
	 * @param e
	 * @return
	 */
	public boolean doError(Exception e) {
		return false;
	}

	/**
	 * 获得当前监听器是否单次有效
	 * 
	 * @return
	 */
	public boolean isOnce() {
		return isOnce;
	}

	/**
	 * 获得此监听器的命令数组
	 * 
	 * @return
	 */
	public short[][] getMdms() {
		return mParseProtocol;
	};

}
