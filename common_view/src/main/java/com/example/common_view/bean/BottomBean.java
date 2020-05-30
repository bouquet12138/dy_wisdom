package com.example.common_view.bean;


public class BottomBean {

    private int mSelectImg;//选中的图片
    private int mUnSelectImg;//未选中图片
    private String mName;//底部的名字

    public BottomBean(int selectImg, int unSelectImg, String name) {
        mSelectImg = selectImg;
        mUnSelectImg = unSelectImg;
        mName = name;
    }

    public int getSelectImg() {
        return mSelectImg;
    }

    public int getUnSelectImg() {
        return mUnSelectImg;
    }

    public String getName() {
        return mName;
    }

}
