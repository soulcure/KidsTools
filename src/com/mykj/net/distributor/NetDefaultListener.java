/**
 * 
 */
package com.mykj.net.distributor;

/**
 * 缺省分发监听器,此监听器需要被设置到NetDistributor，并且当前只有一个缺省监听器
 * 
 * @author Jason
 * 
 */
public abstract class NetDefaultListener {
	/**
	 * 此方法不供外部调用，有分发器NetDistributor调用来触发网络数据分发
	 * 
	 * @param netSocketPak
	 * @return
	 */
	protected final boolean receive(NetSocketPak netSocketPak) {
		return doReceive(netSocketPak);
	}

	/**
	 * 子类继承后必须要实现此方法，处理接受到NetSocketPak时的逻辑
	 * 
	 * @param data
	 * @return
	 */
	public abstract boolean doReceive(NetSocketPak data);

	/**
	 * 此方法的数据处理方式由穿透信息返回专用(穿透信息没有命令码)
	 * 
	 * @param buf
	 * @return
	 */
	public boolean doReceive(byte[] buf) {
		return false;
	};

	/**
	 * 此方法用于处理，正在执行doReceive方法的过程中出现的异常信息,由需要处理此种错误信息的子类重写此方法
	 * 
	 * @param e
	 * @return
	 */
	public boolean doError(Exception e) {
		return false;
	}

}
