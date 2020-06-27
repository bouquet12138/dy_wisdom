package com.example.common_lib.java_bean;

public class ProfitMerchantRecordBean {

    public static final String INTEGRAL_TYPE_SHIFT = "收入";
    public static final String INTEGRAL_TYPE_WITHDRAW = "提现";

    public static final String INTEGRAL_TYPE_RECHARGE = "充值";
    public static final String INTEGRAL_TYPE_ELSE = "其他";

    private int profit_merchant_id;
    private Integer user_id;
    private int transaction_amount;
    private Integer pay_user_id;
    private String profit_type;
    private String transaction_remark;
    private String insert_time;
    private int remain_amount;

    public int getProfit_merchant_id() {
        return profit_merchant_id;
    }

    public void setProfit_merchant_id(int profit_merchant_id) {
        this.profit_merchant_id = profit_merchant_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public int getTransaction_amount() {
        return transaction_amount;
    }

    public void setTransaction_amount(int transaction_amount) {
        this.transaction_amount = transaction_amount;
    }

    public Integer getPay_user_id() {
        return pay_user_id;
    }

    public void setPay_user_id(Integer pay_user_id) {
        this.pay_user_id = pay_user_id;
    }

    public String getProfit_type() {
        return profit_type;
    }

    public void setProfit_type(String profit_type) {
        this.profit_type = profit_type;
    }

    public String getTransaction_remark() {
        return transaction_remark;
    }

    public void setTransaction_remark(String transaction_remark) {
        this.transaction_remark = transaction_remark;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public int getRemain_amount() {
        return remain_amount;
    }

    public void setRemain_amount(int remain_amount) {
        this.remain_amount = remain_amount;
    }
}
