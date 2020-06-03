package com.example.common_lib.java_bean;


import java.io.Serializable;

public class ImageBean implements Serializable {

    private int image_id; //图片ID
    private String image_url;//图片地址
    private String image_name;//图片名称
    private int width;//宽
    private int height;//高
    private String image_type;//图片类型
    private String image_describe;//图片描述
    private String insert_time;//插入时间

    public ImageBean(int image_id, String image_describe) {
        this.image_id = image_id;
        this.image_describe = image_describe;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public String getImage_describe() {
        return image_describe;
    }

    public void setImage_describe(String image_describe) {
        this.image_describe = image_describe;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }
}
