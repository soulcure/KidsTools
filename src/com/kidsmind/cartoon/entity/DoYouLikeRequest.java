package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.dialog.DoYouLikeDialog;
import com.kidsmind.cartoon.uitls.GsonUtil;

/**
 * Created by colin on 2015/5/25.
 */
public class DoYouLikeRequest extends ProtocolRequest {


    private String token;
    private int profileId;   //
    private int episodeId;   //
    private DoYouLikeDialog.Select rating;
    private int percentageViewed;


    public void setToken(String token) {
        this.token = token;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public void setRating(DoYouLikeDialog.Select rating) {
        this.rating = rating;
    }

    public void setPercentageViewed(int percentageViewed) {
        this.percentageViewed = percentageViewed;
    }

    @Override
    public String toJsonString() {
        return GsonUtil.format(this);
    }


}
