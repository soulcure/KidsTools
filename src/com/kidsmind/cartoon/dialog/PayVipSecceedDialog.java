package com.kidsmind.cartoon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.kidsmind.cartoon.R;

/**
 *
 */
public class PayVipSecceedDialog extends Dialog {

    private String getVipName;

    private String getVipTime;


    /**
     * Description:构造函数
     *
     * @param context 当前上下文
     */


    public PayVipSecceedDialog(Context context, String vipName, String vipTime) {
        super(context, R.style.full_dialog);
        getVipName = vipName;
        getVipTime = vipTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_secceed);
        initView();
    }


    private void initView() {
        TextView textView = (TextView) findViewById(R.id.iKonw);
        TextView vipName = (TextView) findViewById(R.id.vip_name);
        TextView vipTime = (TextView) findViewById(R.id.vip_time);

        vipName.setText(getVipName);

        vipTime.setText(getVipTime);

        textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                dismiss();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
