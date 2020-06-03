package com.example.common_lib.java_bean;


public class SpreadRecordBean {

    public static final String INTEGRAL_TYPE_TRANSFERS = "互转";
    public static final String INTEGRAL_TYPE_REGISTER = "注册用户";
    public static final String INTEGRAL_TYPE_TOP = "充值";
    public static final String INTEGRAL_TYPE_ELSE = "其他";

    private int spread_id;
    private int transaction_amount;
    private Integer user_id;
    private Integer target_user_id;
    private String integral_type;
    private String transaction_remark;
    private int remain_amount;
    private String insert_time;

    public int getSpread_id() {
        return spread_id;
    }

    public void setSpread_id(int spread_id) {
        this.spread_id = spread_id;
    }

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
}
