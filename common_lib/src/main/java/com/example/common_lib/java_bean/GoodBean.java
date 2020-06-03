package com.example.common_lib.java_bean;

public class GoodBean {

    public static final String SOLD_OUT = "0";//下架
    public static final String SOLD_UP = "1";//上架
    public static final String ON_LINE = "0";//在线商城
    public static final String INTEGRAL_LINE = "1";//积分商城


    private int good_id;
    private String status;  //SOLD_OUT
    private String good_type;  //ON_LINE
    private String title;
    private String introduce;
    private int price;
    private String image_ids;
    private String insert_time;
    private String update_time;


}
