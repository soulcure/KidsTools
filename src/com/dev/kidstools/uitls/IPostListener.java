package com.dev.kidstools.uitls;

import android.widget.Toast;

import com.dev.kidstools.config.AppConfig;

/**
 * Htpp连接地址，参数，返回数据管理接口
 */
public abstract class IPostListener {
    public static final String TAG = IPostListener.class.getSimpleName();

    /**
     * 传回的数据处理
     *
     * @param response
     */

    public abstract void httpReqResult(final String response);

    public void doError() {
        //Log.e(TAG,"http post error!");
        if(AppConfig.debug){
            Toast.makeText(AppConfig.AppContext, "服务器协议返回为空", Toast.LENGTH_SHORT).show();
        }

    }

}
