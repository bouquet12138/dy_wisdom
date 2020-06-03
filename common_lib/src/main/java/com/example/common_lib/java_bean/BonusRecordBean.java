package com.example.common_lib.java_bean;

public class BonusRecordBean {

    public static final String TYPE_SHIFT = "转入";
    public static final String TYPE_ROLL = "转出";
    public static final String TYPE_OTHER = "其他";


    private int bonus_id;
    private int transaction_amount;
    private Integer user_id;
    private String integral_type;
    private String transaction_remark;
    private int remain_amount;
    private String insert_time;


}
