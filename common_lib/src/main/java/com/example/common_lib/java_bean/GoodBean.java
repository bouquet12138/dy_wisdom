package com.example.common_lib.java_bean;

import java.io.Serializable;
import java.util.List;

public class GoodBean implements Serializable {

    public static final String SOLD_OUT = "0";//下架
    public static final String SOLD_UP = "1";//上架

    public static final String ON_LINE = "在线商城";//在线商城
    public static final String BONUS_LINE = "分润商城";//分润商城


    private Integer good_id;
    private String status;  //SOLD_OUT
    private String good_type;  //ON_LINE
    private String title;
    private String introduce;
    private int price;

    private String image_ids;
    private List<ImageBean> image_list;//图片列表

    private String insert_time;
    private String update_time;

    public Integer getGood_id() {
        return good_id;
    }

    public void setGood_id(Integer good_id) {
        this.good_id = good_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGood_type() {
        return good_type;
    }

    public void setGood_type(String good_type) {
        this.good_type = good_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage_ids() {
        return image_ids;
    }

    public void setImage_ids(String image_ids) {
        this.image_ids = image_ids;
    }

    public List<ImageBean> getImage_list() {
        return image_list;
    }

    public void setImage_list(List<ImageBean> image_list) {
        this.image_list = image_list;
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
