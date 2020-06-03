package com.example.integer_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.SaleShareModel;
import com.example.integer_module.contract.SaleShareContract;

import java.util.ArrayList;
import java.util.List;

public class SaleSharePresenter extends MVPBasePresenter<SaleShareContract.IView>
        implements SaleShareContract.IPresenter {

    private final int NUM = 20;

    private SaleShareModel mModel = new SaleShareModel();
    private List<SaleShareRecordBean> mSaleShareRecordBeans = new ArrayList<>();

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
                    BaseBean<List<SaleShareRecordBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mSaleShareRecordBeans.clear();//清空一下
                        if (baseBean.getData() != null)
                            mSaleShareRecordBeans.addAll(baseBean.getData());//添加数据
                        getView().refreshSaleShareList(mSaleShareRecordBeans);//刷新一下
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
                    BaseBean<List<SaleShareRecordBean>> baseBean1 = (BaseBean) msg.obj;
                    if (baseBean1.getCode() == 1) {
                        if (baseBean1.getData() == null || baseBean1.getData().size() < NUM)//尺寸小于20
                            getView().setFootNoMoreData();//没有更多数据
                        mSaleShareRecordBeans.addAll(baseBean1.getData());//加到最后
                        getView().refreshSaleShareList(mSaleShareRecordBeans);//刷新一下
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

    private static final String TAG = "SaleSharePresenter";

    public void initData(String integral_type) {

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
        mModel.initSaleShareRecord(userBean.getUser_id(), integral_type, new OnGetInfoListener<BaseBean<List<SaleShareRecordBean>>>() {
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
    public void loadMoreData(String integral_type) {
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

        int sale_share_id = mSaleShareRecordBeans == null ? 0 :
                mSaleShareRecordBeans.get(mSaleShareRecordBeans.size() - 1).getSale_share_id();

        mModel.loadMoreSaleShareRecord(userBean.getUser_id(), integral_type, sale_share_id, new OnGetInfoListener<BaseBean<List<SaleShareRecordBean>>>() {
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
