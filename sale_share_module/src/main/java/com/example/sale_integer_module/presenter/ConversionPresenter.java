package com.example.sale_integer_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.TransferBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.SaleShareModel;
import com.example.sale_integer_module.contract.ConversionContract;

public class ConversionPresenter extends MVPBasePresenter<ConversionContract.IView>
        implements ConversionContract.IPresenter {

    private SaleShareModel mModel = new SaleShareModel();

    private final int CONVERSION_ON_RESULT = 1;
    private final int CONVERSION_NET_ERROR = 2;//网络错误
    private final int CONVERSION_ON_COMPLETE = 3;//完成

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;
            switch (msg.what) {
                case CONVERSION_ON_RESULT:
                    BaseBean baseBean = (BaseBean<UserBean>) msg.obj;//得到转换信息

                    if (baseBean.getCode() == 1) {
                        getView().showToast("转换成功");
                        getView().conversionSuccess();
                    } else {
                        getView().showErrorHint(baseBean.getMsg());//弹出提示信息
                    }
                    break;
                case CONVERSION_NET_ERROR:
                    getView().showErrorHint("网络错误");
                    break;
                case CONVERSION_ON_COMPLETE:
                    getView().hideLoading();//隐藏对话框
                    break;

            }
        }
    };


    @Override
    public void conversion() {
        if (!isViewAttached())
            return;
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;

        TransferBean transferBean = new TransferBean(getView().getConversionType(), getView().getIntegralAmount(), userBean.getUser_id(), getView().getRemark(), getView().getPayPassword());
        mModel.sale_to_redeem(transferBean, new OnGetInfoListener<BaseBean>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(CONVERSION_ON_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(CONVERSION_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = CONVERSION_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

}
