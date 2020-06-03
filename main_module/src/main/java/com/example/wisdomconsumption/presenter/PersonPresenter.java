package com.example.wisdomconsumption.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.UserModel;
import com.example.wisdomconsumption.contract.PersonContract;

public class PersonPresenter extends MVPBasePresenter<PersonContract.IView>
        implements PersonContract.IPresenter {

    private UserModel mUserModel = new UserModel();//用户model

    private final int INFO_ON_RESULT = 1;
    private final int INFO_NET_ERROR = 2;//网络错误
    private final int INFO_ON_COMPLETE = 3;//完成

    private static final String TAG = "PersonPresenter";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;
            switch (msg.what) {

                case INFO_ON_RESULT:
                    BaseBean<UserBean> baseBean1 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    // getView().showToast(baseBean.getMsg());//弹出提示信息
                    if (baseBean1.getCode() == 1) {
                        NowUserInfo.setNowUserInfo(baseBean1.getData());//设置当前用户信息
                        Log.d(TAG, "handleMessage: " + baseBean1.getData());
                        getView().setUserInfo();//设置一下用户信息
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

    @Override
    public void getUserInfo() {
        if (!isViewAttached())//没绑定就返回
            return;

        UserBean userBean = NowUserInfo.getNowUserInfo();

        if (userBean == null)
            return;

        mUserModel.getUserInfo(userBean.getUser_id(), new OnGetInfoListener<BaseBean<UserBean>>() {
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
}
