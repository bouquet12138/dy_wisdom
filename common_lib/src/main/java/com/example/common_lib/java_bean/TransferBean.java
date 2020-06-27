package com.example.common_lib.java_bean;


/**
 * 用于积分互转的javabean
 */
public class TransferBean {

    public static final String SALE_TRANSFER = "销售积分互转";
    public static final String SPREAD_TRANSFER = "推广积分互转";

    public static final String SALE_TO_REDEEM = "销售积分转兑换积分";
    public static final String SALE_TO_INTEGER = "销售积分转积分";

    private String transaction_type;
    private int transaction_amount;
    private Integer user_id;
    private Integer target_user_id;
    private String transaction_remark;
    private String pay_password;


    public TransferBean(String transaction_type, int transaction_amount, Integer user_id, Integer target_user_id, String transaction_remark, String pay_password) {
        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.user_id = user_id;
        this.target_user_id = target_user_id;
        this.transaction_remark = transaction_remark;
        this.pay_password = pay_password;
    }

    public TransferBean(int transaction_amount, Integer user_id, Integer target_user_id, String transaction_remark, String pay_password) {

        this.transaction_amount = transaction_amount;
        this.user_id = user_id;
        this.target_user_id = target_user_id;
        this.transaction_remark = transaction_remark;
        this.pay_password = pay_password;
    }

    public TransferBean(String transaction_type, int transaction_amount, Integer user_id, String remark, String pay_password) {

        this.transaction_type = transaction_type;
        this.transaction_amount = transaction_amount;
        this.user_id = user_id;
        this.pay_password = pay_password;
        this.transaction_remark = remark;
    }

    /**
     * getView().getTransactionType(), getView().getIntegralAmount(), userBean.getUser_id(), target_user_id,
     * getView().getRemark(), getView().getPayPassword()
     *
     * @return
     */


    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
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

    public String getTransaction_remark() {
        return transaction_remark;
    }

    public void setTransaction_remark(String transaction_remark) {
        this.transaction_remark = transaction_remark;
    }

    public String getPay_password() {
        return pay_password;
    }

    public void setPay_password(String pay_password) {
        this.pay_password = pay_password;
    }
}
