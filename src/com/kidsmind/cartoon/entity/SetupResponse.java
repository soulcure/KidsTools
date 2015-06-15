
package com.kidsmind.cartoon.entity;

import java.util.Locale;

/**
 *
 */
public class SetupResponse {

    private int code; // 错误类型
    private String message; // 错误信息

    private SetupInfo data; // 详细信息


    public boolean isSucess() {
        return code == 200 ? true : false;
    }

    public boolean isModelA(){
        boolean res=false;
        String model=data.getUiModel().toUpperCase(Locale.US);
        if(model.equals("A")){
            res=true;
        }
        return res;
    }

    public boolean isModelB(){
        boolean res=false;
        String model=data.getUiModel().toUpperCase(Locale.US);
        if(model.equals("B")){
            res=true;
        }
        return res;
    }

    public String getMessage() {
        return message;
    }

    public SetupInfo getData() {
        return data;
    }
}
