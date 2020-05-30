package com.example.common_lib.bean;

import java.io.Serializable;

public class ImageUploadBean implements Serializable {

    public ImageUploadBean() {
    }


    private String mImageLocalUrl;//图片本地地址

    private boolean mIsUpload = true;//默认是已经上传

    private int mImageId;//图片服务器地址

    public ImageUploadBean(String imageLocalUrl, boolean isUpload, int imageId) {
        mImageLocalUrl = imageLocalUrl;
        mIsUpload = isUpload;
        mImageId = imageId;
    }

    public String getImageLocalUrl() {
        return mImageLocalUrl;
    }

    public void setImageLocalUrl(String imageLocalUrl) {
        mImageLocalUrl = imageLocalUrl;
    }

    public boolean isUpload() {
        return mIsUpload;
    }

    public void setUpload(boolean upload) {
        mIsUpload = upload;
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }
}
