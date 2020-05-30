package com.example.common_lib.base;


import com.example.base_lib.base.IMVPBaseView;

public interface IAppMvpView extends IMVPBaseView {

    void setSubmitEnable(boolean enable);

    void showNetError();//展示网络错误

    void showNoMoreData();//展示没有数据

    void showNormalView();//展示正常视图

    void completeRefresh();//完成刷新

}

