package com.example.common_lib.java_bean;

public class SetRecordBean {

    public static final String DISPOSE_NOT = "0";
    public static final String DISPOSE_END = "1";


    private int set_record_id;
    private int set_id;
    private Integer user_id;
    private String dispose;
    private String insert_time;
    private String update_time;
    private String pay_pass;

    private SetBean set_bean;

    public int getSet_record_id() {
        return set_record_id;
    }

    public void setSet_record_id(int set_record_id) {
        this.set_record_id = set_record_id;
    }

    public int getSet_id() {
        return set_id;
    }

    public void setSet_id(int set_id) {
        this.set_id = set_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getDispose() {
        return dispose;
    }

    public void setDispose(String dispose) {
        this.dispose = dispose;
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

    public String getPay_pass() {
        return pay_pass;
    }

    public void setPay_pass(String pay_pass) {
        this.pay_pass = pay_pass;
    }

    public SetBean getSet_bean() {
        return set_bean;
    }

    public void setSet_bean(SetBean set_bean) {
        this.set_bean = set_bean;
    }
}
