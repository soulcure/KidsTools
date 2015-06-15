package com.kidsmind.cartoon;

import com.kidsmind.cartoon.uitls.AppUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class KidsMindActivity extends Activity {

    private static final String TAG = KidsMindActivity.class.getSimpleName();

    public static final int REQUEST_MODE_A = 0;
    public static final int RESULT_MODEL_A_BACK = 1;

    /* 保存返回键被按下的时间 */
    private long mPressedTime;

    private static final int UITHREAD_INIT_COMPLETE = 0;
    private static final int HANDLERTHREAD_INIT_CONFIG_START = 1;

    private UIHandler mUIHandler;
    private ProcessHandler mProcessHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kids_mind);

        initHandler();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!AppUtils.isNetworkConnected(this)) {
            showNetworkError(this);
        }
    }

    /**
     * 主线程处理handler
     *
     * @author Administrator
     */
    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UITHREAD_INIT_COMPLETE:
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 子线程handler,looper
     *
     * @author Administrator
     */
    private class ProcessHandler extends Handler {
        private Handler mHandler;

        public ProcessHandler(Looper looper, Handler restoreHandler) {
            super(looper);
            this.mHandler = restoreHandler;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLERTHREAD_INIT_CONFIG_START:

                    mHandler.sendEmptyMessage(UITHREAD_INIT_COMPLETE); // 通知UI线程
                    break;
                default:
                    break;
            }

        }

    }

    /**
     * 线程初始化
     */
    private void initHandler() {
        if (mUIHandler == null) {
            mUIHandler = new UIHandler();
        }
        if (mProcessHandler == null) {
            HandlerThread handlerThread = new HandlerThread(
                    "handler looper Thread");
            handlerThread.start();
            mProcessHandler = new ProcessHandler(handlerThread.getLooper(),
                    mUIHandler);
        }
    }

    @Override
    public void onBackPressed() {

        if ((System.currentTimeMillis() - mPressedTime) < 2000) {
            ImageLoader.getInstance().stop();
            finish();
        } else {
            mPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }
    }

    public static void showNetworkError(final Context context) {
        // 网络连接不上的提示操作
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false)
                // 屏蔽返回键
                .setTitle(context.getString(R.string.prompt))
                .setMessage(context.getString(R.string.check_connect))
                .setPositiveButton(context.getString(R.string.setting_network),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent intent = new Intent(
                                        android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                context.startActivity(intent);
                                arg0.dismiss();

                            }
                        })
                .setNegativeButton(context.getString(R.string.empress_retry),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                android.os.Process
                                        .killProcess(android.os.Process.myPid());
                            }
                        });
        builder.show();
    }
}
