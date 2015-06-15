package com.kidsmind.cartoon.uitls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class StreamUtils {


    /**
     * 将输入流转换为字节数组
     * @param inStream
     * @return
     * @throws IOException
     */
    public static byte[] read(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {

            outStream.write(buffer, 0, len);

        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 将输入流转换为 UTF-8 字符串
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readStream(InputStream inputStream) throws IOException {
        byte[] buffer = read(inputStream);
        return new String(buffer, 0, buffer.length, Charset.forName("UTF-8"));
    }
    
   
    
}
