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

    public String getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public String getUpdate_time() {
        return update_time;
    }
}
