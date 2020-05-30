package com.example.common_lib.java_bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FlashBean implements Serializable {

    private int flash_id;
    private int user_id;
    private String title;
    private long reading_volume;
    private String public_time;

    private List<FlashContentBean> content_list = new ArrayList<>();

    public FlashBean(String title, long reading_volume, String public_time) {
        this.title = title;
        this.reading_volume = reading_volume;
        this.public_time = public_time;
    }

    public int getFlash_id() {
        return flash_id;
    }

    public void setFlash_id(int flash_id) {
        this.flash_id = flash_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getReading_volume() {
        return reading_volume;
    }

    public void setReading_volume(long reading_volume) {
        this.reading_volume = reading_volume;
    }

    public String getPublic_time() {
        return public_time;
    }

    public void setPublic_time(String public_time) {
        this.public_time = public_time;
    }


    public List<FlashContentBean> getContent_list() {
        return content_list;
    }

    public void setContent_list(List<FlashContentBean> content_list) {
        this.content_list = content_list;
    }

    @Override
    public String toString() {
        return "FlashBean{" +
                "flash_id=" + flash_id +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", reading_volume=" + reading_volume +
                ", public_time='" + public_time + '\'' +
                ", content_list=" + content_list +
                '}';
    }
}
