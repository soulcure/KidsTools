package com.login.view;


import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ListView;

public class AccountList extends LinearLayout {
	private Context mContext;
	private LinearLayout.LayoutParams mLp;
	
	private ListView mAccoutList;
	
	public AccountList(Context context) {
		super(context);
		mContext=context;
		LinearLayout container = this;
		
		mLp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		
		//container.setBackgroundResource(R.drawable.pw_account_center);
		mAccoutList=new ListView(mContext);
		mAccoutList.setCacheColorHint(0);
		mAccoutList.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
		//mAccoutList.setDivider(mContext.getResources().getDrawable(R.drawable.pw_account_divider));
		container.addView(mAccoutList,mLp);
	}
	
	
	/**
	 * 设置listview 分隔线
	 * @param resId
	 */
	public void setDivider(int resId){
		if(mAccoutList!=null){
			mAccoutList.setDivider(mContext.getResources().getDrawable(resId));
		}
	}
	
	
	public ListView getAccoutList(){
		return mAccoutList;
	}
}
