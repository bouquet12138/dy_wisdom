package com.example.withdraw_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.common_lib.model.WithdrawModel;
import com.example.withdraw_module.contract.WithdrawContract;


public class WithdrawPresenter extends MVPBasePresenter<WithdrawContract.IView>
        implements WithdrawContract.IPresenter {

    private WithdrawModel mModel = new WithdrawModel();

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
                    BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        getView().showToast("提现成功，等待处理");
                        // getView().showToast(baseBean.getMsg());//展示提示信息
                        getView().withdrawSuccess();//提现成功
                    } else {
                        getView().showErrorHint(baseBean.getMsg());//提示信息
                    }
                    break;
                case NET_ERROR:
                    getView().showErrorHint("网络错误");//提示信息
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };

    private static final String TAG = "WithdrawRecordPresenter";

    @Override
    public void withdrawInfo(WithdrawBean withdrawBean) {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            return;
        }

        Log.d(TAG, "getMyTeamInfo: " + withdrawBean);

        getView().showLoading("信息加载中..");
        mModel.withdraw(withdrawBean, new OnGetInfoListener<BaseBean>() {
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
