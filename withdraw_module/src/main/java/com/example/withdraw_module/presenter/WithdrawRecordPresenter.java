package com.example.withdraw_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.common_lib.model.WithdrawModel;
import com.example.withdraw_module.contract.WithdrawRecordContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WithdrawRecordPresenter extends MVPBasePresenter<WithdrawRecordContract.IView>
        implements WithdrawRecordContract.IPresenter {

    private static final int NUM = 20;

    private WithdrawModel mModel = new WithdrawModel();
    private List<WithdrawBean> mWithdrawBeanRecords = new ArrayList<>();
    private boolean mSuccess = false;


    private final int INIT_SUCCESS = 0;//成功
    private final int INIT_NET_ERROR = 1;//网络错误
    private final int INIT_COMPLETE = 2;//完成

    private final int LOAD_MORE_SUCCESS = 3;//成功
    private final int LOAD_MORE_NET_ERROR = 4;//网络错
    private final int LOAD_MORE_COMPLETE = 5;//完成

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case INIT_SUCCESS:
                    BaseBean<List<WithdrawBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mWithdrawBeanRecords.clear();//清空一下
                        if (baseBean.getData() != null)
                            mWithdrawBeanRecords.addAll(baseBean.getData());//添加数据
                        getView().refreshWithdrawList(mWithdrawBeanRecords);//刷新一下
                        if (baseBean.getData() == null || baseBean.getData().size() < NUM)//为空或者尺寸小于20
                            getView().setFootNoMoreData();//没有更多数据
                    } else {
                        getView().showErrorHint(baseBean.getMsg());
                        if (!mSuccess)
                            getView().showNetError();//展示网络错误
                    }
                    break;
                case INIT_NET_ERROR:
                    if (!mSuccess)
                        getView().showNetError();//网络错误
                    else getView().showToast("网络错误");
                    break;
                case INIT_COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
                case LOAD_MORE_SUCCESS:
                    BaseBean<List<WithdrawBean>> baseBean1 = (BaseBean) msg.obj;
                    if (baseBean1.getCode() == 1) {
                        if (baseBean1.getData() == null || baseBean1.getData().size() < NUM)//尺寸小于20
                            getView().setFootNoMoreData();//没有更多数据
                        mWithdrawBeanRecords.addAll(baseBean1.getData());//加到最后
                        getView().refreshWithdrawList(mWithdrawBeanRecords);//刷新一下
                    } else {
                        getView().showErrorHint(baseBean1.getMsg());//提示信息
                    }
                    break;
                case LOAD_MORE_NET_ERROR:
                    getView().showErrorHint("网络错误");//提示信息
                    break;
                case LOAD_MORE_COMPLETE:
                    getView().completeLoadMore();//完成加载更多
                    break;

            }
        }
    };

    private static final String TAG = "WithdrawPresenter";

    /**
     * 初始化提现信息
     *
     * @param user_id
     * @param withdraw_type
     */
    public void initWithdrawInfo(int user_id, String withdraw_type) {

        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {//没链接的话
            getView().showErrorHint("网络错误");
            getView().showNetError();//展示网络错误
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();//当前用户信息
        if (userBean == null)//没有用户信息
            return;

        // getView().showLoading("信息加载中..");
        mModel.initWithdrawRecord(userBean.getUser_id(), withdraw_type, new OnGetInfoListener<BaseBean<List<WithdrawBean>>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INIT_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INIT_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INIT_SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }

    @Override
    public void loadMoreWithdrawInfo(int user_id, String withdraw_type) {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {//没链接的话
            getView().showErrorHint("网络错误");
            getView().completeLoadMore();//完成加载更多
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();//当前用户信息
        if (userBean == null)//没有用户信息
            return;

        int withdraw_id = mWithdrawBeanRecords == null ? 0 :
                mWithdrawBeanRecords.get(mWithdrawBeanRecords.size() - 1).getWithdraw_id();

        mModel.loadMoreWithdrawRecord(userBean.getUser_id(), withdraw_type, withdraw_id, new OnGetInfoListener<BaseBean<List<WithdrawBean>>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(LOAD_MORE_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(LOAD_MORE_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = LOAD_MORE_SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }

}
