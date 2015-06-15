package com.kidsmind.cartoon.entity;


/**
 * Created by colin on 2015/5/25.
 */
public class FavoriteItem {

    private int episodeId;
    private String episodeName;
    private int serieId;

    private String serieName;
    private String langSwitch;
    private String imgUrl;


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

    public String getLangSwitch() {
        return langSwitch;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
