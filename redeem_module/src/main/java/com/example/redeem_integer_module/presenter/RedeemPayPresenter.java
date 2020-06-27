package com.example.redeem_integer_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.TransferBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.RedeemModel;
import com.example.redeem_integer_module.contract.RedeemPayContract;


public class RedeemPayPresenter extends MVPBasePresenter<RedeemPayContract.IView>
        implements RedeemPayContract.IPresenter {

    private RedeemModel mModel = new RedeemModel();

    private final int PAY_SUCCESS = 0;//成功
    private final int PAY_NET_ERROR = 1;//网络错误
    private final int PAY_COMPLETE = 2;//完成


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case PAY_SUCCESS:
                    BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        getView().showToast("转账成功");
                        getView().paySuccess();
                    } else {
                        getView().showErrorHint(baseBean.getMsg());
                    }
                    break;
                case PAY_NET_ERROR:
                    getView().showErrorHint("网络错误");
                    break;
                case PAY_COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };

    @Override
    public void redeemPay() {
        if (!isViewAttached())
            return;
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;
        TransferBean transferBean = new TransferBean(getView().getIntegralAmount(), userBean.getUser_id(), getView().getTargetUserId(), getView().getRemark(), getView().getPayPassword());

        getView().showLoading("支付中...");

        mModel.payToMerchant(transferBean, new OnGetInfoListener<BaseBean>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(PAY_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(PAY_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = PAY_SUCCESS;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }
}
