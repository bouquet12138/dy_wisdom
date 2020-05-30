package com.example.flash_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.model.FlashModel;
import com.example.flash_module.contract.FlashFragmentContract;

import java.util.ArrayList;
import java.util.List;

public class FlashFragmentPresenter extends MVPBasePresenter<FlashFragmentContract.IView>
        implements FlashFragmentContract.IPresenter {

    private static final String TAG = "FlashPresenter";

    private FlashModel mModel = new FlashModel();
    private List<FlashBean> mFlashBeans = new ArrayList<>();//一个集合

    private final int INIT_SUCCESS = 0;//成功
    private final int INIT_NET_ERROR = 1;//网络错误
    private final int INIT_COMPLETE = 2;//完成


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case INIT_SUCCESS:
                    BaseBean<List<FlashBean>> baseBean = (BaseBean<List<FlashBean>>) msg.obj;//得到快讯bean
                    if (baseBean.getCode() == 1) {
                        mFlashBeans.clear();//清空一下
                        if (baseBean.getData() != null)
                            for (int i = 0; i < 2 && i < baseBean.getData().size(); i++)
                                mFlashBeans.add(baseBean.getData().get(i));

                        getView().refreshFlashList(mFlashBeans);//刷新一下
                    } else {
                        getView().showToast(baseBean.getMsg());//弹出提示信息
                    }
                    break;
                case INIT_NET_ERROR:
                    break;
                case INIT_COMPLETE:
                    //  getView().hideLoading();
                    break;
            }
        }
    };

    @Override
    public void initFlashInfo() {
        if (!isViewAttached())
            return;
        //getView().showLoading("数据加载中..");

        mModel.initFlashInfo(new OnGetInfoListener<BaseBean<List<FlashBean>>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INIT_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INIT_NET_ERROR);//网络错误
            }

            @Override
            public void onResult(BaseBean<List<FlashBean>> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INIT_SUCCESS;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    @Override
    public void addReadVolume(FlashBean _flashBean) {

        if (!isViewAttached())
            return;
        for (FlashBean flashBean : mFlashBeans) {
            if (_flashBean.getFlash_id() == flashBean.getFlash_id()) {
                flashBean.setReading_volume(flashBean.getReading_volume() + 1);//浏览量加1
                getView().refreshFlashList(mFlashBeans);//刷新一下
                return;
            }
        }

    }

}
