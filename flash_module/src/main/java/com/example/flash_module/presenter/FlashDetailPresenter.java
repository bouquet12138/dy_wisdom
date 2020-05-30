package com.example.flash_module.presenter;

import android.os.Handler;
import android.os.Message;


import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BaseBean;
;
import com.example.common_lib.model.FlashModel;
import com.example.flash_module.contract.FlashDetailContract;


public class FlashDetailPresenter extends MVPBasePresenter<FlashDetailContract.IView>
        implements FlashDetailContract.IPresenter {

    private FlashModel mModel = new FlashModel();

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
                    BaseBean baseBean = (BaseBean) msg.obj;//得到快讯bean
                    if (baseBean.getCode() == 1) {

                    } else {

                    }
                    break;
                case NET_ERROR:

                    break;
                case COMPLETE:
                    break;
            }
        }
    };


    @Override
    public void addReadingVolume(int flash_id) {

        if (!isViewAttached())
            return;

        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            return;
        }

        mModel.addFlashReadingVolume(flash_id, new OnGetInfoListener<BaseBean>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(NET_ERROR);//网络错误
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = SUCCESS;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });


    }
}
