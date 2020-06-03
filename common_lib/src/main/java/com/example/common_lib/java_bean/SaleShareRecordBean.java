package com.example.common_lib.java_bean;


/**
 * 销售积分
 */
public class SaleShareRecordBean {
    public static final String INTEGRAL_TYPE_TRAN = "互转";
    public static final String INTEGRAL_TYPE_DIRECT = "直推";
    public static final String INTEGRAL_TYPE_INDIRECT = "间推";
    public static final String INTEGRAL_TYPE_BUY = "购买套餐";
    public static final String INTEGRAL_TYPE_REGISTER = "注册用户";
    public static final String INTEGRAL_TYPE_WITHDRAW = "提现";
    public static final String INTEGRAL_TYPE_TOP = "充值";
    public static final String INTEGRAL_TYPE_NEW_ADD = "新增";
    public static final String INTEGRAL_TYPE_ELSE = "其他";


    private int sale_share_id;
    private int transaction_amount;
    private Integer user_id;
    private Integer target_user_id;
    private String integral_type;
    private String transaction_remark;
    private int remain_amount;
    private String insert_time;

    public int getSale_share_id() {
        return sale_share_id;
    }

    public int getTransaction_amount() {
        return transaction_amount;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getTarget_user_id() {
        return target_user_id;
    }

    public String getIntegral_type() {
        return integral_type;
    }

    public String getTransaction_remark() {
        return transaction_remark;
    }

    public int getRemain_amount() {
        return remain_amount;
    }

    public String getInsert_time() {
        return insert_time;
    }
}
