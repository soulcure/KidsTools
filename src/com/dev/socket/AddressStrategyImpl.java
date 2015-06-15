package com.dev.socket;



/**
 * 此类为TcpConnector专用连接管理器，
 * 
 * @author Jason
 * 
 */
public interface AddressStrategyImpl {	
	/**
	 * 子类必须实现此方法，返回一个socket连接ip+port
	 * 
	 * @return
	 */
	public IpPortObj getIpPort();

	/**
	 * 数据复位
	 */
	public void reset();

	
	
}
