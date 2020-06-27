package com.example.common_lib.java_bean;


public class OrderRecordBean {

    public static final String STATUS_ALL = "全部";
    public static final String STATUS_WAIT_DELIVERY = "待发货";
    public static final String STATUS_HAD_DELIVERY = "已发货";
    public static final String STATUS_COMPLETE = "已完成";

    public static final String ON_LINE = "在线商城";//在线商城
    public static final String BONUS_LINE = "分润商城";//分润商城


    private Integer order_id;
    private Integer user_id;
    private String pay_pass;//支付密码

    private int good_id;
    private GoodBean good_bean;


    private String good_type;

    private String serial;
    private String status;
    private int good_count;


    private String receive_address;
    private String receive_detail_address;
    private String receive_name;
    private String receive_phone;

    private String insert_time;

    public OrderRecordBean(Integer user_id, String pay_pass, int good_id,
                           int good_count, String receive_address, String receive_detail_address, String receive_name, String receive_phone) {
        this.user_id = user_id;
        this.pay_pass = pay_pass;
        this.good_id = good_id;
        this.good_count = good_count;
        this.receive_address = receive_address;
        this.receive_detail_address = receive_detail_address;
        this.receive_name = receive_name;
        this.receive_phone = receive_phone;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPay_pass() {
        return pay_pass;
    }

    public void setPay_pass(String pay_pass) {
        this.pay_pass = pay_pass;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public GoodBean getGood_bean() {
        return good_bean;
    }

    public void setGood_bean(GoodBean good_bean) {
        this.good_bean = good_bean;
    }

    public String getGood_type() {
        return good_type;
    }

    public void setGood_type(String good_type) {
        this.good_type = good_type;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public String getReceive_address() {
        return receive_address;
    }

    public void setReceive_address(String receive_address) {
        this.receive_address = receive_address;
    }

    public String getReceive_detail_address() {
        return receive_detail_address;
    }

    public void setReceive_detail_address(String receive_detail_address) {
        this.receive_detail_address = receive_detail_address;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getReceive_phone() {
        return receive_phone;
    }

    public void setReceive_phone(String receive_phone) {
        this.receive_phone = receive_phone;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }
}
