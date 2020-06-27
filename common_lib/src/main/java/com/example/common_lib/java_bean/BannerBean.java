package com.example.common_lib.java_bean;

/**
 * 轮播图
 */
public class BannerBean {

    private int banner_id;
    private int image_id;
    private String insert_time;

    private ImageBean image;

    @Override
    public String toString() {
        return "BannerBean{" +
                "banner_id=" + banner_id +
                ", banner_image_id=" + image_id +
                ", insert_time='" + insert_time + '\'' +
                ", image=" + image +
                '}';
    }

    public int getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(int banner_id) {
        this.banner_id = banner_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }
}
