package com.example.login_module.contract;


import com.example.base_lib.base.IMVPBaseView;

public interface LoginContract {

    interface IView extends IMVPBaseView {

        /**
         * 是否同意隐私条例
         *
         * @param agree
         */
        void setAgree(boolean agree);

        void setAccount(String account);

        void setPassword(String password);

        void setRememberChecked();

        void setAutoLandChecked();


        boolean isRememberPassword();

        boolean isAutoLogin();

        String getAccount();//得到账号

        String getPassword();//得到密码

        void startMainActivity();//跳转到主界面

        void showPrivacy();//展示隐私政策

    }


    interface IPresenter {

        void login();//登陆

        void initInfo();//初始化信息

    }


}
