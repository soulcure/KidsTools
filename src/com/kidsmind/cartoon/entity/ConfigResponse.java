/**
 *
 */
package com.kidsmind.cartoon.entity;


import java.util.List;

public class ConfigResponse {

    public int code; // 错误类型
    public String message; // 错误信息

    public ConfigInfo data; // 详细信息

    public boolean isSucess() {
        return code == 200 ? true : false;
    }
    

    public String getMessage() {
        return message;
    }


    public List<ConfigItem> getList() {
        return data.getList();
    }
}
