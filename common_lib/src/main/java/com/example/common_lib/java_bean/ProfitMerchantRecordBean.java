package com.example.common_lib.java_bean;


public class ProfitMerchantRecordBean {

    public static final String PROFIT_TYPE_SHIFT = "转入";
    public static final String PROFIT_TYPE_WITHDRAW = "提现";
    public static final String PROFIT_TYPE_ELSE = "其他";

    private int profit_merchant_id;
    private Integer user_id;
    private int transaction_amount;
    private int pay_user_id;
    private String profit_type;
    private String transaction_remark;
    private String insert_time;
    private int remain_amount;


}
