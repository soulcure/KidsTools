package com.kidsmind.cartoon.entity;

import java.util.List;

/**
 * Created by john on 2015/5/28.
 */
public class RecordInfo {
    private List<RecordItem> list; //获取播放记录 返回
    private int counts; // 创建播放记录数量 返回

    public List<RecordItem> getList() {
        return list;
    }
    public int getCounts() {
        return counts;
    }
}
