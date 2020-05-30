package com.example.flash_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.model.FlashModel;
import com.example.flash_module.contract.FlashContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlashPresenter extends MVPBasePresenter<FlashContract.IView>
        implements FlashContract.IPresenter {

    private static final String TAG = "FlashPresenter";

    private static final int NUM = 10;

    private FlashModel mModel = new FlashModel();
    private List<FlashBean> mFlashBeans = new ArrayList<>();//一个集合

    private final int INIT_SUCCESS = 0;//成功
    private final int INIT_NET_ERROR = 1;//网络错误
    private final int INIT_COMPLETE = 2;//完成

    private final int REFRESH_SUCCESS = 3;//成功
    private final int REFRESH_NET_ERROR = 4;//网络错误
    private final int REFRESH_COMPLETE = 5;//完成

    private final int LOAD_MORE_SUCCESS = 6;//成功
    private final int LOAD_MORE_NET_ERROR = 7;//网络错误
    private final int LOAD_MORE_COMPLETE = 8;//完成

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case INIT_SUCCESS:
                    BaseBean<List<FlashBean>> baseBean = (BaseBean<List<FlashBean>>) msg.obj;//得到快讯bean
                    if (baseBean.getCode() == 1) {
                        mFlashBeans.clear();//清空一下
                        if (!CollectionUtils.isEmpty(baseBean.getData()))
                            mFlashBeans.addAll(baseBean.getData());//全部添加进来

                        getView().showNormalView();//展示正常view
                        getView().refreshFlashList(mFlashBeans);//刷新一下

                        if (CollectionUtils.isEmpty(baseBean.getData()) || baseBean.getData().size() < NUM)
                            getView().setFootNoMoreData();//没有更多数据
                    } else {
                        getView().showToast(baseBean.getMsg());//弹出提示信息
                        getView().showNetError();//展示网络错误
                    }
                    break;
                case INIT_NET_ERROR:
                    getView().showNetError();//展示网络错误
                    break;
                case INIT_COMPLETE:
                    getView().hideLoading();
                    break;
                case REFRESH_SUCCESS:
                    BaseBean<List<FlashBean>> baseBean1 = (BaseBean<List<FlashBean>>) msg.obj;//得到用户id

                    List flashBeans = baseBean1.getData();
                    if (!CollectionUtils.isEmpty(flashBeans))//不是空的话 反转一下
                        Collections.reverse(flashBeans);
                    if (baseBean1.getCode() == 1) {
                        if (!CollectionUtils.isEmpty(baseBean1.getData())) {
                            mFlashBeans.addAll(0, flashBeans);//将新的快讯信息全部添加进来
                            getView().refreshFlashList(mFlashBeans);//刷新一下
                        }
                    } else {
                        getView().showToast(baseBean1.getMsg());//弹出提示信息
                    }
                    break;
                case REFRESH_NET_ERROR:
                    getView().showToast("网络错误，刷新失败");
                    break;
                case REFRESH_COMPLETE:
                    getView().completeRefresh();
                    break;
                case LOAD_MORE_SUCCESS:
                    BaseBean<List<FlashBean>> baseBean2 = (BaseBean<List<FlashBean>>) msg.obj;//得到用户id
                    if (baseBean2.getCode() == 1) {
                        if (!CollectionUtils.isEmpty(baseBean2.getData())) {
                            mFlashBeans.addAll(baseBean2.getData());//全部添加进来
                            getView().refreshFlashList(mFlashBeans);//刷新一下
                            if (baseBean2.getData().size() < NUM)
                                getView().setFootNoMoreData();//没有更多数据
                        } else
                            getView().setFootNoMoreData();//没有更多数据
                    } else {
                        getView().showToast(baseBean2.getMsg());//弹出提示信息
                    }
                    break;
                case LOAD_MORE_NET_ERROR:
                    getView().showToast("网络错误，加载失败");
                    break;
                case LOAD_MORE_COMPLETE:
                    getView().completeLoadMore();//完成加载更多
                    break;

            }
        }
    };

    @Override
    public void initFlashInfo() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            getView().showNetError();//展示网络错误
            return;
        }
        getView().showLoading("数据加载中..");


        mModel.initFlashInfo(new OnGetInfoListener<BaseBean<List<FlashBean>>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INIT_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INIT_NET_ERROR);//网络错误
            }

            @Override
            public void onResult(BaseBean<List<FlashBean>> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INIT_SUCCESS;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    @Override
    public void refreshFlashInfo() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            getView().completeRefresh();//完成刷新
            return;
        }

        Log.d(TAG, "refreshStoreInfo: onRes 刷新");

        int flash_id = 0;
        if (!CollectionUtils.isEmpty(mFlashBeans))
            flash_id = mFlashBeans.get(0).getFlash_id();//第一个flashBean的id

        mModel.refreshFlashInfo(flash_id,
                new OnGetInfoListener<BaseBean<List<FlashBean>>>() {
                    @Override
                    public void onComplete() {
                        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                    }

                    @Override
                    public void onNetError() {
                        mHandler.sendEmptyMessage(REFRESH_NET_ERROR);//网络错误
                    }

                    @Override
                    public void onResult(BaseBean<List<FlashBean>> info) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = REFRESH_SUCCESS;//结果
                        msg.obj = info;
                        mHandler.sendMessage(msg);//发送信息
                    }
                });

    }

    @Override
    public void loadMoreFlashInfo() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            getView().completeLoadMore();//完成加载更多
            return;
        }

        int flash_id = 0;
        if (!CollectionUtils.isEmpty(mFlashBeans))
            flash_id = mFlashBeans.get(mFlashBeans.size() - 1).getFlash_id();//第一个store_bean的id

        mModel.loadMoreFlashInfo(flash_id,
                new OnGetInfoListener<BaseBean<List<FlashBean>>>() {
                    @Override
                    public void onComplete() {
                        mHandler.sendEmptyMessage(LOAD_MORE_COMPLETE);
                    }

                    @Override
                    public void onNetError() {
                        mHandler.sendEmptyMessage(LOAD_MORE_NET_ERROR);//网络错误
                    }

                    @Override
                    public void onResult(BaseBean<List<FlashBean>> info) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = LOAD_MORE_SUCCESS;//结果
                        msg.obj = info;
                        mHandler.sendMessage(msg);//发送信息
                    }
                });
    }

    @Override
    public void addReadVolume(FlashBean _flashBean) {

        if (!isViewAttached())
            return;
        for (FlashBean flashBean : mFlashBeans) {
            if (_flashBean.getFlash_id() == flashBean.getFlash_id()) {
                flashBean.setReading_volume(flashBean.getReading_volume() + 1);//浏览量加1
                getView().refreshFlashList(mFlashBeans);//刷新一下
                return;
            }
        }

    }

}
