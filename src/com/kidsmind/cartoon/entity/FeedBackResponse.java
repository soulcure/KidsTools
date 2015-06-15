
package com.kidsmind.cartoon.entity;

/**
 *
 */
public class FeedBackResponse {

    private int code; // 错误类型
    private String message; // 错误信息

    private String data; // 详细信息


    public boolean isSucess() {
        return code == 200 ? true : false;
    }


    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }
}
