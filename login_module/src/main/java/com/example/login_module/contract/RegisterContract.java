package com.example.login_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.UserBean;

public interface RegisterContract {

    interface IView extends IAppMvpView {

        /**
         * 设置按钮是否可用
         *
         * @param enable
         */
        void setSendBtEnable(boolean enable);

        /**
         * 设置按钮上的文字
         *
         * @param text
         */
        void setSendBtText(String text);

        /**
         * 用户填写的是否正确
         *
         * @return
         */
        boolean isRight();


        /**
         * 设置用户信息
         *
         * @param userInfo
         */
        void setUserInfo(UserBean userInfo);

        /**
         * 注册成功后
         */
        void registerSuccess(int userId);


        String getPhoneNum();

        String getVerCode();
    }


    interface IPresenter {

        /**
         * 发送验证码
         */
        void sendQrCode();

        /**
         * 注册
         */
        void register(String phone_num, String login_password, String pay_password,
                      String recommend_user_phone, String vertex_user_phone);

        /**
         * 得到用户信息
         */
        void getUserInfo(String phone_num);
    }

}
