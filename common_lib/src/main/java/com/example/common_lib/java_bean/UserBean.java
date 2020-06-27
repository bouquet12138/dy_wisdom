package com.example.common_lib.java_bean;

import java.io.Serializable;

public class UserBean implements Serializable {

    public static final String MERCHANT_NO = "0";
    public static final String MERCHANT_YES = "1";

    public static final String SEX_MAN = "男";
    public static final String SEX_WOMAN = "女";

    public static final String ROLE_ALLIANCE_BUSINESS = "加盟商";
    public static final String ROLE_COOPERATIVE_PARTNER = "合作商";
    public static final String ROLE_AGENT = "代理商";
    public static final String ROLE_SERVICE = "服务商";
    public static final String ROLE_SERVICE_CENTER = "服务中心";

    public static final String ENABLED_NOT = "0";
    public static final String ENABLED_YES = "1";


    private Integer user_id;
    private String is_merchant;
    private String id_card;
    private Integer recommend_user_id;
    private Integer placement_user_id;
    private String name;
    private String sex;
    private String phone;
    private Integer head_img_id;
    private String birthday;
    private String province;
    private String city;
    private String district;
    private String detail_address;
    private String role;
    private String enabled;
    private int integral;
    private int sale_share_integral;
    private int redeem_integral;
    private int bonus_integral;
    private int spread_integral;
    private int profit_merchant;
    private int profit_frozen_merchant;
    private String uuid;
    private String login_pass;
    private String pay_pass;
    private String register_time;
    private String update_time;
    private String login_time;
    private String bank_num;

    private String recommend_user_pay_pass;//推荐用户的支付密码

    private int child_num;//孩子数

    //对应的头像信息
    private ImageBean head_img;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getIs_merchant() {
        return is_merchant;
    }

    public void setIs_merchant(String is_merchant) {
        this.is_merchant = is_merchant;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public Integer getRecommend_user_id() {
        return recommend_user_id;
    }

    public void setRecommend_user_id(Integer recommend_user_id) {
        this.recommend_user_id = recommend_user_id;
    }

    public Integer getPlacement_user_id() {
        return placement_user_id;
    }

    public void setPlacement_user_id(Integer placement_user_id) {
        this.placement_user_id = placement_user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getHead_img_id() {
        return head_img_id;
    }

    public void setHead_img_id(Integer head_img_id) {
        this.head_img_id = head_img_id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail_address() {
        return detail_address;
    }

    public void setDetail_address(String detail_address) {
        this.detail_address = detail_address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getSale_share_integral() {
        return sale_share_integral;
    }

    public void setSale_share_integral(int sale_share_integral) {
        this.sale_share_integral = sale_share_integral;
    }

    public int getRedeem_integral() {
        return redeem_integral;
    }

    public void setRedeem_integral(int redeem_integral) {
        this.redeem_integral = redeem_integral;
    }

    public int getBonus_integral() {
        return bonus_integral;
    }

    public void setBonus_integral(int bonus_integral) {
        this.bonus_integral = bonus_integral;
    }

    public int getSpread_integral() {
        return spread_integral;
    }

    public void setSpread_integral(int spread_integral) {
        this.spread_integral = spread_integral;
    }

    public int getProfit_merchant() {
        return profit_merchant;
    }

    public void setProfit_merchant(int profit_merchant) {
        this.profit_merchant = profit_merchant;
    }

    public int getProfit_frozen_merchant() {
        return profit_frozen_merchant;
    }

    public void setProfit_frozen_merchant(int profit_frozen_merchant) {
        this.profit_frozen_merchant = profit_frozen_merchant;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLogin_pass() {
        return login_pass;
    }

    public void setLogin_pass(String login_pass) {
        this.login_pass = login_pass;
    }

    public String getPay_pass() {
        return pay_pass;
    }

    public void setPay_pass(String pay_pass) {
        this.pay_pass = pay_pass;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getBank_num() {
        return bank_num;
    }

    public void setBank_num(String bank_num) {
        this.bank_num = bank_num;
    }

    public String getRecommend_user_pay_pass() {
        return recommend_user_pay_pass;
    }

    public void setRecommend_user_pay_pass(String recommend_user_pay_pass) {
        this.recommend_user_pay_pass = recommend_user_pay_pass;
    }

    public int getChild_num() {
        return child_num;
    }

    public void setChild_num(int child_num) {
        this.child_num = child_num;
    }

    public ImageBean getHead_img() {
        return head_img;
    }

    public void setHead_img(ImageBean head_img) {
        this.head_img = head_img;
    }

    /**
     * 设置用户信息
     *
     * @param modifyUserBean
     */
    public void setInfo(UserBean modifyUserBean) {

        this.head_img_id = modifyUserBean.getHead_img_id();
        this.name = modifyUserBean.getName();
        this.sex = modifyUserBean.getSex();
        this.birthday = modifyUserBean.getBirthday();//生日
        this.province = modifyUserBean.getProvince();
        this.city = modifyUserBean.getCity();
        this.district = modifyUserBean.getDistrict();//区
        this.detail_address = modifyUserBean.getDetail_address();//详细地址
        this.id_card = modifyUserBean.id_card;
        this.bank_num = modifyUserBean.getBank_num();
        this.head_img = modifyUserBean.getHead_img();

    }

}
