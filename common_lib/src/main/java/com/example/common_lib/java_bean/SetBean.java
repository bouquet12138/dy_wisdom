package com.example.common_lib.java_bean;


public class SetBean {

    public static final String SET_TYPE_JOIN = "加盟商";
    public static final String SET_TYPE_COLL = "合作商";
    public static final String SET_TYPE_AGENCY = "代理商";

    private int set_id;
    private String set_name;
    private int set_price;
    private int rebate_price;
    private float redeem_ratio;
    private float sale_radio;
    private String set_describe;
    private String insert_time;
    private String update_time;

    private int set_count;

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }

    public String getSet_name() {
        return set_name;
    }

    public void setSet_name(String set_name) {
        this.set_name = set_name;
    }

    public int getSet_price() {
        return set_price;
    }

    public void setSet_price(int set_price) {
        this.set_price = set_price;
    }

    public int getRebate_price() {
        return rebate_price;
    }

    public void setRebate_price(int rebate_price) {
        this.rebate_price = rebate_price;
    }

    public float getRedeem_ratio() {
        return redeem_ratio;
    }

    public void setRedeem_ratio(float redeem_ratio) {
        this.redeem_ratio = redeem_ratio;
    }

    public float getSale_radio() {
        return sale_radio;
    }

    public void setSale_radio(float sale_radio) {
        this.sale_radio = sale_radio;
    }

    public String getSet_describe() {
        return set_describe;
    }

    public void setSet_describe(String set_describe) {
        this.set_describe = set_describe;
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

    public int getSet_count() {
        return set_count;
    }

    public void setSet_count(int set_count) {
        this.set_count = set_count;
    }
}
