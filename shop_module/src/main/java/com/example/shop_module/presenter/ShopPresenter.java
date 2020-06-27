package com.example.shop_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.GoodBean;
import com.example.common_lib.model.GoodModel;
import com.example.shop_module.contract.ShopContract;

import java.util.ArrayList;
import java.util.List;

public class ShopPresenter extends MVPBasePresenter<ShopContract.IView>
        implements ShopContract.IPresenter {

    private final int NUM = 20;

    private GoodModel mModel = new GoodModel();//商品列表
    private List<GoodBean> mGoodBeans = new ArrayList<>();

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
                    BaseBean<List<GoodBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mGoodBeans.clear();//清空一下
                        if (baseBean.getData() != null)
                            mGoodBeans.addAll(baseBean.getData());//添加数据
                        getView().refreshGoodList(mGoodBeans);//刷新一下
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
                    BaseBean<List<GoodBean>> baseBean1 = (BaseBean) msg.obj;
                    if (baseBean1.getCode() == 1) {
                        if (baseBean1.getData() == null || baseBean1.getData().size() < NUM)//尺寸小于20
                            getView().setFootNoMoreData();//没有更多数据
                        mGoodBeans.addAll(baseBean1.getData());//加到最后
                        getView().refreshGoodList(mGoodBeans);//刷新一下
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
    public void initData(String good_type) {

        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {//没链接的话
            // getView().showErrorHint("网络错误");
            getView().showNetError();//展示网络错误
            getView().registerNetworkListener();//注册一下
            return;
        }


        getView().showLoading("信息加载中..");
        mModel.initGoodList(getView().getShopType(), new OnGetInfoListener<BaseBean<List<GoodBean>>>() {
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
    public void loadMoreData(String good_type) {

        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {//没链接的话
            getView().showErrorHint("网络错误");
            getView().completeLoadMore();//完成加载更多
            return;
        }


        int good_id = CollectionUtils.isEmpty(mGoodBeans) ? 0 :
                mGoodBeans.get(mGoodBeans.size() - 1).getGood_id();

        mModel.loadMoreGoodList(getView().getShopType(), good_id, new OnGetInfoListener<BaseBean<List<GoodBean>>>() {
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
