package com.example.wisdomconsumption.java_bean;

import com.blankj.utilcode.util.GsonUtils;

public class QRBean {

    public static final String SALE_SHARE = "销售积分";
    public static final String SPREAD = "推广积分";
    public static final String MERCHANTS = "商家收款";

    private String phone_num;//手机号
    private int user_id;//用户id

    private String type;//转账类型

    public QRBean(String phone_num, int user_id, String type) {
        this.phone_num = phone_num;
        this.user_id = user_id;
        this.type = type;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public String getType() {
        return type;
    }


    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return GsonUtils.toJson(this);
    }
}
