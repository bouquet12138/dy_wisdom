package com.example.set_module.presenter;


import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.SetRecordBean;

import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.SetModel;
import com.example.set_module.contract.CanGetRewardContract;

import java.util.ArrayList;
import java.util.List;

public class CanGetRewardPresenter extends MVPBasePresenter<CanGetRewardContract.IView>
        implements CanGetRewardContract.IPresenter {

    private static final int NUM = 20;

    private SetModel mModel = new SetModel();
    private List<SetRecordBean> mSetRecordBeans = new ArrayList<>();//一个集合

    private final int INIT_SUCCESS = 0;//成功
    private final int INIT_NET_ERROR = 1;//网络错误
    private final int INIT_COMPLETE = 2;//完成

    private final int LOAD_MORE_SUCCESS = 3;//成功
    private final int LOAD_MORE_NET_ERROR = 4;//网络错误
    private final int LOAD_MORE_COMPLETE = 5;//完成


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case INIT_SUCCESS:
                    BaseBean<List<SetRecordBean>> baseBean = (BaseBean<List<SetRecordBean>>) msg.obj;//得到可领奖励bean
                    if (baseBean.getCode() == 1) {
                        mSetRecordBeans.clear();//清空一下
                        if (!CollectionUtils.isEmpty(baseBean.getData()))
                            mSetRecordBeans.addAll(baseBean.getData());//全部添加进来

                        getView().showNormalView();//展示正常view
                        getView().refreshSetRecordList(mSetRecordBeans);//刷新一下

                        if (CollectionUtils.isEmpty(baseBean.getData()) || baseBean.getData().size() < NUM)
                            getView().setFootNoMoreData();//没有更多数据
                    } else {
                        getView().showErrorHint(baseBean.getMsg());//弹出提示信息
                        getView().showNetError();//展示网络错误
                    }
                    break;
                case INIT_NET_ERROR:
                    getView().showNetError();//展示网络错误
                    break;
                case INIT_COMPLETE:
                    getView().hideLoading();
                    break;
                case LOAD_MORE_SUCCESS:
                    BaseBean<List<SetRecordBean>> baseBean2 = (BaseBean<List<SetRecordBean>>) msg.obj;//得到用户id
                    if (baseBean2.getCode() == 1) {
                        if (!CollectionUtils.isEmpty(baseBean2.getData())) {
                            mSetRecordBeans.addAll(baseBean2.getData());//全部添加进来
                            getView().refreshSetRecordList(mSetRecordBeans);//刷新一下
                            if (baseBean2.getData().size() < NUM)
                                getView().setFootNoMoreData();//没有更多数据
                        } else
                            getView().setFootNoMoreData();//没有更多数据
                    } else {
                        getView().showErrorHint(baseBean2.getMsg());//弹出提示信息
                    }
                    break;
                case LOAD_MORE_NET_ERROR:
                    getView().showErrorHint("网络错误，加载失败");
                    break;
                case LOAD_MORE_COMPLETE:
                    getView().completeLoadMore();//完成加载更多
                    break;

            }
        }
    };

    @Override
    public void initCanGetReward() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().registerNetworkListener();//注册网络监听
            getView().showNetError();//展示网络错误
            return;
        }
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;

        getView().showLoading("数据加载中..");

        mModel.initCanGetReward(userBean.getUser_id(), new OnGetInfoListener<BaseBean<List<SetRecordBean>>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INIT_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INIT_NET_ERROR);//网络错误
            }

            @Override
            public void onResult(BaseBean<List<SetRecordBean>> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INIT_SUCCESS;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }

    @Override
    public void loadMoreCanGetReward() {

        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            getView().completeLoadMore();//完成加载更多
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;

        int set_record_id = 0;
        if (!CollectionUtils.isEmpty(mSetRecordBeans))
            set_record_id = mSetRecordBeans.get(mSetRecordBeans.size() - 1).getSet_record_id();//第一个store_bean的id

        mModel.loadMoreCanGetReward(userBean.getUser_id(), set_record_id
                ,
                new OnGetInfoListener<BaseBean<List<SetRecordBean>>>() {
                    @Override
                    public void onComplete() {
                        mHandler.sendEmptyMessage(LOAD_MORE_COMPLETE);
                    }

                    @Override
                    public void onNetError() {
                        mHandler.sendEmptyMessage(LOAD_MORE_NET_ERROR);//网络错误
                    }

                    @Override
                    public void onResult(BaseBean<List<SetRecordBean>> info) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = LOAD_MORE_SUCCESS;//结果
                        msg.obj = info;
                        mHandler.sendMessage(msg);//发送信息
                    }
                });
    }

}
