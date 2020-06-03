package com.example.wisdomconsumption.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BannerBean;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.model.BannerModel;
import com.example.wisdomconsumption.contract.HomeContract;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends MVPBasePresenter<HomeContract.IView>
        implements HomeContract.IPresenter {

    private BannerModel mModel = new BannerModel();

    private final int INIT_SUCCESS = 0;//成功
    private final int INIT_NET_ERROR = 1;//网络错误
    private final int INIT_COMPLETE = 2;//完成

    private List<BannerBean> mBannerBeans = new ArrayList<>();//轮播图Bean

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case INIT_SUCCESS:
                    BaseBean<List<BannerBean>> baseBean = (BaseBean<List<BannerBean>>) msg.obj;//得到轮播图bean
                    if (baseBean.getCode() == 1) {
                        mBannerBeans.clear();//清空一下
                        if (!CollectionUtils.isEmpty(baseBean.getData()))
                            mBannerBeans.addAll(baseBean.getData());//全部添加进来

                        getView().setBannerInfo(mBannerBeans);
                    }
                    break;
                case INIT_NET_ERROR:
                case INIT_COMPLETE:
                    break;
            }
        }
    };

    /**
     * 得到轮播图信息
     */
    @Override
    public void getBannerInfo() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            return;
        }

        mModel.initBanner(new OnGetInfoListener<BaseBean<List<BannerBean>>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INIT_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INIT_NET_ERROR);//网络错误
            }

            @Override
            public void onResult(BaseBean<List<BannerBean>> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INIT_SUCCESS;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }
}
