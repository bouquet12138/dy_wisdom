package com.example.wisdomconsumption.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.AppBean;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.model.AppModel;
import com.example.wisdomconsumption.contract.AppContract;


public class AppPresenter extends MVPBasePresenter<AppContract.IView>
        implements AppContract.IPresenter {

    private AppModel mModel = new AppModel();

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
                    BaseBean<AppBean> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        getView().setAppInfo(baseBean.getData());//app信息
                    } else {
                        getView().showToast(baseBean.getMsg());//展示提示信息
                        getView().showNetError();//展示网络错误
                    }
                    break;
                case NET_ERROR:

                    getView().showNetError();//网络错误
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };


    @Override
    public void getAppInfo() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            return;
        }
        getView().showLoading("信息加载中");

        mModel.getAppInfo(new OnGetInfoListener<BaseBean<AppBean>>() {
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
}
