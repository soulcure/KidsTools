package com.mykj.comm.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 高级还存流，支持高级数据缓存，字节缓存及转化
 */
public class TDataOutputStream extends ByteArrayOutputStream {

	// X86结构,很多的ARM，DSP都为小端模式，而KEIL C51则为大端模式
	// buf[0] (0x78) -- 低位
	// buf[1] (0x56)
	// buf[2] (0x34)
	// buf[3] (0x12)-- 高位
	private boolean mIsFront = false; // true 高位在前 ; false 低位在前

	public TDataOutputStream() {
		super();
	}

	/**
	 * @param mIsFront
	 *            是否是高位在前
	 */
	public TDataOutputStream(boolean isFront) {
		super();
		mIsFront = isFront;
	}

	/**
	 * 设置字节对齐方式 true:高位在前 false:低位在前
	 * 
	 * @param isFront
	 *            the isFront to set
	 */
	public void setFront(boolean isFront) {
		mIsFront = isFront;
	}

	/**
	 * 写入long的全部8个字节，高位在前，依次类推
	 * 
	 * @param num
	 *            long
	 */
	public void writeLong(long num) {
		writeLong(num, mIsFront);
	}

	/**
	 * 写入int的全部4个字节，高位在前，依次类推
	 * 
	 * @param num
	 *            int
	 */
	public void writeInt(int num) {
		writeInt(num, mIsFront);
	}

	/**
	 * 写入short的全部2个字节，高位在前，依次类推
	 * 
	 * @param num
	 *            short
	 */
	public void writeShort(int num) {
		writeShort(num, mIsFront);
	}

	/**
	 * @Title: writeByte
	 * @Description: 写入一个字节
	 * @param lobbyType
	 * @version: 2011-5-18 下午03:23:51
	 */
	public void writeByte(int b) {
		write(b);
	}

	/**
	 * 写入long的全部8个字节，高位在前，依次类推
	 * 
	 * @param num
	 *            long
	 * @param bool
	 *            true高位在前，false高位在后
	 */
	public void writeLong(long num, boolean bool) {
		byte[] array = longToBytes(num, bool);
		if (array == null) {
			return;
		}
		write(array, 0, array.length);
	}

	/**
	 * 写入int的全部4个字节，高位在前，依次类推
	 * 
	 * @param num
	 *            int
	 * @param bool
	 *            true高位在前，false高位在后
	 */
	public void writeInt(int num, boolean bool) {
		byte[] array = intToBytes(num, bool);
		if (array == null) {
			return;
		}
		write(array, 0, array.length);
	}

	/**
	 * 写入short的全部2个字节，高位在前，依次类推
	 * 
	 * @param num
	 *            short
	 * @param bool
	 *            true高位在前，false高位在后
	 */
	public void writeShort(int num, boolean bool) {
		byte[] array = shortToBytes(num, bool);
		if (array == null) {
			return;
		}
		write(array, 0, array.length);
	}

	/**
	 * 用指定的编码写入字符串
	 * 
	 * @param str
	 *            要写入的字符串
	 * @param enc
	 *            指定的编码
	 */
	public void writeSTR(String str, String enc, int len) {
		if (str == null) {
			str = "";
		}
		byte[] array = null;
		try {
			array = str.getBytes(enc);
			if (array == null || len > array.length) {
				return;
			}
			write(array, 0, len);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用指定的编码写入字符串
	 * 
	 * @param str
	 *            要写入的字符串
	 * @param enc
	 *            指定的编码
	 */
	public void writeSTR(String str, String enc) {
		if (str == null) {
			str = "";
		}
		byte[] array = null;
		try {
			array = str.getBytes(enc);
			if (array == null) {
				return;
			}
			write(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用指定的编码依次写入字符串长度（short）及字符串
	 * 
	 * @param str
	 *            要写入的字符串
	 * @param enc
	 *            指定的编码
	 */
	public void writeSTRShort(String str, String enc) {
		if (str == null) {
			str = "";
		}
		byte[] array = null;
		try {
			array = str.getBytes(enc);
			if (array == null) {
				return;
			}
			writeShort(array.length); // 写入长度
			write(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用指定的编码依次写入字符串长度（byte）及字符串
	 * 
	 * @param str
	 *            要写入的字符串
	 * @param enc
	 *            指定的编码
	 */
	public void writeSTRByte(String str, String enc) {
		if (str == null) {
			str = "";
		}
		byte[] array = null;
		try {
			array = str.getBytes(enc);
			if (array == null) {
				return;
			}
			write(array.length); // 写入长度
			write(array);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用默认UTF-8编码写入字符串
	 * 
	 * @param str
	 *            要写入的字符串
	 * @param len
	 *            固定的长度，超出utf-8长度，将被剔除
	 */
	public void writeUTF(String str, final int len) {
		if (str == null) {
			str = "";
		}
		byte[] bits = new byte[len];
		byte[] array = utf8toBytes(str);
		final int blen = array.length < len ? array.length : len;
		System.arraycopy(array, 0, bits, 0, blen);
		write(bits, 0, len);
	}

	/**
	 * 用默认UTF-8编码写入字符串
	 * 
	 * @param str
	 *            要写入的字符串
	 */
	public void writeUTF(String str) {
		if (str == null) {
			str = "";
		}
		byte[] bits = utf8toBytes(str);
		if (bits == null) {
			return;
		}
		write(bits, 0, bits.length);
	}

	/**
	 * 用默认UTF-8编码依次写入字符串长度（short）及字符串
	 * 
	 * @param str
	 *            要写入的字符串
	 */
	public void writeUTFShort(String str) {
		if (str == null) {
			str = "";
		}
		byte[] bits = utf8toBytes(str);
		if (bits == null) {
			return;
		}
		int len = bits.length;
		writeShort(len);
		write(bits, 0, len);
	}

	/**
	 * 用默认UTF-8编码依次写入字符串长度（byte）及字符串
	 * 
	 * @param str
	 *            要写入的字符串
	 */
	public void writeUTFByte(String str) {
		if (str == null) {
			str = "";
		}
		byte[] bits = utf8toBytes(str);
		if (bits == null) {
			return;
		}
		int len = bits.length;
		writeByte(len);
		write(bits, 0, len);
	}

	/**
	 * 写入制定数量的byte值
	 * 
	 * @param b
	 * @param num
	 */
	public void writeBytes(final int b, final int num) {
		final byte[] array = new byte[num];
		for (int i = 0; i < array.length; i++) {
			array[i] = (byte) b;
		}
		write(array, 0, array.length);
	}

	/**
	 * 写入一个byte用来表示boolean 1：true，0：false
	 * 
	 * @param bool
	 */
	public void writeBoolean(boolean bool) {
		writeByte(bool ? 1 : 0);
	}

	/**
	 * 将指定的long数据提取全部8位到byte数组，高位在前，依次类推
	 * 
	 * @param num
	 *            long
	 * @return byte[]
	 */
	public static byte[] longToBytes(long num, boolean bool) {
		byte[] array = new byte[8];
		getDataBytes(num, array, bool);
		return array;
	}

	/**
	 * 将指定的long数据提取低4位（其他忽略）到byte数组，高位在前，依次类推
	 * 
	 * @param num
	 *            long
	 * @return byte[]
	 */
	public static byte[] intToBytes(long num, boolean bool) {
		byte[] array = new byte[4];
		getDataBytes(num, array, bool);
		return array;
	}

	/**
	 * 将指定的long数据提取低2位（其他忽略）到byte数组，高位在前，依次类推
	 * 
	 * @param num
	 *            long
	 * @return byte[]
	 */
	public static byte[] shortToBytes(long num, boolean bool) {
		byte[] array = new byte[2];
		getDataBytes(num, array, bool);
		return array;
	}

	/**
	 * 将指定的long数据内的array个数据写入array，从低位开始
	 * 
	 * @param num
	 *            要提取的数据
	 * @param array
	 *            缓存数组
	 * @param bool
	 *            true高位在前，false低位在前
	 */
	private static void getDataBytes(long num, byte[] array, boolean bool) {
		if (array == null) {
			return;
		}
		for (int i = 0; i < array.length; ++i) {
			array[bool ? array.length - 1 - i : i] = (byte) ((num >>> (i * 8)) & 0xff); // 最低位
		}
	}

	public void write(ByteArrayInputStream inputDate) {
		try {
			if (inputDate == null) {
				return;
			}
			byte[] array = new byte[inputDate.available()];
			if (array == null || array.length <= 0) {
				return;
			}
			write(array);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @Title: stringToUTFByte
	 * @Description: string --> utf-8 bytes
	 * @param _text
	 * @return
	 * @version: 2011-5-19 上午09:58:46
	 */
	public final static byte[] utf8toBytes(String text) {
		if (text == null) {
			return null;
		}
		try {
			return text.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return text.getBytes();
		}
	}

	@Override
	public String toString() {
		String str = "";
		str += "current bytes=" + this.size();
		return str;
	}
}
