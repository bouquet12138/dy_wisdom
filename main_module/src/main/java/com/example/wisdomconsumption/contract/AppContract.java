package com.example.wisdomconsumption.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.AppBean;

public interface AppContract {

    interface IView extends IAppMvpView {

        void setAppInfo(AppBean appBean);//设置app信息

    }

    interface IPresenter {
        void getAppInfo();//得到app信息
    }

}
