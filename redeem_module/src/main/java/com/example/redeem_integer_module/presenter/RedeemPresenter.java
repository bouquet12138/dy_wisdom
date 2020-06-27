package com.example.redeem_integer_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.RedeemRecordBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.RedeemModel;
import com.example.redeem_integer_module.contract.RedeemContract;

import java.util.ArrayList;
import java.util.List;

public class RedeemPresenter extends MVPBasePresenter<RedeemContract.IView>
        implements RedeemContract.IPresenter {

    private final int NUM = 20;

    private RedeemModel mModel = new RedeemModel();
    private List<RedeemRecordBean> mRedeemRecordBeans = new ArrayList<>();

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
                    BaseBean<List<RedeemRecordBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mRedeemRecordBeans.clear();//清空一下
                        if (baseBean.getData() != null)
                            mRedeemRecordBeans.addAll(baseBean.getData());//添加数据
                        getView().refreshRedeemList(mRedeemRecordBeans);//刷新一下
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
                    else getView().showErrorHint("网络错误");
                    break;
                case INIT_COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
                case LOAD_MORE_SUCCESS:
                    BaseBean<List<RedeemRecordBean>> baseBean1 = (BaseBean) msg.obj;
                    if (baseBean1.getCode() == 1) {
                        if (baseBean1.getData() == null || baseBean1.getData().size() < NUM)//尺寸小于20
                            getView().setFootNoMoreData();//没有更多数据
                        mRedeemRecordBeans.addAll(baseBean1.getData());//加到最后
                        getView().refreshRedeemList(mRedeemRecordBeans);//刷新一下
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

    private static final String TAG = "RedeemPresenter";

    public void initData() {

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

        Log.d(TAG, "initData: ");

        // getView().showLoading("信息加载中..");
        mModel.initRedeemRecord(userBean.getUser_id(), getView().getType(), new OnGetInfoListener<BaseBean<List<RedeemRecordBean>>>() {
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
    public void loadMoreData() {
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

        int sale_share_id = mRedeemRecordBeans == null ? 0 :
                mRedeemRecordBeans.get(mRedeemRecordBeans.size() - 1).getRedeem_id();

        mModel.loadMoreRedeemRecord(userBean.getUser_id(), getView().getType(), sale_share_id, new OnGetInfoListener<BaseBean<List<RedeemRecordBean>>>() {
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
