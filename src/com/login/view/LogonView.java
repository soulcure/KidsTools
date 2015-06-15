package com.login.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class LogonView extends RelativeLayout{

	public static final int HANDLER_CHOOSE_ACCOUNT=0;   //选择账号handler

	private Context mContext;
	private  Handler mPopHandler;
	private Drawable popupWindwPush=null;  //Drawable popup弹出指示 箭头向下
	private Drawable popupWindwPull=null;  //Drawable popup缩回指示 箭头向上

	private PopupWindow popupWindow;     //账号弹出pop框 

	private EditText etAccoutInpunt;         //账号输入框
	private EditText etPassWordInput;        //密码输入框
	private TextView tvForgetPass;//忘记密码

	private ImageView imgLogo;        //移动棋牌图片logo  
	private Button btnLogin;          //登录
	private Button btnBinding;        //绑定按钮
	private ImageButton btnChoose;    //账号选择按钮

	private AccountList accountList;  //popup 弹出账号选择listview 控件

	private RelativeLayout input;     //中间整体UI布局
	private LinearLayout linearInput; //账号和密码框

	@SuppressLint("HandlerLeak")
	public LogonView(Context context) {
		super(context);
		mContext=context;

		// 用来处理选中或者删除下拉项消息
		mPopHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case HANDLER_CHOOSE_ACCOUNT:
					break;
				default:
					break;
				}

			}
		};


		RelativeLayout container = this;

		//container.setBackgroundResource(R.drawable.bg);  //设置整体背景，开发接口 外部设置

		imgLogo=new ImageView(context);
		imgLogo.setId(GlobalViewId.NORMAL_LOGO);
		//imgLogo.setImageResource(R.drawable.normal_logo);//设置logo图片，开发接口 外部设置

		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT); 
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		lp.topMargin=30;   //设置上间距 
		container.addView(imgLogo, lp);



		input=new RelativeLayout(mContext);
		//input.setBackgroundResource(R.drawable.bg_content);
		RelativeLayout.LayoutParams inputLp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		inputLp.topMargin=15;   //设置上间距 
		inputLp.addRule(RelativeLayout.BELOW,GlobalViewId.NORMAL_LOGO);
		inputLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		
		linearInput=new LinearLayout(mContext);
		linearInput.setId(GlobalViewId.LINEAR_INPUT);
		linearInput.setOrientation(LinearLayout.VERTICAL);

		linearInput.addView(getAccoutInput(mContext));

		linearInput.addView(getPassWordInput(mContext));
		RelativeLayout.LayoutParams acountLp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		acountLp.topMargin=20;
		acountLp.leftMargin = 40;
		acountLp.rightMargin = 40;
		input.addView(linearInput,acountLp);

		//忘记密码文本
		RelativeLayout.LayoutParams forgetPassLp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		forgetPassLp.addRule(RelativeLayout.BELOW, GlobalViewId.LINEAR_INPUT);
		forgetPassLp.addRule(RelativeLayout.ALIGN_RIGHT,GlobalViewId.LINEAR_INPUT);
		tvForgetPass = new TextView(context);
		tvForgetPass.setId(GlobalViewId.TV_FORGET_PASS);
		tvForgetPass.setText(Html.fromHtml("<u>忘记密码?</u>"));
		tvForgetPass.setTextColor(Color.parseColor("#ffffff"));
		//屏蔽忘记密码
		tvForgetPass.setVisibility(View.INVISIBLE);
		input.addView(tvForgetPass,forgetPassLp);
		

		RelativeLayout.LayoutParams loginLp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		loginLp.topMargin=30;
		loginLp.bottomMargin = 20;
		loginLp.addRule(RelativeLayout.BELOW,GlobalViewId.TV_FORGET_PASS);
		loginLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		input.addView(getLoginInput(mContext),loginLp);

		
		
//		//客服电话
//		btnServer=new ImageButton(mContext);
//		btnServer.setVisibility(View.INVISIBLE);
//		//imgButtomLogo.setBackgroundResource(R.drawable.common_chinamobile_logo);
//		RelativeLayout.LayoutParams serverLp = new RelativeLayout.LayoutParams(
//				RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT); 
//		serverLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		serverLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//		serverLp.rightMargin=20;
//		serverLp.bottomMargin=20;
//		container.addView(btnServer, serverLp);
		container.addView(input, inputLp);
		setLastAccout();
		
		
//
//		//版本信息
//		tvVersion = new TextView(mContext);
//		tvVersion.setId(GlobalViewId.TV_VERSION);
//		tvVersion.setTextColor(0xffffffff);
//		tvVersion.setTextSize(20);
//		RelativeLayout.LayoutParams mLp=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
//				RelativeLayout.LayoutParams.WRAP_CONTENT);
//		mLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//		mLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//		mLp.leftMargin=20;
//		mLp.bottomMargin=20;
//		container.addView(tvVersion, mLp);

	}







	/**
	 * 获取账号输入框账号
	 * @return
	 */
	public String getAccoutInput(){
		String accout="";
		if(etAccoutInpunt!=null){
			accout=etAccoutInpunt.getText().toString();
		}
		return accout;
	}



	/**
	 * 获取密码输入框密码
	 * @return
	 */
	public String getPassWordInput(){
		String password="";
		if(etPassWordInput!=null){
			password=etPassWordInput.getText().toString();
		}
		return password;
	}


	/**
	 * 设置背景
	 * @param resId
	 */
	public void setBackgroundRes(int resId){
		this.setBackgroundResource(resId);
	}


	/**
	 * 设置logo图片
	 * @param resId
	 */
	public void setImgLogo(int resId){
		if(imgLogo!=null){
			imgLogo.setImageResource(resId);
		}
	}


	/**
	 * 设置中间输入部分整体控件背景
	 * @param resId
	 */
	public void setBackgroudInput(int resId){
		if(input!=null){
			input.setBackgroundResource(resId);
		}
	}


	/**
	 * 设置账号和密码输入控件背景
	 * @param resId
	 */
	public void setBackgroudLinearInput(int resId){
		if(linearInput!=null){
			linearInput.setBackgroundResource(resId);
		}
	}


	/**
	 * 设置popup弹出按钮控件背景
	 * @param resId
	 */
	public void setBackgroudBtnChoose(int resId){
		if(btnChoose!=null){
			btnChoose.setBackgroundResource(resId);
		}
	}



	/**
	 * 设置忘记密码按键监听器
	 * @param callback
	 */
	public void setBtnGetPassWordOnClickCallBack(View.OnClickListener callback){
		if(tvForgetPass!=null){
			tvForgetPass.setOnClickListener(callback);
		}
	}


	/**
	 * 设置登录按键控件背景
	 * @param drawable
	 */
	public void setBackgroundBtnLogin(Drawable drawable){
		if(btnLogin!=null){
			btnLogin.setBackgroundDrawable(drawable);
		}
	}


	/**
	 * 设置登录按键监听器
	 * @param callback
	 */
	public void setBtnLoginOnClickCallBack(View.OnClickListener callback){
		if(btnLogin!=null){
			btnLogin.setOnClickListener(callback);
		}
	}


	/**
	 * 设置绑定按钮背景
	 * @param drawable
	 */
	public void setBackgroundBtnBinding(int resid){
		if(btnBinding!=null){
			btnBinding.setBackgroundResource(resid);
		}
	}
	/**
	 * 设置绑定按钮回调
	 * @param callback
	 */
	public void setBtnBindingOnClickCallback(View.OnClickListener callback){
		if(btnBinding!=null)
			btnBinding.setOnClickListener(callback);
	}

	/**
	 * 设置popup弹出框 listview控件背景
	 * @param resId
	 */
	public void setAccountListBackGround(int resId){
		if(accountList!=null){
			accountList.setBackgroundResource(resId);
		}
	}

	/**
	 * 设置popup弹出框 listview控件分隔线
	 * @param resId
	 */
	public void setAccountListDivider(int resId){
		if(accountList!=null){
			accountList.setDivider(resId);
		}
	}



	/**
	 *  popup弹出指示 箭头向下
	 */
	public void setPopupWindwPush(Drawable drawable){
		popupWindwPush=drawable;
		if(btnChoose!=null){
			btnChoose.setImageDrawable(drawable);
		}
	}


	/**
	 *  popup缩回指示 箭头向上
	 */
	public void setPopupWindwPull(Drawable drawable){
		popupWindwPull=drawable;
	}




	/**
	 * 获取账号输入部分控件
	 * @param context
	 * @return
	 */
	private LinearLayout getAccoutInput(Context context){
		LinearLayout linearAccout=new LinearLayout(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		linearAccout.setLayoutParams(layoutParams);
		linearAccout.setPadding(13, 6, 13, 6);
		linearAccout.setOrientation(LinearLayout.HORIZONTAL);


		TextView tvAccout=new TextView(context);
		tvAccout.setPadding(10, 0, 30, 0);
		tvAccout.setText("账号");
		tvAccout.setTextSize(16);
		tvAccout.setTextColor(Color.parseColor("#766457"));
		linearAccout.addView(tvAccout);

		etAccoutInpunt=new EditText(context);
		etAccoutInpunt.setHint("请输入账号");
		etAccoutInpunt.setSingleLine();
		etAccoutInpunt.setTextSize(16);
		etAccoutInpunt.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);

		//linearAccout.setLayoutParams(layoutParams);

		etAccoutInpunt.setBackgroundDrawable(null);


		linearAccout.addView(etAccoutInpunt);

		btnChoose=new ImageButton(context);
		LinearLayout.LayoutParams btnlayoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		btnlayoutParams.leftMargin=70;
		//btnChoose.setBackgroundResource(R.drawable.btn_account_pull);
		initPopuWindow();
		btnChoose.setBackgroundDrawable(null);
		btnChoose.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(popupWindow.isShowing()){
					popupWindow.dismiss();
				}else{
					popupWindwShowing(v);
				}
			}

		});
		linearAccout.addView(btnChoose,btnlayoutParams);

		return linearAccout;
	}


	/**
	 * 获取密码输入部分控件
	 * @param context
	 * @return
	 */
	private LinearLayout getPassWordInput(Context context){
		LinearLayout linearAccout=new LinearLayout(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		linearAccout.setPadding(13, 6, 13, 6);
		linearAccout.setOrientation(LinearLayout.HORIZONTAL);

		TextView tvPassWord=new TextView(context);
		tvPassWord.setText("密码");
		tvPassWord.setPadding(10, 0, 30, 0);
		tvPassWord.setTextSize(16);
		tvPassWord.setTextColor(Color.parseColor("#766457"));
		linearAccout.addView(tvPassWord);

		etPassWordInput=new EditText(context);
		etPassWordInput.setHint("请输入密码");
		etPassWordInput.setSingleLine();
		etPassWordInput.setTextSize(16);
		etPassWordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		etPassWordInput.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		etPassWordInput.setBackgroundDrawable(null);


		linearAccout.addView(etPassWordInput,layoutParams);

		return linearAccout;
	}
	


	/**
	 * 获取忘记密码 登录按钮部分控件
	 * @param context
	 * @return
	 */
	private LinearLayout getLoginInput(Context context){
		LinearLayout linearAccout=new LinearLayout(context);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		linearAccout.setOrientation(LinearLayout.HORIZONTAL);
		linearAccout.setLayoutParams(layoutParams);
		linearAccout.setPadding(0, 0, 0, 20);


		btnLogin=new Button(context);
		
		btnBinding = new Button(context);
		
		
		layoutParams.rightMargin = 10;
		linearAccout.addView(btnBinding, layoutParams);
		
		layoutParams.rightMargin = 10;
		layoutParams.leftMargin = 30;
		linearAccout.addView(btnLogin,layoutParams);
		return linearAccout;
	}


	/**
	 * 初始化popup
	 */
	@SuppressWarnings("deprecation")
	private void initPopuWindow() {
		accountList=new AccountList(mContext);

		ListView pwLoginList = accountList.getAccoutList();
		popupWindow=new PopupWindow(mContext);
		popupWindow.setContentView(accountList);
		popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setWidth(250);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}








	private void popupWindwShowing(final View v) {
		
	}


	/**
	 * 隐藏输入框
	 */
	public void hideInputKeyBoard(){
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			if (imm.hideSoftInputFromWindow(getWindowToken(), 0)) {
				return;
			}
		}
	}




	/**
	 * 自动填充最后一次账号登录信息
	 */
	private void setLastAccout(){
		
	}


}