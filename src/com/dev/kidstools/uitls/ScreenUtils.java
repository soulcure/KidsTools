package com.dev.kidstools.uitls;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * ScreenUtils
 * <ul>
 * <strong>Convert between dp and sp</strong>
 * <li>{@link ScreenUtils#dpToPx(Context, float)}</li>
 * <li>{@link ScreenUtils#pxToDp(Context, float)}</li>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-14
 */
public class ScreenUtils {

    private ScreenUtils() {
        throw new AssertionError();
    }


    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }

        return dp * getDensity(context);
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / getDensity(context);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 每英寸像素数
     *
     * @return
     */
    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }


    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    /**
     * 一般是19寸以上是电视
     *
     * @param context
     * @return
     */
    public static boolean isTv(Context context) {
        double size = getScreenPhysicalSize(context);
        return size > 19;
    }


    /**
     * 一般是7寸以上是平板
     * 一般是19寸以上是电视
     *
     * @param context
     * @return
     */
    public static double getScreenPhysicalSize(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
        return diagonalPixels / (160 * dm.density);
    }
}
