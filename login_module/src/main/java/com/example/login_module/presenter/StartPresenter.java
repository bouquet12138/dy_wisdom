package com.example.login_module.presenter;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.LoginAndRegisterModel;
import com.example.common_lib.model.UserModel;
import com.example.login_module.contract.StartContract;

import static android.content.Context.MODE_PRIVATE;

public class StartPresenter extends MVPBasePresenter<StartContract.IView> implements StartContract.IPresenter {

    private LoginAndRegisterModel mModel = new LoginAndRegisterModel();
    private UserModel mUserModel = new UserModel();

    private final int ON_RESULT = 0;
    private final int END = 1;

    private final int INFO_ON_RESULT = 2;
    private final int INFO_NET_ERROR = 3;//网络错误

    private boolean mLoginSuccess = false;//是否登陆成功

    private String mAccount;
    private String mPassword;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;
            switch (msg.what) {
                case ON_RESULT:
                    BaseBean<Integer> baseBean = (BaseBean<Integer>) msg.obj;//得到
                    if (baseBean.getCode() == 1) {
                        getUserInfo(baseBean.getData());//查询用户信息
                    }
                    break;
                case END:
                    Log.d("StartPresenter", "handleMessage: ");
                    if (mLoginSuccess)
                        getView().startMainActivity();
                    else
                        getView().startLoginActivity();//启动登陆界面
                    break;
                case INFO_ON_RESULT:
                    BaseBean<UserBean> baseBean1 = (BaseBean<UserBean>) msg.obj;//得到
                    if (baseBean1.getCode() == 1) {
                        NowUserInfo.setNowUserInfo(baseBean1.getData());//设置当前用户信息
                        mLoginSuccess = true;
                    }
                    break;
                case INFO_NET_ERROR:
                    break;
            }
        }
    };

    public void login() {
        if (!isViewAttached())
            return;

        mModel.login(mAccount, mPassword, new OnGetInfoListener<BaseBean<Integer>>() {
            @Override
            public void onResult(BaseBean<Integer> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }

            @Override
            public void onNetError() {
            }

            @Override
            public void onComplete() {
            }
        });

    }

    /**
     * 初始化信息
     */
    @Override
    public void initInfo() {
        if (!isViewAttached())
            return;
        Log.d("StartPresenter", "initInfo: ");
        mHandler.sendEmptyMessageDelayed(END, 4000);//发送空消息

        SharedPreferences preferences = getShare();//得到一下sharePreference
        mAccount = preferences.getString("account", "");//账号
        mPassword = preferences.getString("password", "");//密码

        boolean isAutoLogin = preferences.getBoolean("isAutoLogin", false);

        if (isAutoLogin) {
            login();//去登陆
        } else {
            if (TextUtils.isEmpty(mAccount) && TextUtils.isEmpty(mPassword))
                mLoginSuccess = true;
        }
    }

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
            }
        });
    }


    private SharedPreferences getShare() {
        return getView().getContext().getSharedPreferences
                ("loginShare", MODE_PRIVATE);

    }

}
