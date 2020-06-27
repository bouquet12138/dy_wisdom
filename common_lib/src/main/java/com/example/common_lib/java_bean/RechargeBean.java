package com.example.common_lib.java_bean;

public class RechargeBean {

    public static final String INTEGRAL_TYPE_SHARE = "销售积分";
    public static final String INTEGRAL_TYPE_REDEEM = "兑换积分";
    public static final String INTEGRAL_TYPE_SPREAD = "推广积分";
    public static final String INTEGRAL_TYPE_BONUS = "分润积分";
    public static final String INTEGRAL_TYPE_PROFIT = "商家积分";

    private int user_id;//用户id
    private String integral_type;//充值类型
    private int recharge_amount; //积分

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIntegral_type() {
        return integral_type;
    }

    public void setIntegral_type(String integral_type) {
        this.integral_type = integral_type;
    }

    public int getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(int recharge_amount) {
        this.recharge_amount = recharge_amount;
    }
}
