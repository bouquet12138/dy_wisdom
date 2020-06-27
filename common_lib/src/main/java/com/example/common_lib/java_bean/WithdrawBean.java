package com.example.common_lib.java_bean;


public class WithdrawBean {

    public static final String SALE_WITHDRAW = "销售积分提现";
    public static final String MERCHANT_WITHDRAW = "商家提现";

    private int withdraw_id;
    private int withdraw_amount;
    private String insert_time;
    private Integer user_id;
    private Integer sale_share_id;
    private Integer profit_merchant_id;
    private String withdraw_remark;
    private String bank_card;
    private String ali_pay;
    private String we_chat;

    private int remain_amount;
    private int is_process;
    private String update_time;

    private String withdraw_type;//提现类型
    private String pay_pass;

    /**
     * userBean.getUser_id(), amount, password, remark, mWithdrawType
     * @return
     */
    public WithdrawBean( Integer user_id, int withdraw_amount, String pay_pass,String withdraw_remark, String withdraw_type) {
        this.withdraw_amount = withdraw_amount;
        this.user_id = user_id;
        this.withdraw_remark = withdraw_remark;
        this.withdraw_type = withdraw_type;
        this.pay_pass = pay_pass;
    }




    public int getWithdraw_id() {
        return withdraw_id;
    }

    public void setWithdraw_id(int withdraw_id) {
        this.withdraw_id = withdraw_id;
    }

    public int getWithdraw_amount() {
        return withdraw_amount;
    }

    public void setWithdraw_amount(int withdraw_amount) {
        this.withdraw_amount = withdraw_amount;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getSale_share_id() {
        return sale_share_id;
    }

    public void setSale_share_id(Integer sale_share_id) {
        this.sale_share_id = sale_share_id;
    }

    public Integer getProfit_merchant_id() {
        return profit_merchant_id;
    }

    public void setProfit_merchant_id(Integer profit_merchant_id) {
        this.profit_merchant_id = profit_merchant_id;
    }

    public String getWithdraw_remark() {
        return withdraw_remark;
    }

    public void setWithdraw_remark(String withdraw_remark) {
        this.withdraw_remark = withdraw_remark;
    }

    public String getBank_card() {
        return bank_card;
    }

    public void setBank_card(String bank_card) {
        this.bank_card = bank_card;
    }

    public String getAli_pay() {
        return ali_pay;
    }

    public void setAli_pay(String ali_pay) {
        this.ali_pay = ali_pay;
    }

    public String getWe_chat() {
        return we_chat;
    }

    public void setWe_chat(String we_chat) {
        this.we_chat = we_chat;
    }

    public int getRemain_amount() {
        return remain_amount;
    }

    public void setRemain_amount(int remain_amount) {
        this.remain_amount = remain_amount;
    }

    public int getIs_process() {
        return is_process;
    }

    public void setIs_process(int is_process) {
        this.is_process = is_process;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getWithdraw_type() {
        return withdraw_type;
    }

    public void setWithdraw_type(String withdraw_type) {
        this.withdraw_type = withdraw_type;
    }

    public String getPay_pass() {
        return pay_pass;
    }

    public void setPay_pass(String pay_pass) {
        this.pay_pass = pay_pass;
    }
}
