package com.example.login_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.SetBean;
import com.example.common_lib.java_bean.UserBean;

public interface RegisterContract {

    interface IView extends IAppMvpView {

        /**
         * 设置套餐信息
         * @param setBean
         */
        void setSetInfo(SetBean setBean);

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
         * 设置用户信息
         *
         * @param userInfo
         */
        void setUserInfo(UserBean userInfo);

        /**
         * 注册成功后
         */
        void registerSuccess(int userId);


        String getPhone();

        String getVerCode();


        /**
         * 得到登陆密码
         *
         * @return
         */
        String getLoginPass();


        String getPayPass();


        /**
         * 得到安置者手机号
         *
         * @return
         */
        String getPlaceUserPhone();

        /**
         * 得到推荐人的支付密码
         *
         * @return
         */
        String getName();

        String getRecommendUserPayPass();
    }


    interface IPresenter {

        /**
         * 得到套餐信息
         */
        void getSetInfo();

        /**
         * 发送验证码
         */
        void sendQrCode();

        /**
         * 注册
         */
        void register();

        /**
         * 得到用户信息
         */
        void getUserInfo();

        /**
         * 判断用户填写的信息是否正确
         *
         * @return
         */
        boolean isRight();
    }

}
