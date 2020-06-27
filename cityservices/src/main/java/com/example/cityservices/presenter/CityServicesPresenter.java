package com.example.cityservices.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.cityservices.contract.CityServicesContract;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.StoreBean;
import com.example.common_lib.model.StoreModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CityServicesPresenter extends MVPBasePresenter<CityServicesContract.IView>
        implements CityServicesContract.IPresenter {

    private static final int NUM = 10;

    private StoreModel mModel = new StoreModel();
    private List<StoreBean> mStoreBeans = new ArrayList<>();//一个集合

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
                    BaseBean<List<StoreBean>> baseBean = (BaseBean<List<StoreBean>>) msg.obj;//得到用户id
                    //
                    if (baseBean.getCode() == 1) {
                        mStoreBeans.clear();//清空一下
                        if (!CollectionUtils.isEmpty(baseBean.getData()))
                            mStoreBeans.addAll(baseBean.getData());//全部添加进来
                        if (CollectionUtils.isEmpty(baseBean.getData()) || baseBean.getData().size() < NUM)
                            getView().setFootNoMoreData();//没有更多数据
                        getView().showNormalView();//展示正常view
                        getView().refreshStoreList(mStoreBeans);//刷新一下
                        getView().smoothScrollToPosition(0);//滚动到顶部
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
                    BaseBean<List<StoreBean>> baseBean1 = (BaseBean<List<StoreBean>>) msg.obj;//得到用户id
                    if (baseBean1.getCode() == 1) {
                        if (!CollectionUtils.isEmpty(baseBean1.getData())) {
                            List<StoreBean> storeBeans = baseBean1.getData();
                            Collections.reverse(storeBeans);//反转一下
                            mStoreBeans.addAll(0, storeBeans);//全部添加进来
                            getView().refreshStoreList(mStoreBeans);//刷新一下
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
                    BaseBean<List<StoreBean>> baseBean2 = (BaseBean<List<StoreBean>>) msg.obj;//得到用户id
                    if (baseBean2.getCode() == 1) {
                        if (!CollectionUtils.isEmpty(baseBean2.getData())) {
                            List<StoreBean> storeBeans = baseBean2.getData();
                            Collections.reverse(storeBeans);//反转一下
                            mStoreBeans.addAll(storeBeans);//全部添加进来
                            getView().refreshStoreList(mStoreBeans);//刷新一下
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
    public void initStoreInfo() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            getView().showNetError();//展示网络错误
            return;
        }
        getView().setFootHasData();//底部有数据
        getView().showLoading("数据加载中..");


        mModel.initStoreInfo(getView().getProvince(), getView().getCity(), getView().getDistrict(), getView().getStoreName(),
                new OnGetInfoListener<BaseBean<List<StoreBean>>>() {
                    @Override
                    public void onComplete() {
                        mHandler.sendEmptyMessageDelayed(INIT_COMPLETE, 300);
                    }

                    @Override
                    public void onNetError() {
                        mHandler.sendEmptyMessage(INIT_NET_ERROR);//网络错误
                    }

                    @Override
                    public void onResult(BaseBean<List<StoreBean>> info) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = INIT_SUCCESS;//结果
                        msg.obj = info;
                        mHandler.sendMessage(msg);//发送信息
                    }
                });

    }

    private static final String TAG = "CityServicesPresenter";

    @Override
    public void refreshStoreInfo() {

        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            getView().completeRefresh();//完成刷新
            return;
        }

        Log.d(TAG, "refreshStoreInfo: onRes 刷新");

        int store_id = 0;
        if (!CollectionUtils.isEmpty(mStoreBeans))
            store_id = mStoreBeans.get(0).getStore_id();//第一个store_bean的id

        mModel.refreshStoreInfo(store_id, getView().getProvince(), getView().getCity(), getView().getDistrict(), getView().getStoreName(),
                new OnGetInfoListener<BaseBean<List<StoreBean>>>() {
                    @Override
                    public void onComplete() {
                        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                    }

                    @Override
                    public void onNetError() {
                        mHandler.sendEmptyMessage(REFRESH_NET_ERROR);//网络错误
                    }

                    @Override
                    public void onResult(BaseBean<List<StoreBean>> info) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = REFRESH_SUCCESS;//结果
                        msg.obj = info;
                        mHandler.sendMessage(msg);//发送信息
                    }
                });


    }

    @Override
    public void loadMoreStoreInfo() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            getView().completeLoadMore();//完成加载更多
            return;
        }

        int store_id = 0;
        if (!CollectionUtils.isEmpty(mStoreBeans))
            store_id = mStoreBeans.get(mStoreBeans.size() - 1).getStore_id();//第一个store_bean的id

        mModel.loadMoreStoreInfo(store_id, getView().getProvince(), getView().getCity(), getView().getDistrict(), getView().getStoreName(),
                new OnGetInfoListener<BaseBean<List<StoreBean>>>() {
                    @Override
                    public void onComplete() {
                        mHandler.sendEmptyMessage(LOAD_MORE_COMPLETE);
                    }

                    @Override
                    public void onNetError() {
                        mHandler.sendEmptyMessage(LOAD_MORE_NET_ERROR);//网络错误
                    }

                    @Override
                    public void onResult(BaseBean<List<StoreBean>> info) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = LOAD_MORE_SUCCESS;//结果
                        msg.obj = info;
                        mHandler.sendMessage(msg);//发送信息
                    }
                });

    }
}
