package com.example.login_module.contract;

import com.example.common_lib.base.IAppMvpView;


public interface ForgetPassContract {

    interface IView extends IAppMvpView {

        /**
         * 得到手机号
         *
         * @return
         */
        String getPhoneNum();

        /**
         * 得到验证码
         *
         * @return
         */
        String getVerCode();

        String getLoginPassword();//

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
         * 是否是忘记支付密码
         *
         * @return
         */
        boolean isForgetPay();

    }

    interface IPresenter {

        /**
         * 发送验证码
         */
        void sendQrCode();

        void submit();//提交
    }

}
