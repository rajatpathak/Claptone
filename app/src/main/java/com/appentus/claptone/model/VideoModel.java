package com.appentus.claptone.model;

public class VideoModel {

    private String title, vidId, year;

    public VideoModel() {
    }

    public VideoModel(String title) {
        this.title = title;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVidId() {
        return vidId;
    }

    public void setVidId(String vidId) {
        this.vidId = vidId;
    }

}
