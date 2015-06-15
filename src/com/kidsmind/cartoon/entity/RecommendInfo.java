package com.kidsmind.cartoon.entity;

import java.util.List;

/**
 * Created by colin on 2015/5/25.
 */
public class RecommendInfo  {
    private int profileId; //
    private List<RecommendItem> recommendation; //


    public int getProfileId() {
        return profileId;
    }

    public List<RecommendItem> getRecommendation() {
        return recommendation;
    }
}
