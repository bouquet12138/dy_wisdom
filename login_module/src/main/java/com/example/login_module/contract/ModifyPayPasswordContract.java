package com.example.login_module.contract;


import com.example.common_lib.base.IAppMvpView;

public interface ModifyPayPasswordContract {

    interface IView extends IAppMvpView {
        String getOldPassword();//得到旧密码

        String getNewPassword();//得到新密码

        String getConfirmPassword();//得到确认密码

        void setSubmitEnable(boolean enable);//设置提交是否可用


        void setOldPasswordHint(String hint);

        void setNewPasswordHint(String hint);

        void setConfirmPasswordHint(String hint);
    }

    interface IPresenter {
        void modifyPassword();//修改密码

        boolean onPasswordChange();//密码改变时
    }

}
