package com.example.login_module.presenter;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.LoginAndRegisterModel;
import com.example.common_lib.model.UserModel;
import com.example.login_module.contract.LoginContract;

import static android.content.Context.MODE_PRIVATE;

public class LoginPresenter extends MVPBasePresenter<LoginContract.IView>
        implements LoginContract.IPresenter {

    private static final String TAG = "LoginPresenter";

    private LoginAndRegisterModel mModel = new LoginAndRegisterModel();
    private UserModel mUserModel = new UserModel();//用户model

    private final int ON_RESULT = 0;
    private final int NET_ERROR = 1;//网络错误
    private final int ON_COMPLETE = 2;//完成

    private final int INFO_ON_RESULT = 3;
    private final int INFO_NET_ERROR = 4;//网络错误
    private final int INFO_ON_COMPLETE = 5;//完成


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;
            switch (msg.what) {
                case ON_RESULT:
                    BaseBean<Integer> baseBean = (BaseBean<Integer>) msg.obj;//得到用户id
                    if (baseBean.getCode() == 1) {
                        // NowUserInfo.setNowUserInfo(baseBean.getData());//设置当前学生信息
                        getUserInfo(baseBean.getData());
                        Log.d(TAG, "handleMessage: " + baseBean.getData());
                        saveData();//设置数据
                    } else {
                        getView().showErrorHint("网络错误");
                        getView().hideLoading();//隐藏进度框
                    }
                    break;
                case NET_ERROR:
                    getView().showErrorHint("网络错误");
                    getView().hideLoading();//隐藏进度框
                    break;
                case ON_COMPLETE:
                    break;
                case INFO_ON_RESULT:
                    BaseBean<UserBean> baseBean1 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    // getView().showToast(baseBean.getMsg());//弹出提示信息
                    if (baseBean1.getCode() == 1) {
                        NowUserInfo.setNowUserInfo(baseBean1.getData());//设置当前用户信息
                        Log.d(TAG, "handleMessage: " + baseBean1.getData());
                        startMain();//启动主activity
                    }
                    break;
                case INFO_NET_ERROR:
                    getView().showErrorHint("网络错误");
                    break;
                case INFO_ON_COMPLETE:
                    getView().hideLoading();//隐藏加载进度框
                    break;
            }

        }
    };

    private void getUserInfo(int userId) {
        mUserModel.getUserInfo(userId, new OnGetInfoListener<BaseBean<UserBean>>() {
            @Override
            public void onResult(BaseBean<UserBean> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INFO_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INFO_NET_ERROR);//网络错误
            }

            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INFO_ON_COMPLETE);//完成
            }
        });
    }


    /**
     * 跳转到主activity
     */
    private void startMain() {
        getView().startMainActivity(); //启动主Activity
    }

    /**
     * 存储数据
     */
    private void saveData() {
        SharedPreferences preferences = getShare();
        SharedPreferences.Editor editor = preferences.edit();

        boolean isAutoLand = getView().isAutoLogin();//是否自动登陆
        boolean rememberPassword = getView().isRememberPassword();//是否记住密码
        String account = getView().getAccount();//账号
        String password = getView().getPassword();//密码

        editor.putBoolean("isAutoLogin", isAutoLand);
        editor.putBoolean("isRememberPassWord", rememberPassword);
        editor.putBoolean("agree", true);//同意隐私条例

        //如果记住密码
        if (rememberPassword) {
            editor.putString("account", account);
            editor.putString("password", password);
        } else {
            editor.putString("account", "");
            editor.putString("password", "");
        }
        editor.apply();
    }

    @Override
    public void initInfo() {
        if (!isViewAttached())
            return;

        SharedPreferences preferences = getShare();

        boolean isFirst = preferences.getBoolean("isFirst", true);//默认是第一次

        if (isFirst) {
            getView().showPrivacy();//展示隐私政策
            SharedPreferences.Editor editor = preferences.edit();//得到edit
            editor.putBoolean("isFirst", false);//不是第一次了
            editor.apply();//应用一下
        }

        boolean isAutoLogin = preferences.getBoolean("isAutoLogin", false);
        boolean isRememberPassWord = preferences.getBoolean("isRememberPassWord", false);
        boolean agree = preferences.getBoolean("agree", false);
        getView().setAgree(agree);

        if (isAutoLogin)
            getView().setAutoLandChecked();//自动登陆
        if (isRememberPassWord) {
            getView().setRememberChecked();//记住密码
            String account = preferences.getString("account", "");//账号
            String password = preferences.getString("password", "");//密码
            getView().setAccount(account);//设置账号
            getView().setPassword(password);//设置密码
        }
    }


    private SharedPreferences getShare() {
        return getView().getContext().getSharedPreferences
                ("loginShare", MODE_PRIVATE);
    }

    @Override
    public void login() {
        if (!isViewAttached())
            return;

        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络未链接");
            return;
        }
        getView().showLoading("登陆中...");
        mModel.login(getView().getAccount(), getView().getPassword(), new OnGetInfoListener<BaseBean<Integer>>() {
            @Override
            public void onResult(BaseBean<Integer> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(NET_ERROR);//网络错误
            }

            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(ON_COMPLETE);//完成
            }
        });


    }

}
