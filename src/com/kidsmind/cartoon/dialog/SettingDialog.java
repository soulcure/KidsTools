package com.kidsmind.cartoon.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author chenqy
 * 
 *         设置对话框
 */
public class SettingDialog extends AlertDialog implements
		android.view.View.OnClickListener {
	private Context mContext;
	private Button mButton;
	private static final String TAG = SettingDialog.class.getSimpleName();

	public SettingDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// setContentView(R.layout.setting_dialog);
		initView();
	}

	private void initView() {
		// do something
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		dismiss();
		// if (id == R.id.setting_main_cancel) {
		// dismiss();
		// }
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

}