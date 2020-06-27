package com.example.set_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.common_lib.java_bean.SetBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.SetModel;
import com.example.set_module.contract.SetShopContract;

import java.util.List;


public class SetShopPresenter extends MVPBasePresenter<SetShopContract.IView>
        implements SetShopContract.IPresenter {

    private SetModel mModel = new SetModel();

    private final int BUY_SUCCESS = 0;//成功
    private final int BUY_NET_ERROR = 1;//网络错误
    private final int BUY_COMPLETE = 2;//完成

    private final int GET_SET_SUCCESS = 3;//成功
    private final int GET_SET_NET_ERROR = 4;//网络错
    private final int GET_SET_COMPLETE = 5;//完成BU


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case BUY_SUCCESS:
                    BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        getView().showSuccessHint(baseBean.getMsg());
                    } else {
                        getView().showErrorHint(baseBean.getMsg());
                    }
                    break;
                case BUY_NET_ERROR:
                    getView().showErrorHint("网络错误");
                    break;
                case GET_SET_SUCCESS:
                    BaseBean<List<SetBean>> baseBean1 = (BaseBean) msg.obj;
                    if (baseBean1.getCode() == 1) {
                        if (CollectionUtils.isEmpty(baseBean1.getData())) {
                            getView().showNoMoreData();//展示没有更多数据
                        } else {
                            getView().showNormalView();//展示正常布局
                            getView().refreshSetList(baseBean1.getData());//刷新一下
                        }
                    } else {
                        getView().showErrorHint(baseBean1.getMsg());//提示信息
                        getView().showNetError();//展示网络错误布局
                    }
                    break;
                case GET_SET_NET_ERROR:
                    getView().showNetError();//展示网络错误布局
                    break;
                case GET_SET_COMPLETE:
                case BUY_COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;

            }
        }
    };


    @Override
    public void buy_set(int set_id) {

        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {//没链接的话
            getView().showErrorHint("网络错误");
            return;
        }

        String payPass = getView().getPayPass();//得到支付密码

        if (TextUtils.isEmpty(payPass)) {
            getView().showErrorHint("请输入支付密码");
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();//当前用户信息
        if (userBean == null)//没有用户信息
            return;

        getView().showLoading("套餐购买中..");
        mModel.buySet(userBean.getUser_id(), set_id, payPass, new OnGetInfoListener<BaseBean>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(BUY_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(BUY_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = BUY_SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }

    @Override
    public void getSetList() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {//没链接的话
            getView().showNetError();//展示网络错误布局
            getView().registerNetworkListener();//注册一下
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();//当前用户信息
        if (userBean == null)//没有用户信息
            return;

        getView().showLoading("套餐加载中..");
        mModel.getSetList(userBean.getUser_id(), new OnGetInfoListener<BaseBean<List<SetBean>>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(GET_SET_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(GET_SET_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = GET_SET_SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }
}
