/**
 *
 */
package com.kidsmind.cartoon.entity;


public class DoYouLikeResponse {

    public int code; // 错误类型
    public String message; // 错误信息


    public boolean isSucess() {
        return code == 200 ? true : false;
    }


}
