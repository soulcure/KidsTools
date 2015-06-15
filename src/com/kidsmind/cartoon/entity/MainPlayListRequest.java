package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.uitls.GsonUtil;

/**
 * Created by colin on 2015/5/25.
 */
public class MainPlayListRequest extends ProtocolRequest {


    private String token;
    private int rowCount; //默认 4
    private int startIndex;
    private int offset;   //默认 10

    public void setToken(String token) {
        this.token = token;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toJsonString() {
        return GsonUtil.format(this);
    }


}
