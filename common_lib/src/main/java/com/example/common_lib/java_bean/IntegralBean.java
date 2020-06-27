package com.example.common_lib.java_bean;

import java.io.Serializable;

public class IntegralBean implements Serializable {

    protected int transaction_amount;
    protected Integer user_id;
    protected Integer target_user_id;
    protected String integral_type;
    protected String transaction_remark;
    protected int remain_amount;
    protected String insert_time;

    protected UserBean user_bean;

    public int getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(int transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getTarget_user_id() {
        return target_user_id;
    }

    public void setTarget_user_id(Integer target_user_id) {
        this.target_user_id = target_user_id;
    }

    public String getIntegral_type() {
        return integral_type;
    }

    public void setIntegral_type(String integral_type) {
        this.integral_type = integral_type;
    }

    public String getTransaction_remark() {
        return transaction_remark;
    }

    public void setTransaction_remark(String transaction_remark) {
        this.transaction_remark = transaction_remark;
    }

    public int getRemain_amount() {
        return remain_amount;
    }

    public void setRemain_amount(int remain_amount) {
        this.remain_amount = remain_amount;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public UserBean getUser_bean() {
        return user_bean;
    }

    public void setUser_bean(UserBean user_bean) {
        this.user_bean = user_bean;
    }
}
