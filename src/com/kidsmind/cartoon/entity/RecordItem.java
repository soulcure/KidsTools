package com.kidsmind.cartoon.entity;


/**
 * Created by john on 2015/5/28.
 */
public class RecordItem {

    private int episodeId;
    private String episodeName;
    private int serieId;
    private String serieName;
    private String imgUrl;
    private String playTime;
    private int sequence;
    private String[] preferences;

    public int getEpisodeId() {
        return episodeId;
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

    public String getImgUrl() {
        return imgUrl;
    }

    public String getPlayTime() {
        return playTime;
    }

    public int getSequence() {
        return sequence;
    }

    public String[] getPreferences() {
        return preferences;
    }
}
