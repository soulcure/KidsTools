package com.kidsmind.cartoon.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kidsmind.cartoon.R;

/****
 * @ClassName: CustomDialog
 * @Description: 一般对话框，只包含显示文本，一个动态修改的按钮,默认是确定按钮
 * @author
 * @date 2012-7-26 下午03:57:15
 * 
 */
public class CustomDialog extends Dialog {
	public static int SHOW_SERVER_DIAL = 1;
	public static int HIDE_SERVER_DIAL = 0;
	private Button btnConfirm;
	private Button btnCancel;
	private boolean mShowCancle = false;
	private boolean mShowConfirm = true;

	private CheckBox btn_pay_ridio;
	private TextView tvMsg;
	private CharSequence msg;
	private TextView tvServer; // 联系客服说明
	private ImageButton ibServer; // 联系客服按钮

	private CharSequence btnConfirmStr = null;
	private CharSequence btnCancelStr = null;

	/** 是否显示不在提示选项框 ***/
	private boolean pay_ridio = false;

	android.view.View.OnClickListener confirmCallBack;
	android.view.View.OnClickListener cancelCallBack;

	Context mContext;
	private int showServerDail = HIDE_SERVER_DIAL;

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:构造函数
	 * </p>
	 * 
	 * @param context
	 *            当前上下文
	 * @param msg
	 *            显示的文本信息
	 * @param theme
	 *            样式，主要是不规则背景图片等
	 */
	public CustomDialog(Context context, CharSequence msg) {
		super(context, R.style.dialog);
		this.msg = msg;
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.ft_customprogressdialog);
		initView();
	}

	private void initView() {
		// do something
	}

	private class ConfirmCallBack implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {

		}
	}

	private class CancelCallBack implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		if (cancelCallBack != null) {
			cancelCallBack.onClick(new View(mContext));
		}
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
}
