package com.example.sale_integer_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.TransferBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.UserModel;
import com.example.sale_integer_module.contract.TransfersContract;


public class TransfersPresenter extends MVPBasePresenter<TransfersContract.IView>
        implements TransfersContract.IPresenter {

    private UserModel mModel = new UserModel();

    private final int TRANSFER_ON_RESULT = 1;
    private final int TRANSFER_NET_ERROR = 2;//网络错误
    private final int TRANSFER_ON_COMPLETE = 3;//完成

    private final int INFO_ON_RESULT = 4;
    private final int INFO_NET_ERROR = 5;//网络错误
    private final int INFO_ON_COMPLETE = 6;//完成

    private final int TRANSFER_ON_RESULT_2 = 7;
    private final int TRANSFER_NET_ERROR_2 = 8;//网络错误
    private final int TRANSFER_ON_COMPLETE_2 = 9;//完成

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;
            switch (msg.what) {
                case TRANSFER_ON_RESULT:
                    BaseBean<UserBean> baseBean = (BaseBean<UserBean>) msg.obj;//得到用户id
                    //
                    if (baseBean.getCode() == 1) {
                        if (baseBean.getData() != null)
                            payrollTransfers(baseBean.getData().getUser_id());
                    } else {
                        getView().showErrorHint(baseBean.getMsg());//弹出提示信息
                        getView().hideLoading();//隐藏对话框
                    }
                    break;
                case TRANSFER_NET_ERROR:
                    getView().showErrorHint("网络错误");
                    getView().hideLoading();//隐藏对话框
                    break;
                case INFO_ON_RESULT:
                    BaseBean<UserBean> baseBean1 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    if (baseBean1.getCode() == 1) {
                        getView().setUserInfo(baseBean1.getData());
                    }
                    break;
                case INFO_NET_ERROR:

                    break;
                case TRANSFER_ON_RESULT_2:
                    BaseBean baseBean2 = (BaseBean) msg.obj;//得到用户id

                    if (baseBean2.getCode() == 1) {
                        // getView().transferSuccess();//转账成功框
                        getView().showSuccessHint(baseBean2.getMsg());
                    } else {
                        getView().showErrorHint(baseBean2.getMsg());
                    }
                    break;
                case TRANSFER_NET_ERROR_2:
                    getView().showErrorHint("网络错误，转账失败");
                    break;
                case TRANSFER_ON_COMPLETE_2:
                    getView().hideLoading();
                    break;
            }
        }
    };

    private void payrollTransfers(int target_user_id) {
        if (!isViewAttached())
            return;
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null)
            return;

        TransferBean transferBean = new TransferBean(getView().getTransactionType(), getView().getIntegralAmount(), userBean.getUser_id(), target_user_id,
                getView().getRemark(), getView().getPayPassword());
        mModel.integerTransfers(transferBean, new OnGetInfoListener<BaseBean>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(TRANSFER_ON_COMPLETE_2);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(TRANSFER_NET_ERROR_2);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = TRANSFER_ON_RESULT_2;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    @Override
    public void payrollTransfers() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            return;
        }

        getView().showLoading("转账中..");

        mModel.getUserInfoWithPhone(getView().getTargetUserPhone(), new OnGetInfoListener<BaseBean<UserBean>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(TRANSFER_ON_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(TRANSFER_NET_ERROR);//网络错误
            }

            @Override
            public void onResult(BaseBean<UserBean> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = TRANSFER_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    @Override
    public void getUserInfo(String phone_num) {
        mModel.getUserInfoWithPhone(phone_num, new OnGetInfoListener<BaseBean<UserBean>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(INFO_ON_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(INFO_NET_ERROR);//网络错误
            }

            @Override
            public void onResult(BaseBean<UserBean> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = INFO_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }
}
