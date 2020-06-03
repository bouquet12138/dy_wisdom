package com.example.wisdomconsumption.contract;

import com.example.base_lib.base.IMVPBaseView;

public interface PersonContract {

    interface IView extends IMVPBaseView {

        /**
         * 设置用户信息
         */
        void setUserInfo();
    }


    interface IPresenter {

        /**
         * 得到用户信息
         */
        void getUserInfo();

    }

}
