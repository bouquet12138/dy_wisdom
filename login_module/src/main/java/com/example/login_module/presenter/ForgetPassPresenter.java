package com.example.login_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.bean.SMSBean;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.LoginAndRegisterModel;
import com.example.common_lib.model.SMSModel;
import com.example.common_lib.model.UserModel;
import com.example.login_module.contract.ForgetPassContract;

import java.util.Random;

public class ForgetPassPresenter extends MVPBasePresenter<ForgetPassContract.IView>
        implements ForgetPassContract.IPresenter {

    private LoginAndRegisterModel mModel = new LoginAndRegisterModel();//登陆和注册model
    private SMSModel mSMSModel = new SMSModel();

    private UserModel mUserModel = new UserModel();

    private int mCountNum;

    private final int QR_SUCCESS = 0;
    private final int QR_NET_ERROR = 1;//网络错误
    private final int QR_ON_COMPLETE = 2;//完成

    private final int QR_COUNT = 3;//短信验证码计数

    private final int SUBMIT_ON_RESULT = 4;
    private final int SUBMIT_NET_ERROR = 8;//网络错误
    private final int SUBMIT_ON_COMPLETE = 9;//完成

    private String mVerCode = "";//验证码
    private String mMobile;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())//没有视图绑定直接返回
                return;
            switch (msg.what) {
                case QR_SUCCESS:
                    SMSBean smsBean = (SMSBean) msg.obj;
                    if (smsBean.getError_code() == 0) {
                        getView().showSuccessHint("验证码发送成功");
                    } else {
                        getView().showErrorHint(smsBean.getReason());
                        mVerCode = "";
                    }
                    break;
                case QR_NET_ERROR:
                    getView().showErrorHint("网络错误");
                    mVerCode = "";
                    break;
                case QR_ON_COMPLETE:
                    break;
                case QR_COUNT:
                    if (mCountNum <= 0) {
                        getView().setSendBtEnable(true);//发送验证码的按钮可用
                        getView().setSendBtText("获取验证码");
                    } else {
                        getView().setSendBtText("(" + mCountNum + "s后重试)");
                        mHandler.sendEmptyMessageDelayed(QR_COUNT, 1000);//1秒刷新一下
                    }
                    mCountNum--;
                    break;
                case SUBMIT_ON_RESULT:
                    BaseBean<UserBean> baseBean2 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    if (baseBean2.getCode() == 1) {
                        getView().showToast(baseBean2.getMsg());//弹出提示信息
                        getView().finishActivity();//销毁活动
                    }else{
                        getView().showErrorHint(baseBean2.getMsg());
                    }
                    break;
                case SUBMIT_NET_ERROR:
                    getView().showToast("网络错误，密码修改失败");
                    break;
                case SUBMIT_ON_COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };


    /**
     * 发送验证码
     */
    @Override
    public void sendQrCode() {
        if (!isViewAttached())//没有视图绑定,直接返回
            return;
        mMobile = getView().getPhoneNum();//手机号

        if (TextUtils.isEmpty(mMobile) || mMobile.length() != 11) {
            getView().showErrorHint("请输入正确的手机号");
            return;
        }
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            return;
        }
        mCountNum = 60;
        getView().setSendBtEnable(false);//发送按钮不可用
        mHandler.sendEmptyMessage(QR_COUNT);//计数
        Random random = new Random();

        mVerCode = "";
        for (int i = 0; i < 6; i++) {
            mVerCode += random.nextInt(10);
        }

        mSMSModel.sendQrCode(mMobile, mVerCode, new OnGetInfoListener<SMSBean>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(QR_ON_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(QR_NET_ERROR);
            }

            @Override
            public void onResult(SMSBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = QR_SUCCESS;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    @Override
    public void submit() {
        if (!isViewAttached())//没有视图绑定,直接返回
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            return;
        }
        if (TextUtils.isEmpty(mVerCode)) {//如果验证码为空
            getView().showErrorHint("请先获取验证码");
            return;
        }

        String mobile = getView().getPhoneNum();

        if (!mVerCode.equals(getView().getVerCode()) || !mobile.equals(mMobile)) {//如果验证码不相同
            getView().showErrorHint("请输入正确的验证码");
            return;
        }

        if (getView().isForgetPay()) {//忘记支付密码
            mModel.modify_pay_password(mobile, getView().getLoginPassword(), new OnGetInfoListener<BaseBean>() {
                @Override
                public void onComplete() {
                    mHandler.sendEmptyMessage(SUBMIT_ON_COMPLETE);
                }

                @Override
                public void onNetError() {
                    mHandler.sendEmptyMessage(SUBMIT_NET_ERROR);//网络错误
                }

                @Override
                public void onResult(BaseBean info) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = SUBMIT_ON_RESULT;//结果
                    msg.obj = info;
                    mHandler.sendMessage(msg);//发送信息
                }
            });
        } else {
            mModel.modify_login_password(mobile, getView().getLoginPassword(), new OnGetInfoListener<BaseBean>() {
                @Override
                public void onComplete() {
                    mHandler.sendEmptyMessage(SUBMIT_ON_COMPLETE);
                }

                @Override
                public void onNetError() {
                    mHandler.sendEmptyMessage(SUBMIT_NET_ERROR);//网络错误
                }

                @Override
                public void onResult(BaseBean info) {
                    Message msg = mHandler.obtainMessage();
                    msg.what = SUBMIT_ON_RESULT;//结果
                    msg.obj = info;
                    mHandler.sendMessage(msg);//发送信息
                }
            });
        }

    }


}
