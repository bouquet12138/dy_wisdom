package com.example.wisdomconsumption.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.AboutUsBean;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.model.AboutUsModel;
import com.example.wisdomconsumption.contract.AboutUsContract;

/**
 * 关于我们
 */
public class AboutUsPresenter extends MVPBasePresenter<AboutUsContract.IView>
        implements AboutUsContract.IPresenter {

    private AboutUsModel mModel = new AboutUsModel();//关于我们

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
                    BaseBean<AboutUsBean> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        getView().showNormalView();//展示正常view
                        getView().setAboutUsInfo(baseBean.getData().getContent());//关于我们信息
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
    public void getAboutUsInfo() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().registerNetworkListener();//注册一下
            getView().showNetError();//网络错误
            return;
        }
        getView().showLoading("信息加载中");

        mModel.getAboutUs(new OnGetInfoListener<BaseBean<AboutUsBean>>() {
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
