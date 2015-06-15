package com.kidsmind.cartoon.entity;

import com.kidsmind.cartoon.uitls.GsonUtil;

/**
 * Created by john on 2015/6/2.
 */
public class CancelFavRequest extends ProtocolRequest {

    private String token;
    private int profileId;
    private int[] episodeId;


    public void setToken(String token) {
        this.token = token;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public void setEpisodeId(int[] episodeId) {
        this.episodeId = episodeId;
    }

    @Override
    public String toJsonString() {
        return GsonUtil.format(this);
    }


}
