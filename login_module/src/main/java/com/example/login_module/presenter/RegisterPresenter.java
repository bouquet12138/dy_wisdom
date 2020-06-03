package com.example.login_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.bean.SMSBean;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.SetBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.LoginAndRegisterModel;
import com.example.common_lib.model.SMSModel;
import com.example.common_lib.model.SetModel;
import com.example.common_lib.model.UserModel;
import com.example.login_module.contract.RegisterContract;


import java.util.Random;

public class RegisterPresenter extends MVPBasePresenter<RegisterContract.IView>
        implements RegisterContract.IPresenter {


    private String mVerCode = "";//验证码
    private String mMobile;

    private UserModel mModel = new UserModel();
    private SetModel mSetModel = new SetModel();
    private SMSModel mSMSModel = new SMSModel();

    private LoginAndRegisterModel mRegisterModel = new LoginAndRegisterModel();

    private final int QR_SUCCESS = 0;
    private final int QR_NET_ERROR = 1;//网络错误
    private final int QR_ON_COMPLETE = 2;//完成

    private final int QR_COUNT = 3;//短信验证码计数

    private final int INFO_ON_RESULT = 4;
    private final int INFO_NET_ERROR = 5;//网络错误
    private final int INFO_ON_COMPLETE = 6;//完成

    private final int REGISTER_ON_RESULT = 7;
    private final int REGISTER_NET_ERROR = 8;//网络错误
    private final int REGISTER_ON_COMPLETE = 9;//完成


    private final int PLACE_INFO_NET_ERROR = 10;//安置者信息网络错误
    private final int PLACE_INFO_ON_RESULT = 11;//获取到安置者信息

    private final int SET_INFO_ON_RESULT = 12;

    private int mCountNum = 60;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isViewAttached())//没有view绑定直接返回
                return;
            switch (msg.what) {
                case QR_SUCCESS:
                    SMSBean smsBean = (SMSBean) msg.obj;//得到发送过来的对象
                    if (smsBean.getError_code() == 0)
                        getView().showSuccessHint(smsBean.getReason());
                    else
                        getView().showErrorHint(smsBean.getReason());
                    break;
                case QR_NET_ERROR:
                    getView().showErrorHint("网络错误，验证码发送失败");
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
                case INFO_ON_RESULT:
                    BaseBean<UserBean> baseBean1 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    // getView().showToast(baseBean.getMsg());//弹出提示信息
                    if (baseBean1.getCode() == 1) {
                        getView().setUserInfo(baseBean1.getData());
                    }
                    break;
                case INFO_NET_ERROR:
                    //  getView().setUserInfo(baseBean1.getData());
                    break;
                case REGISTER_ON_RESULT:
                    BaseBean<Integer> baseBean2 = (BaseBean<Integer>) msg.obj;//得到用户id
                    // getView().showToast(baseBean.getMsg());//弹出提示信息
                    if (baseBean2.getCode() == 1) {
                        getView().registerSuccess(baseBean2.getData());
                    } else {
                        getView().showErrorHint(baseBean2.getMsg());
                    }
                    break;
                case REGISTER_NET_ERROR:
                    getView().showToast("网络错误");
                    break;
                case REGISTER_ON_COMPLETE:
                case PLACE_INFO_NET_ERROR:
                    getView().hideLoading();//隐藏加载进度框
                    break;
                case PLACE_INFO_ON_RESULT:
                    BaseBean<UserBean> baseBean3 = (BaseBean<UserBean>) msg.obj;//得到用户id
                    if (baseBean3.getCode() == 1) {
                        innerRegister(baseBean3.getData().getUser_id());//得到安置者的id
                    } else {
                        getView().hideLoading();//隐藏进度框
                    }
                    break;
                case SET_INFO_ON_RESULT://获取成功
                    BaseBean<SetBean> baseBean4 = (BaseBean<SetBean>) msg.obj;//得到用户id
                    if (baseBean4.getCode() == 1)//成功
                        getView().setSetInfo(baseBean4.getData());//设置套餐信息
                    break;
            }
        }
    };


    @Override
    public void getSetInfo() {
        if (!isViewAttached())//没有view绑定
            return;
        mSetModel.getSetByName(SetBean.SET_TYPE_JOIN, new OnGetInfoListener<BaseBean<SetBean>>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onNetError() {

            }

            @Override
            public void onResult(BaseBean<SetBean> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = SET_INFO_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    /**
     * 发送验证码
     */
    @Override
    public void sendQrCode() {

        if (!isViewAttached())//没有视图绑定,直接返回
            return;

        mMobile = getView().getPhone();//手机号
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

    /**
     * 注册
     */
    @Override
    public void register() {

        if (!isViewAttached())
            return;

        if (!isRight())//输入不正确，直接返回
            return;

        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络错误");
            return;
        }

        if (!mVerCode.equals(getView().getVerCode()) || !getView().getPhone().equals(mMobile)) {//如果验证码不相同
            getView().showErrorHint("请输入正确的验证码");
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null) {
            getView().showErrorHint("请先登陆");
            return;
        }

        getView().showLoading("注册中..");
        getPlaceUserInfo();//得到安置者信息
    }

    /**
     * 真正的注册方法
     *
     * @param placement_user_id
     */
    private void innerRegister(int placement_user_id) {
        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null) {
            getView().hideLoading();//隐藏进度条
            return;
        }

        mRegisterModel.register(getView().getPhone(), getView().getLoginPass(), getView().getPayPass(), userBean.getUser_id(), placement_user_id, getView().getName(), getView().getRecommendUserPayPass(), new OnGetInfoListener<BaseBean<Integer>>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(REGISTER_ON_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(REGISTER_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean<Integer> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = REGISTER_ON_RESULT;//结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    /**
     * 得到安置者的信息
     */
    private void getPlaceUserInfo() {
        if (!isViewAttached())
            return;

        String phone = getView().getPlaceUserPhone();

        mModel.getUserInfoWithPhone(phone, new OnGetInfoListener<BaseBean<UserBean>>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(PLACE_INFO_NET_ERROR);//安置者网络错误
            }

            @Override
            public void onResult(BaseBean<UserBean> info) {
                Message msg = mHandler.obtainMessage();
                msg.what = PLACE_INFO_ON_RESULT;//安置者结果
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });
    }

    /**
     * 得到安置者信息
     */
    @Override
    public void getUserInfo() {
        if (!isViewAttached())
            return;

        String phone = getView().getPlaceUserPhone();

        mModel.getUserInfoWithPhone(phone, new OnGetInfoListener<BaseBean<UserBean>>() {
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

    @Override
    public boolean isRight() {
        if (!isViewAttached())
            return false;

        String name = getView().getName();//得到姓名
        String phone = getView().getPhone();//手机号
        String placePhoneNum = getView().getPlaceUserPhone();
        String verCode = getView().getVerCode();//验证码

        String loginPass = getView().getLoginPass();

        String payPass = getView().getPayPass();//支付密码
        String recommendUserPayPass = getView().getRecommendUserPayPass();//推荐者的支付密码

        if (TextUtils.isEmpty(name)) {
            getView().showErrorHint("姓名不能为空");
            return false;
        }

        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            getView().showErrorHint("请输入正确的手机号");
            return false;
        }
        if (TextUtils.isEmpty(placePhoneNum) || placePhoneNum.length() < 11) {
            getView().showErrorHint("请输入正确安置者手机号");
            return false;
        }

        if (TextUtils.isEmpty(verCode) || !verCode.equals(mVerCode)) {
            getView().showErrorHint("请输入正确的验证码");
            return false;
        }

        if (TextUtils.isEmpty(loginPass) || loginPass.length() < 6) {
            getView().showErrorHint("登陆密码至少6位");
            return false;
        }

        if (payPass.length() != 6) {
            getView().showErrorHint("支付密码必须6位");
            return false;
        }

        if (TextUtils.isEmpty(recommendUserPayPass)) {
            getView().showErrorHint("请输入您的支付密码");
            return false;
        }

        return true;
    }

}
