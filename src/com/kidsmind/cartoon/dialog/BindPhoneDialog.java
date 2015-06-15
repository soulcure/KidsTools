package com.kidsmind.cartoon.dialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.kidsmind.cartoon.KidsMindApplication;
import com.kidsmind.cartoon.R;
import com.kidsmind.cartoon.config.AppConfig;
import com.kidsmind.cartoon.entity.MobileBindRequest;
import com.kidsmind.cartoon.entity.ProtocolResponse;
import com.kidsmind.cartoon.entity.SmsCodeRequest;
import com.kidsmind.cartoon.entity.SmsCodeRequest.SmsParm;
import com.kidsmind.cartoon.uitls.GsonUtil;
import com.kidsmind.cartoon.uitls.HttpConnector;
import com.kidsmind.cartoon.uitls.IPostListener;

/**
 *
 */
public class BindPhoneDialog extends Dialog {

	private TextView getSecurityCode;

	private EditText bindPhone;

	private EditText bindPhoneCode;

	private TextView bind;

	private String phoneNumber;

	private SmsParm smsParm;

	private String phoneCode;


    /**
     * Description:构造函数
     *
     * @param context 当前上下文
     */


    public BindPhoneDialog(Context context, String vipName, String vipTime) {
        super(context, R.style.full_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_vip);
        initView();
    }


    private void initView() {
        getSecurityCode = (TextView) findViewById(R.id.get_security_code);
        bindPhone = (EditText) findViewById(R.id.bind_phone);
        bindPhoneCode = (EditText) findViewById(R.id.bind_phone_code);
        bind = (TextView) findViewById(R.id.bind);
        phoneNumber = bindPhone.getText().toString();
        phoneCode = bindPhoneCode.getText().toString();
        smsParm = SmsCodeRequest.SmsParm.bind;

        getSecurityCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
                Matcher m = p.matcher(phoneNumber);  //手机号正则匹配

                if (m.matches()) { 
                	reqSmsCode(phoneNumber, smsParm);
				}else{
					Toast.makeText(getContext(), "你输入的手机号有误", Toast.LENGTH_SHORT).show();
				}
            }
        });
        
        bind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (phoneNumber.length()>0 && phoneCode.length() > 0) { 
                	mobileBind(phoneNumber, phoneCode);
				}else{
					Toast.makeText(getContext(), "手机号或者验证码有误", Toast.LENGTH_SHORT).show();
				}
            }
        });

    }
    
    /**
     * 1.4 获取短信验证码
     *
     * @param phoneNum
     */
    private void reqSmsCode(String phoneNum, SmsCodeRequest.SmsParm parm) {
        String url = AppConfig.SMS_CODE;
        SmsCodeRequest request = new SmsCodeRequest();
        request.setMobile(phoneNum);
        request.setUsefor(parm);

        String json = request.toJsonString();
        HttpConnector.httpPost(url, json, new IPostListener() {

            @Override
            public void httpReqResult(String response) {
                // TODO Auto-generated method stub
                ProtocolResponse resp = GsonUtil.parse(response,
                        ProtocolResponse.class);
                if (resp.isSucess()) {
                }

            }

        });

    }
    
    /**
     * 1.5 绑定手机号
     *
     * @param phoneNum
     * @param smsCode
     */
    private void mobileBind(final String phoneNum, final String smsCode) {
        String url = AppConfig.MOBILE_BIND;
        MobileBindRequest request = new MobileBindRequest();
        request.setMobile(phoneNum);
        request.setValidateCode(smsCode);

        String token = ((KidsMindApplication) getOwnerActivity().getApplication()).getToken();
        request.setToken(token);

        String json = request.toJsonString();
        HttpConnector.httpPost(url, json, new IPostListener() {

            @Override
            public void httpReqResult(String response) {
                // TODO Auto-generated method stub
                ProtocolResponse resp = GsonUtil.parse(response,
                        ProtocolResponse.class);
                if (resp.isSucess()) {
                	
                } 

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
