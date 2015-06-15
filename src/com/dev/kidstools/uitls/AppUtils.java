package com.dev.kidstools.uitls;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Pattern;

import com.dev.kidstools.config.AppConfig;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;

public class AppUtils {
    private AppUtils() {
        throw new AssertionError();
    }

    /**
     * 获取当前应用的版本号
     *
     * @param context
     * @return String
     */
    public static String getVersion(Context context) {
        String packageName = context.getPackageName();

        return getAppVersion(context, packageName);

    }

    /**
     * 获取指定包名的应用版本号
     *
     * @param context
     * @param packageName
     * @return String
     */
    private static String getAppVersion(Context context, String packageName) {
        String ver = null;

        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            ver = pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ver;
    }

    /**
     * 安装APK
     */
    public static void installApk(Context context, String apkFilePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFilePath), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    /**
     * 写入SharedPreferences数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setStringSharedPreferences(Context context, String key,
                                                  String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                AppConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 读取SharedPreferences数据
     *
     * @return String value
     */
    public static String getStringSharedPreferences(Context context,
                                                    String key, String defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                AppConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaultValue);
    }

    /**
     * 写入SharedPreferences数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setBooleanSharedPreferences(Context context, String key,
                                                   boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                AppConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 读取SharedPreferences数据
     *
     * @return boolean value
     */
    public static boolean getBooleanSharedPreferences(Context context,
                                                      String key, boolean defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                AppConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, defaultValue);
    }

    /**
     * 写入SharedPreferences数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setIntSharedPreferences(Context context, String key,
                                               int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                AppConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 读取SharedPreferences数据
     *
     * @return int value
     */
    public static int getIntSharedPreferences(Context context, String key,
                                              int defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                AppConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, defaultValue);
    }

    /**
     * 写入SharedPreferences数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setLongSharedPreferences(Context context, String key,
                                                long value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(
                AppConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 读取SharedPreferences数据
     *
     * @return long value
     */
    public static long getLongSharedPreferences(Context context, String key,
                                                long defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                AppConfig.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getLong(key, defaultValue);
    }

    /**
     * 网络是否连通
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo nInfo = cm.getActiveNetworkInfo();
                if (nInfo != null) {
                    if (nInfo.isConnected()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo nInfo = cm.getActiveNetworkInfo();
                if (nInfo != null) {
                    return nInfo.getTypeName().toUpperCase(Locale.US)
                            .equals("WIFI");
                }
            }
        } catch (Exception e) {

        }
        return false;
    }


    /**
     * bytes to kb
     *
     * @param bytes
     * @return
     */
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal kilobyte = new BigDecimal(1024);
        float returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP).floatValue();
        return (returnValue + "  KB ");
    }


    /**
     * bytes to mb
     *
     * @param bytes
     * @return
     */
    public static String bytes2mb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP).floatValue();
        return (returnValue + "  MB ");
    }


    /**
     * 获取SD卡的路径
     */
    public static String getSdcardPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * md5验证
     *
     * @param file 文件
     * @param md5  md5验证码
     * @return
     */

    public static boolean checkFileMd5(File file, String md5) {
        boolean flag = false;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = fis.read(b)) != -1) {
                md.update(b, 0, len);
            }

            if (md5(md).equals(md5)) {
                flag = true;
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    /**
     * 获得md5验证码
     *
     * @param md5
     * @return
     */
    public static synchronized String md5(MessageDigest md5) {
        StringBuffer strBuf = new StringBuffer();
        byte[] result16 = md5.digest();
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f'};
        for (int i = 0; i < result16.length; i++) {
            char[] c = new char[2];
            c[0] = digit[result16[i] >>> 4 & 0x0f];
            c[1] = digit[result16[i] & 0x0f];
            strBuf.append(c);
        }

        return strBuf.toString();
    }

    public static String md5(String string) {
        if (StringUtils.isEmpty(string)) {
            return "";
        }
        try {
            return getMD5(string.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    private static String getMD5(byte[] source) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            StringBuffer result = new StringBuffer();
            for (byte b : md5.digest(source)) {
                result.append(Integer.toHexString((b & 0xf0) >>> 4));
                result.append(Integer.toHexString(b & 0x0f));
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 自适应式从图片获取Drawable，类似于把图片放入drawable目录一样
     *
     * @param context
     * @param pngFile
     * @param density
     * @return
     */
    public static Drawable getDrawableFromFile(Context context, File pngFile,
                                               int density) {
        Bitmap bmp = BitmapFactory.decodeFile(pngFile.getPath());
        if (bmp != null)
            bmp.setDensity(density);

        return new BitmapDrawable(context.getResources(), bmp);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawableFromFile(File pngFile) {

        return Drawable.createFromPath(pngFile.getPath());
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawableFromFile(String filePath) {

        return Drawable.createFromPath(filePath);
    }

    /**
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is).copy(
                    Bitmap.Config.ARGB_8888, true);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    /**
     * 指定编码获取properties文件中的属性值（解决中文乱码问题）
     *
     * @param properties java.util.Properties
     * @param key        属性key
     * @return
     */
    public static String getProperty(Properties properties, String key, String encoding)
            throws UnsupportedEncodingException {
        //param check
        if (properties == null)
            return null;

        //如果此时value是中文，则应该是乱码
        String value = properties.getProperty(key);
        if (value == null)
            return null;

        //编码转换，从ISO8859-1转向指定编码
        value = new String(value.getBytes("ISO8859-1"), encoding);
        return value;
    }


    public static boolean isMobileNum(String num) {
        return Pattern.compile("^1[3458]\\d{9}$").matcher(num).matches();
    }

}
