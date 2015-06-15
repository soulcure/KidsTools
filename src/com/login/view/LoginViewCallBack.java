package com.login.view;

import android.os.Message;

/***
 * 
 * @author 蒋殷芝
 * 
 */
public abstract class LoginViewCallBack {
	/** 登录成功所需要做操作的事件 */
	public void loginsuccessed(Message msg) {
	};

	/** 登录失败所需要做操作的事件 */
	public void loginFailed(Message msg) {
	};

	/** 登录界面返回按钮操作的事件 */
	public void btnBackClick() {
	};

	/**loadingUI取消按钮**/
	public void btnCancel(){
		
	}
	
	/** 注销的事件 */
	public void logout() {
	};

	/** 开始登录的事件 */
	public void loginAction() {
	};

	/** 是否有本地账号信息 */
	public void nativeLoginInfo(boolean isHasNativeLoginInfo) {
	}

	public void loginsuccessed() {
		// TODO Auto-generated method stub
		
	};
}
