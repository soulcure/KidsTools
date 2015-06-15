package com.kidsmind.cartoon.entity;

/**
 * Created by colin on 2015/5/25.
 */
public class SerieItem {

    private int episodeId;
    private String title;
    private String description;
    private int sequence;
    private String imgUrl;
    private boolean langSwitch;
    private boolean favorite;


    public int getEpisodeId() {
        return episodeId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getSequence() {
        return sequence;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public boolean isLangSwitch() {
        return langSwitch;
    }

    public boolean isFavorite() {
        return favorite;
    }
}
