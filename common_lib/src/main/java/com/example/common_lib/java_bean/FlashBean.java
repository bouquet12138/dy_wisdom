package com.example.common_lib.java_bean;


import java.io.Serializable;
import java.util.List;

public class FlashBean implements Serializable {

    private int flash_id;
    private int user_id;
    private String title;
    private int reading_volume;
    private String insert_time;
    private String update_time;

    private List<FlashContentBean> content_list;

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

    public int getReading_volume() {
        return reading_volume;
    }

    public void setReading_volume(int reading_volume) {
        this.reading_volume = reading_volume;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<FlashContentBean> getContent_list() {
        return content_list;
    }

    public void setContent_list(List<FlashContentBean> content_list) {
        this.content_list = content_list;
    }
}
