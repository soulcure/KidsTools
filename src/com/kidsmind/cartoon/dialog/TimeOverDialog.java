package com.kidsmind.cartoon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.kidsmind.cartoon.R;

/**
 *
 */
public class TimeOverDialog extends Dialog {

    /**
     * Description:构造函数
     *
     * @param context 当前上下文
     */
    public TimeOverDialog(Context context) {
        super(context, R.style.full_dialog);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_over_dialog);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        dismiss();
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            dismiss();
        }
        return true;
    }
}
