package com.example.wisdomconsumption.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.OptionModel;
import com.example.wisdomconsumption.contract.OptionContract;


public class OptionPresenter extends MVPBasePresenter<OptionContract.IView>
        implements OptionContract.IPresenter {

    private OptionModel mModel = new OptionModel();

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
                    getView().showToast(baseBean.getMsg());//展示提示信息
                    if (baseBean.getCode() == 1) {
                        getView().finishActivity();//销毁活动
                    }
                    break;
                case NET_ERROR:
                    getView().showToast("网络错误");
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };

    @Override
    public void submit() {

        if (!isViewAttached())
            return;
        if (!NetworkUtils.isAvailable()) {
            getView().showErrorHint("网络错误");
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();//当前用户信息
        if (userBean == null)
            return;

        getView().showLoading("信息提交中..");
        mModel.publicOption(userBean.getUser_id(), getView().getTitleStr(), getView().getContentStr(), new OnGetInfoListener<BaseBean>() {
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
