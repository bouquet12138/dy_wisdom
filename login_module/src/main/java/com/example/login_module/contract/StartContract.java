package com.example.login_module.contract;


import com.example.base_lib.base.IMVPBaseView;

public interface StartContract {

    interface IView extends IMVPBaseView {
        void startMainActivity();

        void startLoginActivity();
    }

    interface IPresenter {
        void initInfo();
    }

}
