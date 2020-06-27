package com.example.common_lib.java_bean;


/**
 * 分润积分bean
 */
public class BonusRecordBean {


    public static final String INTEGRAL_TYPE_BUY = "购买商品";
    public static final String INTEGRAL_TYPE_RECHARGE = "充值";

    public static final String INTEGRAL_TYPE_IN = "转入";
    public static final String INTEGRAL_TYPE_ELSE = "其他";


    private int bonus_id;
    private int transaction_amount;
    private Integer user_id;
    private String integral_type;
    private String transaction_remark;
    private int remain_amount;
    private String insert_time;

    public int getBonus_id() {
        return bonus_id;
    }

    public void setBonus_id(int bonus_id) {
        this.bonus_id = bonus_id;
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
