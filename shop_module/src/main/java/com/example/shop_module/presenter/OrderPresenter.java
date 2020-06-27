package com.example.shop_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.GoodBean;
import com.example.common_lib.java_bean.OrderRecordBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.GoodModel;
import com.example.shop_module.contract.OrderContract;

import java.util.ArrayList;
import java.util.List;


public class OrderPresenter extends MVPBasePresenter<OrderContract.IView>
        implements OrderContract.IPresenter {

    private final int NUM = 20;

    private GoodModel mModel = new GoodModel();//商品列表
    private List<OrderRecordBean> mOrderRecordBeans = new ArrayList<>();

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
                    BaseBean<List<OrderRecordBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mOrderRecordBeans.clear();//清空一下
                        if (baseBean.getData() != null)
                            mOrderRecordBeans.addAll(baseBean.getData());//添加数据
                        getView().refreshOrderList(mOrderRecordBeans);//刷新一下
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
                    BaseBean<List<OrderRecordBean>> baseBean1 = (BaseBean) msg.obj;
                    if (baseBean1.getCode() == 1) {
                        if (baseBean1.getData() == null || baseBean1.getData().size() < NUM)//尺寸小于20
                            getView().setFootNoMoreData();//没有更多数据
                        mOrderRecordBeans.addAll(baseBean1.getData());//加到最后
                        getView().refreshOrderList(mOrderRecordBeans);//刷新一下
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



    @Override
    public void initData() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {//没链接的话
            getView().showErrorHint("网络错误");
            getView().showNetError();//展示网络错误
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;
        getView().showLoading("信息加载中..");


        mModel.initOrderRecord(userBean.getUser_id(), getView().getOrderType(), new OnGetInfoListener<BaseBean<List<OrderRecordBean>>>() {
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

        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;

        int order_id = CollectionUtils.isEmpty(mOrderRecordBeans) ? 0 :
                mOrderRecordBeans.get(mOrderRecordBeans.size() - 1).getGood_id();

        mModel.loadMoreOrderRecord(userBean.getUser_id(), getView().getOrderType(), order_id, new OnGetInfoListener<BaseBean<List<OrderRecordBean>>>() {
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
