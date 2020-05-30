package com.example.base_lib.listener;

public interface OnGetInfoListener<T> {

    void onComplete();

    void onNetError();

    void onResult(T info);

}
