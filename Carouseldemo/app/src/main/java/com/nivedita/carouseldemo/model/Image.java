package com.nivedita.carouseldemo.model;

/**
 * Created by PUNEETU on 03-06-2017.
 */

public class Image {

    private String title;
    private String thumbNail;

    public Image(String title, String thumbnail) {
        this.title = title;
        this.thumbNail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }


}
