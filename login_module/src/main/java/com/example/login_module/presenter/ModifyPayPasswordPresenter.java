package com.example.login_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;


import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.UserModel;
import com.example.login_module.contract.ModifyPayPasswordContract;


public class ModifyPayPasswordPresenter extends MVPBasePresenter<ModifyPayPasswordContract.IView>
        implements ModifyPayPasswordContract.IPresenter {

    private UserModel mModel = new UserModel();

    private final int SUCCESS = 0;//成功
    private final int NET_ERROR = 1;//网络错误
    private final int COMPLETE = 2;//完成

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    BaseBean baseBean = (BaseBean) msg.obj;
                    getView().showToast(baseBean.getMsg());//展示提示信息
                    if (baseBean.getCode() == 1) {
                        getView().finishActivity();//销毁Activity
                        NowUserInfo.getNowUserInfo().setLogin_pass(mNewPassword);//设置新密码
                        NowUserInfo.setNowUserInfo(NowUserInfo.getNowUserInfo());
                    }
                    break;
                case NET_ERROR:
                    getView().showToast("网络错误");
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };
    private String mNewPassword;

    @Override
    public void modifyPassword() {
        if (!isViewAttached())
            return;
        String oldPassword = getView().getOldPassword();
        mNewPassword = getView().getNewPassword();
        UserBean userBean = NowUserInfo.getNowUserInfo();//用户信息
        if (userBean == null)
            return;

        if (!judgePassword())//密码不符合要求
            return;

        getView().showLoading("密码修改中");
        mModel.modify_pay_password_with_old(userBean.getUser_id(),
                oldPassword, mNewPassword, new OnGetInfoListener<BaseBean>() {
                    @Override
                    public void onComplete() {
                        mHandler.sendEmptyMessage(COMPLETE);
                    }

                    @Override
                    public void onNetError() {
                        mHandler.sendEmptyMessage(NET_ERROR);
                    }

                    @Override
                    public void onResult(BaseBean info) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = SUCCESS;
                        msg.obj = info;
                        mHandler.sendMessage(msg);//发送信息
                    }
                });
    }

    @Override
    public boolean onPasswordChange() {
        if (!isViewAttached())
            return false;

        String oldPassword = getView().getOldPassword();
        String newPassword = getView().getNewPassword();
        String confirmPassword = getView().getConfirmPassword();

        getView().setOldPasswordHint("");
        getView().setNewPasswordHint("");
        getView().setConfirmPasswordHint("");

        if (!TextUtils.isEmpty(newPassword) && newPassword.equals(oldPassword))
            getView().setNewPasswordHint("新旧密码一致");

        if (!TextUtils.isEmpty(newPassword) && newPassword.length() < 6)
            getView().setNewPasswordHint("新密码长度至少为6");

        if (!TextUtils.isEmpty(confirmPassword) && !confirmPassword.equals(newPassword))
            getView().setConfirmPasswordHint("密码不一致");


        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)
                || TextUtils.isEmpty(confirmPassword) || !confirmPassword.equals(newPassword)
                || newPassword.length() < 6) {
            getView().setSubmitEnable(false);//不可用
            return false;
        } else {
            getView().setSubmitEnable(true);//可用
            return true;
        }

    }

    public boolean judgePassword() {
        String oldPassword = getView().getOldPassword();
        String newPassword = getView().getNewPassword();
        String confirmPassword = getView().getConfirmPassword();

        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)
                || TextUtils.isEmpty(confirmPassword) || !confirmPassword.equals(newPassword)
                || newPassword.length() != 6)
            return false;

        return true;
    }

}
