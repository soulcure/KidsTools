/**
 * 
 */
package com.mykj.net.distributor;

/**
 * 网络错误监听器，此监听器只会处理网络底层引起的错误信息，如果是由分发器，分发数据过程中引起的错误信息，将被分发到当前监听器的doError方法处理
 * 
 * @author Jason
 * 
 */
public abstract class NetErrorListener {

	/**
	 * 此方法有分发器调用不提供重写操作
	 * 
	 * @param e
	 * @return
	 */
	protected final boolean netError(Exception e) {
		return doNetError(e);
	}

	/**
	 * 此方法由子类重写来处理，网络错误后的逻辑
	 * 
	 * @param e
	 * @return
	 */
	public abstract boolean doNetError(Exception e);
}
