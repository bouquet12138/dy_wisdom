package com.example.common_view.bean;

public class DayBean {

    private int mDay;//对应几号
    private boolean mLegal;//是否是法定节假日
    private String mLunarStr;//对应的阴历

    public DayBean(int day, boolean legal, String lunarStr) {
        mDay = day;
        mLegal = legal;
        mLunarStr = lunarStr;
    }

    public int getDay() {
        return mDay;
    }


    public boolean isLegal() {
        return mLegal;
    }

    public String getLunarStr() {
        return mLunarStr;
    }

}
