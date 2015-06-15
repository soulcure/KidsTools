package com.mykj.comm.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 字节输入流，提供各种数据类型的解析 <br>
 * 默认高位在前
 */
public class TDataInputStream extends ByteArrayInputStream {
	// X86结构,很多的ARM，DSP都为小端模式，而KEIL C51则为大端模式
	// buf[0] (0x78) -- 低位
	// buf[1] (0x56)
	// buf[2] (0x34)
	// buf[3] (0x12)-- 高位
	private boolean mIsFront = false; // true 高位在前 ; false 低位在前

	public TDataInputStream(byte[] buf) {
		super(buf);
	}

	public TDataInputStream(byte[] buf, boolean bool) {
		super(buf);
		mIsFront = bool;
	}

	public TDataInputStream(InputStream in) throws Exception {
		super(new byte[0]);
		if (in == null || in.available() == 0) {
			throw new Exception("InputStream is null");
		}
		byte[] array = new byte[in.available()];
		in.read(array);
		this.buf = array;
		this.count = array.length;
		this.pos = 0;
	}

	/**
	 * @param in
	 * @param readlen
	 *            指定长度
	 */
	public TDataInputStream(final InputStream in, final int readlen)
			throws Exception {
		super(new byte[0]);
		if (in == null || readlen <= 0) {
			return;
		}
		byte[] array = new byte[readlen];
		int re = in.read(array, 0, array.length);

		if (re == -1) {
			throw new Exception(
					"Create TDataInputStream readDataByLen return -1");
		}
		this.buf = array;
		this.count = array.length;
		this.pos = 0;
	}

	/**
	 * @Title: setFront
	 * @Description: 设置字节对齐方式
	 * @param isFront
	 *            true:高位在前 false:低位在前
	 * @version: 2011-5-16 下午02:25:51
	 */
	public void setFront(boolean isFront) {
		mIsFront = isFront;
	}

	/**
	 * @return the isFront
	 */
	public boolean isFront() {
		return mIsFront;
	}

	/**
	 * 默认高位在前的分布方式读取long
	 * 
	 * @return long
	 */
	public long readLong() {
		return readLong(mIsFront);
	}

	/**
	 * 默认高位在前的分布方式读取Int
	 * 
	 * @return Int
	 */
	public int readInt() {
		return readInt(mIsFront);
	}

	/**
	 * 默认高位在前的分布方式读取Short
	 * 
	 * @return Short
	 */
	public short readShort() {
		return readShort(mIsFront);
	}

	/**
	 * 读取一个byte
	 * 
	 * @return byte
	 */
	public byte readByte() {
		if (!isCanRead(1)) {
			return 0;
		}
		return (byte) this.read();
	}

	/**
	 * 读取BYTE,无符号的，和C++中的byte统一
	 * 
	 * @return
	 */
	public int readByteUnsigned() {
		if (!isCanRead(1)) {
			return 0;
		}
		return this.read(); // integer in the range from 0 to 255
		// int data = this.read();
		// if (data < 0) {
		// data = -data + 128;// ?
		// }
		// return data;
	}

	/**
	 * 用指定的高低位分布方式读取一个Long,true高位在前，false高位在后
	 * 
	 * @return long
	 */
	public long readLong(boolean bool) {
		if (!isCanRead(8)) {
			return 0;
		}
		byte[] array = new byte[8];
		read(array, 0, array.length);
		return getLongByBytes(array, bool);
	}

	/**
	 * 用指定的高低位分布方式读取一个Int,true高位在前，false高位在后
	 * 
	 * @return Int
	 */
	public int readInt(boolean bool) {
		if (!isCanRead(4)) {
			return 0;
		}
		byte[] array = new byte[4];
		read(array, 0, array.length);
		return getIntByBytes(array, bool);
	}

	/**
	 * 用指定的高低位分布方式读取一个Short,true高位在前，false高位在后
	 * 
	 * @return Short
	 */
	public short readShort(boolean bool) {
		if (!isCanRead(2)) {
			return 0;
		}
		byte[] array = new byte[2];
		read(array, 0, array.length);
		return getShortByBytes(array, bool);
	}

	/**
	 * 读取一个用UTF-8以short为长度单位的字符串
	 */
	public String readUTFShort() {
		if (!isCanRead(2)) {
			return null;
		}
		short len = readShort();
		return readUTFData(len);
	}

	/**
	 * 读取一个用UTF-8以byte为长度单位的字符串
	 */
	public String readUTFByte() {
		if (!isCanRead(1)) {
			return null;
		}
		short len = (short) read();
		return readUTFData(len);
	}

	/**
	 * @Title: readUTFData
	 * @Description: 读取UTF字符串指定个数的字节数，然后转化为正确的DataOutputStream流读取数据
	 * @param len
	 * @return
	 * @version: 2011-9-8 下午03:42:35
	 */
	private String readUTFData(short len) {
		if (!isCanRead(len)) {
			return null;
		}
		if (len <= 0) {
			return null;
		}
		byte bits[] = new byte[len];
		read(bits, 0, bits.length);
		return getUTF8String(bits);
	}

	/**
	 * 将指定数量用UTF-8的字节转化为字符串
	 * 
	 * @param num
	 *            字节数
	 */
	public String readUTF(int len) {
		return readUTFData((short) len);
	}

	/**
	 * 将指定字节数组转化为字符串
	 * 
	 * @param array
	 *            byte[]
	 * @return enc 指定编码
	 */
	public static String getUTF8String(byte[] array) {
		if (array == null || array.length <= 0) {
			return null;
		}
		try {
			int bitLen = -1; // 字符串的实际长度
			for (int i = 0; i < array.length; i++) { // 字符串遇到0将自动结束
				if (array[i] == 0) {
					bitLen = i;
				}
			}
			if (bitLen == 0) {
				return null;
			} else if (bitLen == -1) {
				bitLen = array.length;
			}
			return new String(array, 0, bitLen, "UTF-8").trim();
		} catch (Exception e) {
			return new String(array).trim();
		}
	}

	/**
	 * 读取一个byte用来表示boolean 1：true，0：false
	 * 
	 * @return
	 */
	public boolean readBoolean() {
		final byte b = readByte();
		return b == 1;
	}

	/**
	 * 将指定字节数组转化为long
	 * 
	 * @param array
	 *            byte[]
	 * @param bool
	 *            true高位在前，false高位在后
	 * @return long
	 */
	public static long getLongByBytes(byte[] array, boolean bool) {
		if (array == null /* || array.length != 8 */) {
			return -1;
		}
		return getDataByBytes(array, bool);
	}

	/**
	 * 将指定字节数组转化为int
	 * 
	 * @param array
	 *            byte[]
	 * @param bool
	 *            true高位在前，false高位在后
	 * @return int
	 */
	public static int getIntByBytes(byte[] array, boolean bool) {
		if (array == null /* || array.length != 4 */) {
			return -1;
		}
		return (int) getDataByBytes(array, bool);
	}

	/**
	 * 将指定字节数组转化为short
	 * 
	 * @param array
	 *            byte[]
	 * @param bool
	 *            true高位在前，false高位在后
	 * @return short
	 */
	public static short getShortByBytes(byte[] array, boolean bool) {
		if (array == null /* || array.length != 2 */) {
			return -1;
		}
		return (short) getDataByBytes(array, bool);
	}

	/**
	 * 将指定字节数组转化为long
	 * 
	 * @param array
	 *            byte[]
	 * @param bool
	 *            true高位在前，false高位在后
	 * @return long
	 */
	public static long getDataByBytes(byte[] array, boolean bool) {
		if (array == null) {
			return 0;
		}
		long tmp = 0;
		for (int i = 0; i < array.length; i++) {
			//
			tmp |= (long) ((array[bool ? array.length - 1 - i : i]) & 0xff) << (i * 8); // 其他字节转化
		}
		return tmp;
	}

	/**
	 * @Title: readBytes
	 * @Description: 读取此流指定起始位置和长度的字节
	 * @param dataStart
	 * @param dataLen
	 * @deprecated
	 * @return
	 * @version: 2011-5-18 上午09:58:55
	 */
	public byte[] readBytes(int dataStart, int dataLen) {
		if (!isCanRead(dataLen)) {
			return null;
		}
		if (dataLen < 0 || dataLen > buf.length || dataStart >= buf.length
				|| dataStart < 0) {
			return null;
		}
		byte[] array = null;
		try {
			int tmpPos = this.pos;
			array = new byte[dataLen];
			this.pos = dataStart;
			read(array);
			this.pos = tmpPos; // 还原
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return array;
	}

	/**
	 * 当前位置读取指定长度
	 * 
	 * @param dataLen
	 * @return
	 */
	public byte[] readBytes(int dataLen) {
		if (!isCanRead(dataLen)) {
			return null;
		}
		byte[] array = new byte[dataLen];
		try {
			read(array);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return array;
	}

	/**
	 * @Title: readBytes
	 * @Description: 读取流内的所有剩余字节
	 * @return
	 * @version: 2011-5-18 上午09:58:22
	 */
	public byte[] readBytes() {
		if (!isCanRead(1)) {
			return null;
		}
		byte[] array = new byte[available()];
		try {
			read(array);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return array;
	}

	/**
	 * 判断当前是否还可以继续读取
	 * 
	 * @return
	 */
	private boolean isCanRead(int len) {

		int counts = available();
		return counts >= len;

	}

	
	public boolean markData(int len){
		return isCanRead(len);
	}
	
	
	/**
	 * @Title: getPos
	 * @Description: 获得流的当前位置索引
	 * @return
	 * @version: 2011-5-26 上午09:36:36
	 */
	public int getPos() {
		return pos;
	}

}
