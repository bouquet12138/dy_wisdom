package com.example.wisdomconsumption.contract;

import com.example.common_lib.base.IAppMvpView;

public interface AboutUsContract {

    interface IView extends IAppMvpView {

        /**
         * 设置关于我们的信息
         *
         * @param info
         */
        void setAboutUsInfo(String info);

    }

    interface IPresenter {
        /**
         * 得到关于我们
         */
        void getAboutUsInfo();
    }

}
