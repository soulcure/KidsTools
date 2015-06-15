package com.kidsmind.cartoon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.kidsmind.cartoon.KidsMindApplication;
import com.kidsmind.cartoon.R;
import com.kidsmind.cartoon.config.AppConfig;
import com.kidsmind.cartoon.entity.DoYouLikeRequest;
import com.kidsmind.cartoon.entity.DoYouLikeResponse;
import com.kidsmind.cartoon.uitls.GsonUtil;
import com.kidsmind.cartoon.uitls.HttpConnector;
import com.kidsmind.cartoon.uitls.IPostListener;

import java.lang.ref.WeakReference;

/**
 * @author chenqy
 *         <p/>
 *         设置对话框
 */
public class DoYouLikeDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = DoYouLikeDialog.class.getSimpleName();

    private static final int HANDLER_COUNT_DOWN = 0;

    private Context mContext;

    /**
     * 是否喜欢统计枚举
     */
    public enum Select {
        like, dislike, skip
    }

    private int mEpisodeId;
    private int mPercentage;
    private int timeCount = 5;

    private TextView timeOver;

    private TimeHandler mHandler;

    public DoYouLikeDialog(Context context) {
        super(context, R.style.full_dialog);
        mContext = context;
    }


    public void setEpisodeId(int episodeId) {
        mEpisodeId = episodeId;
    }

    public void setPercentage(int percentage) {
        mPercentage = percentage;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doyoulike_activity);
        mHandler = new TimeHandler(this);

        initView();
    }

    private void initView() {
        timeOver = (TextView) findViewById(R.id.time_over);

        findViewById(R.id.btn_like).setOnClickListener(this);
        findViewById(R.id.btn_unlike).setOnClickListener(this);

        startCountDown();

    }


    private void startCountDown() {
        Message msg = mHandler.obtainMessage(HANDLER_COUNT_DOWN);
        if (!mHandler.hasMessages(HANDLER_COUNT_DOWN)) {
            mHandler.sendMessageDelayed(msg, 1000);
        }
    }

    /**
     * @param episodeId
     * @param rating     喜欢或不喜欢
     * @param percentage
     */
    private void doYoulike(int episodeId, Select rating, int percentage) {
        String url = AppConfig.DO_YOU_LIKE;
        DoYouLikeRequest request = new DoYouLikeRequest();

        String token = ((KidsMindApplication) mContext.getApplicationContext()).getToken();
        request.setToken(token);

        int profileId = ((KidsMindApplication) mContext.getApplicationContext()).getProfileId();
        request.setProfileId(profileId);

        request.setEpisodeId(episodeId);
        request.setRating(rating);
        request.setPercentageViewed(percentage);

        String json = request.toJsonString();
        HttpConnector.httpPost(url, json, new IPostListener() {

            @Override
            public void httpReqResult(String response) {
                // TODO Auto-generated method stub
                DoYouLikeResponse resp = GsonUtil.parse(response,
                        DoYouLikeResponse.class);
                if (resp.isSucess()) {

                }

            }

        });
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_like) {
            doYoulike(mEpisodeId, Select.like, mPercentage);

        } else if (id == R.id.btn_unlike) {
            doYoulike(mEpisodeId, Select.dislike, mPercentage);

        }

        dismiss();
    }


    private class TimeHandler extends Handler {
        private final WeakReference<DoYouLikeDialog> mTarget;

        TimeHandler(DoYouLikeDialog target) {
            mTarget = new WeakReference<DoYouLikeDialog>(target);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_COUNT_DOWN:
                    timeOver.setText(String.valueOf(timeCount));
                    if (timeCount <= 0) {
                        doYoulike(mEpisodeId, Select.skip, mPercentage);
                        if (mHandler.hasMessages(HANDLER_COUNT_DOWN)) {
                            mHandler.removeMessages(HANDLER_COUNT_DOWN);
                        }
                        dismiss();
                    } else {
                        timeCount--;
                        startCountDown();
                    }

                    break;
                default:
                    break;
            }


        }
    }

}