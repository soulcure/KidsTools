package com.kidsmind.cartoon.entity;

import java.util.List;

/**
 * Created by colin on 2015/5/25.
 */
public class PlayUrlInfo {

    private int profileId; //
    private String episodeName;
    private int serieId;
    private String serieName;
    private List<String> preferences; //
    private List<PlayUrlItem> langUrl; //


    public int getProfileId() {
        return profileId;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public int getSerieId() {
        return serieId;
    }

    public String getSerieName() {
        return serieName;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public List<PlayUrlItem> getLangUrl() {
        return langUrl;
    }
}
