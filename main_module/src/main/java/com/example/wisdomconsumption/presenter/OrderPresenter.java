package com.example.wisdomconsumption.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;

import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.DeliverNumBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.GoodModel;
import com.example.wisdomconsumption.contract.OrderContract;

public class OrderPresenter extends MVPBasePresenter<OrderContract.IView>
        implements OrderContract.IPresenter {

    private GoodModel mModel = new GoodModel();

    private final int SUCCESS = 0;//成功
    private final int NET_ERROR = 1;//网络错误
    private final int COMPLETE = 2;//完成

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    BaseBean<DeliverNumBean> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        getView().setWaitDelivery(baseBean.getData().getWait_delivery());
                        getView().setHadDelivery(baseBean.getData().getHad_delivery());
                    }
                    break;
                case NET_ERROR:
                   // getView().showToast("网络错误");
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };

    @Override
    public void getDeliverGoodNum() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();//当前用户信息
        if (userBean == null)
            return;

        mModel.getDeliverGoodNum(userBean.getUser_id(), new OnGetInfoListener<BaseBean<DeliverNumBean>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }
}
