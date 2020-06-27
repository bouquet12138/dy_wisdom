package com.example.common_lib.java_bean;

public class AboutUsBean {

    public static final String UN_ENABLED = "0";
    public static final String ENABLED = "1";

    private int about_us_id;//关于我们

    private String status;
    private String content;
    private String insert_time;
    private String update_time;


    public int getAbout_us_id() {
        return about_us_id;
    }

    public void setAbout_us_id(int about_us_id) {
        this.about_us_id = about_us_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
