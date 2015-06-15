
package com.kidsmind.cartoon.entity;

import java.util.List;

/**
 *
 */
public class FavoriteResponse {

    private int code; // 错误类型
    private String message; // 错误信息

    private FavoriteInfo data; // 详细信息


    public boolean isSucess() {
        return code == 200 ? true : false;
    }


    public String getMessage() {
        return message;
    }

    public int getCounts() {
        return data.getCounts();
    }


    public List<FavoriteItem> getList() {
        return data.getList();
    }

}
