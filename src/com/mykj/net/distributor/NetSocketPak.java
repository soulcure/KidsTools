package com.mykj.net.distributor;

import java.io.IOException;
import java.io.InputStream;

import com.mykj.comm.io.TDataInputStream;
import com.mykj.comm.io.TDataOutputStream;

/**
 * 网络数据封包对象
 * 
 * @ClassName: NetSocketPak_1
 * @Description: socket协议结构
 * 
 */
public class NetSocketPak {
	/** 数据包头长度 8 */
	public static final byte HEAD_NO_SECRET = 4;

	private static final byte HEADSIZE = 8;

	/** 版本标示 1个字节,版本标识：默认值为4 */
	private byte mVersion = 4;

	/** 校验字段 1个字节 */
	private byte mCheckCode;

	/** 数据大小 2个字节 , 整个网络包体数据大小 */
	private short mPacketSize;

	/** 主命令码 2个字节 */
	private short mdm_gr;

	/** 子命令码 2个字节 */
	private short sub_gr;

	/** 整个发送数据包数据 */
	private byte[] mPackageData;

	/** 包体数据 */
	private byte[] mPackageBody;

	/** 接收数据 输入流 */
	private TDataInputStream dataInputStream;

	/** 发送数据 输出流 */
	private TDataOutputStream dataOutputStream;

	/**
	 * 构造函数 ---构造接受网络数据包
	 * 
	 * @param headByte
	 *            包头数据
	 * @param inputStream
	 *            接收输入流
	 */
	public NetSocketPak(byte[] buffer) {

		if (buffer == null || buffer.length < 8) {
			mdm_gr = sub_gr = -1;
			return;
		}
		byte[] head = new byte[HEADSIZE];
		mPackageBody = new byte[buffer.length - HEADSIZE];

		System.arraycopy(buffer, 0, head, 0, HEADSIZE);
		System.arraycopy(buffer, HEADSIZE, mPackageBody, 0, buffer.length
				- HEADSIZE);

		TDataInputStream in = new TDataInputStream(head);
		in.setFront(false);

		mVersion = in.readByte();
		mCheckCode = in.readByte();
		mPacketSize = in.readShort();
		mdm_gr = in.readShort();
		sub_gr = in.readShort();

		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// 记录数据
		dataInputStream = null;
		dataOutputStream = null;
		mPackageData = null;

	}

	
	
	/**
	 * 构造函数 ---构造接受网络数据包
	 * 
	 * @param headByte
	 *            包头数据
	 * @param inputStream
	 *            接收输入流
	 */
	public NetSocketPak(InputStream in) {

		if (in == null) {
			mdm_gr = sub_gr = -1;
			return;
		}
		
		TDataInputStream tdinput = null;
		try {
			tdinput = new TDataInputStream(in);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tdinput.setFront(false);

		mVersion = tdinput.readByte();
		mCheckCode = tdinput.readByte();
		mPacketSize = tdinput.readShort();
		mdm_gr = tdinput.readShort();
		sub_gr = tdinput.readShort();

		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		// 记录数据
		dataInputStream = null;
		dataOutputStream = null;
		mPackageData = null;

	}
	
	
	
	/**
	 * 构造函数 ---构造发送数据网络包
	 * 
	 * @param _mdm_gr
	 *            主命令码
	 * @param _sub_gr
	 *            子命令码
	 * @param outputStream
	 *            发送数据流
	 */
	public NetSocketPak(short _mdm_gr, short _sub_gr,
			TDataOutputStream outputStream) {
		mdm_gr = _mdm_gr;
		sub_gr = _sub_gr;
		int datalen = 0;
		if (outputStream != null) {
			datalen = outputStream.toByteArray().length;
		}
		dataOutputStream = outputStream;
		dataInputStream = null;
		mPackageBody = null;
		// 计算数据大小
		mPacketSize = (short) (HEADSIZE + datalen);
		mPackageData = null;
	}

	/**
	 * 构造函数 ---构造发送数据网络包
	 * 
	 * @param _mdm_gr
	 *            主命令码
	 * @param _sub_gr
	 *            子命令码
	 * @param _data
	 *            发送数据byte数组
	 */
	public NetSocketPak(short _mdm_gr, short _sub_gr, byte[] _data) {
		mdm_gr = _mdm_gr;
		sub_gr = _sub_gr;
		int datalen = 0;
		if (_data != null) {
			datalen = _data.length;
		}
		mPackageBody = _data;
		dataOutputStream = null;
		dataInputStream = null;
		// 计算数据大小
		mPacketSize = (short) (HEADSIZE + datalen);
		mPackageData = null;
	}

	/**
	 * 构造函数 ---构造发送数据网络包 ，无包体数据
	 * 
	 * @param _mdm_gr
	 *            主命令码
	 * @param _sub_gr
	 *            子命令码
	 */
	public NetSocketPak(short _mdm_gr, short _sub_gr) {
		mdm_gr = _mdm_gr;
		sub_gr = _sub_gr;
		int datalen = 0;
		mPackageBody = null;
		dataOutputStream = null;
		dataInputStream = null;
		// 计算数据大小
		mPacketSize = (short) (HEADSIZE + datalen);
		mPackageData = null;
	}

	/**
	 * 获得发送的完整数据包 (包头+数据体)以字节数组的形式
	 * 
	 * @return byte[]
	 */
	public byte[] getSendBuffer() {
		if (mPackageData == null) {
			mPackageData = sendBuffer();
		}
		return mPackageData;
	}

	/**
	 * @Title: getDataSize
	 * @Description: 获得数据的总长度--包括协议头
	 * @return
	 * @version: 2011-5-16 下午02:03:56
	 */
	public short getAllDataSize() {
		return mPacketSize;
	}

	/**
	 * @Title: getDataSize
	 * @Description: 获得总有效数据的长度--不包括协议头
	 * @return
	 * @version: 2011-5-16 下午02:05:31
	 */
	public short getDataSize() {
		return (short) (mPacketSize - HEADSIZE);
	}

	/**
	 * 获得此数据包的校验字段
	 * 
	 * @return
	 */
	public byte getValidata() {
		return mCheckCode;
	}

	/**
	 * 设置此数据包的校验字段
	 * 
	 * @param validata
	 */
	public void setValidata(byte validata) {
		mCheckCode = validata;
	}

	/**
	 * 获得此数据包的版本号
	 * 
	 * @return
	 */
	public byte getVersion() {
		return mVersion;
	}

	/**
	 * @Title: getMdm_gr
	 * @Description: 获得主命令码
	 * @return
	 * @version: 2011-5-16 下午02:01:47
	 */
	public short getMdm_gr() {
		return mdm_gr;
	}

	/**
	 * @Title: getSub_gr
	 * @Description: 获得子命令码
	 * @return
	 * @version: 2011-5-16 下午02:02:01
	 */
	public short getSub_gr() {
		return sub_gr;
	}

	/**
	 * 获得输入数据流--多用于接收到网络数据后解析时
	 * 
	 * @return the dataInputStream
	 */
	public TDataInputStream getDataInputStream() {
		if (dataInputStream == null) {
			dataInputStream = new TDataInputStream(mPackageBody);
			dataInputStream.setFront(false);
		}
		return dataInputStream;
	}

	/**
	 * @Title: free
	 * @Description: 清理所有已缓存的数据
	 * @version: 2011-5-10 下午06:12:40
	 */
	public void free() {
		mdm_gr = -1;
		sub_gr = -1;
		mPackageData = null;
		mPackageBody = null;
		if (dataInputStream != null) {
			try {
				dataInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dataInputStream = null;
		}
		if (dataOutputStream != null) {
			try {
				dataOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			dataOutputStream = null;
		}
	}

	/**
	 * 已覆盖实现，输出流的包头等关键信息
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("主命令码：").append(mdm_gr);
		sb.append("  子命令码：").append(sub_gr);
		sb.append("  数据长度：").append(mPacketSize - HEADSIZE);
		return sb.toString();
	}

	/**
	 * @Title: sendBufferEncrypt
	 * @Description: 获得包括协议头的所有数据，加密数据
	 */
	private byte[] sendBuffer() {
		if (mPacketSize < HEADSIZE) {
			return new byte[0];
		}
		// 组合数据包
		byte[] bufferByte = new byte[mPacketSize - HEADSIZE];
		if (mPacketSize >= HEADSIZE) {
			if (dataOutputStream != null) {
				final byte[] buf = dataOutputStream.toByteArray();
				System.arraycopy(buf, 0, bufferByte, 0, buf.length);
				dataOutputStream.reset();
			} else if (mPackageBody != null) {
				System.arraycopy(mPackageBody, 0, bufferByte, 0,
						bufferByte.length);
			} else if (dataInputStream != null) {
				final byte[] buf = dataInputStream.readBytes();
				System.arraycopy(buf, 0, bufferByte, 0, buf.length);
				dataInputStream.reset();
			}

			// 组装包头
			TDataOutputStream send = new TDataOutputStream(false); // 还原完整的数据包

			// 写入包头数据
			send.writeByte(mVersion); // 版本标识：默认值为1
			send.writeByte(mCheckCode); // 效验字段
			send.writeShort(mPacketSize); // 数据大小

			send.writeShort(mdm_gr); // 主命令码
			send.writeShort(sub_gr); // 子命令码

			send.write(bufferByte, 0, bufferByte.length); // 包体数据

			bufferByte = send.toByteArray(); // 包头+数据体

			try {
				send.close();
				send = null;
			} catch (IOException e) {
			}
		}

		return bufferByte;
	}

}
