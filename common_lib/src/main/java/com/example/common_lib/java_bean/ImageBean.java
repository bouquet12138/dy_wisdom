package com.example.common_lib.java_bean;

import java.io.Serializable;

public class ImageBean implements Serializable {

    private int image_id;
    private String image_url;
    private String image_name;
    private int width;
    private int height;
    private String image_type;
    private String image_describe;

    public ImageBean(int image_id, String image_describe) {
        this.image_id = image_id;
        this.image_describe = image_describe;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "image_id=" + image_id +
                ", image_url='" + image_url + '\'' +
                ", image_name='" + image_name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", image_type='" + image_type + '\'' +
                ", image_describe='" + image_describe + '\'' +
                '}';
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
}
