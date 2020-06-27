package com.example.common_lib.java_bean;

public class AdvertBean {

    private int advert_id;
    private int user_id;
    private String title;
    private int reading_volume;
    private String insert_time;
    private String update_time;


    public int getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(int advert_id) {
        this.advert_id = advert_id;
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
}
