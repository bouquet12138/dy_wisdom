package com.example.common_lib.java_bean;

public class WithdrawBean {

    private int withdraw_id;
    private int withdraw_amount;
    private String insert_time;
    private int user_id;
    private int sale_share_id;
    private int profit_merchant_id;
    private String withdraw_remark;
    private String bank_card;
    private String ali_pay;
    private String we_chat;

    private int remain_amount;
    private int is_process;
    private String update_time;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getSale_share_id() {
        return sale_share_id;
    }

    public void setSale_share_id(int sale_share_id) {
        this.sale_share_id = sale_share_id;
    }

    public int getProfit_merchant_id() {
        return profit_merchant_id;
    }

    public void setProfit_merchant_id(int profit_merchant_id) {
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
}
