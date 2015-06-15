package com.dev.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

/**
 * 网络数据及异常处理单元
 * 
 */
public interface UnitProcess {
	/**
	 * 处理网络数据接收逻辑
	 * 
	 * @param is
	 *            输入流以阻塞方式读取数据，此方法会被循环调用，应该用is.read()方式阻塞，直到有数据到达
	 * @return
	 * @throws SocketTimeoutException
	 * @throws IOException
	 */
	public void socketInputStreamProcess(InputStream in) throws IOException;

	/**
	 * 网络底层出现的所有异常信息都将由此函数来处理
	 * 
	 * @param e
	 * @return
	 */
	public void socketExceptionProcess(Exception e);

	/** 打开处理单元，表示已经具有处理能力 **/
	public void openSocketProcess();

	/** 关闭处理单元，表示不再具备处理能力，但对象保留 **/
	public void closeSocketProcess();
}
