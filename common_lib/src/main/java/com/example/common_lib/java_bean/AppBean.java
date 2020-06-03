package com.example.common_lib.java_bean;

public class AppBean {

    public static final String NOT_UPDATE = "0";
    public static final String UPDATE = "1";
    public static final String FORCE_UPDATE = "2";

    private int app_id;
    private String insert_time;
    private int app_size;
    private String update_status;
    private String app_md5;
    private String app_name;
    private String app_icon;
    private String app_url;
    private String app_describe;
    private int version_code;
    private String version_name;

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public int getApp_size() {
        return app_size;
    }

    public void setApp_size(int app_size) {
        this.app_size = app_size;
    }

    public String getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    public String getApp_md5() {
        return app_md5;
    }

    public void setApp_md5(String app_md5) {
        this.app_md5 = app_md5;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(String app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_url() {
        return app_url;
    }

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    public String getApp_describe() {
        return app_describe;
    }

    public void setApp_describe(String app_describe) {
        this.app_describe = app_describe;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }
}
