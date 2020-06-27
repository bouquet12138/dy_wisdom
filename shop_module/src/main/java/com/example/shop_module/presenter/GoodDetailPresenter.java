package com.example.shop_module.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.OrderRecordBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.GoodModel;
import com.example.shop_module.contract.GoodDetailContract;


public class GoodDetailPresenter extends MVPBasePresenter<GoodDetailContract.IView>
        implements GoodDetailContract.IPresenter {

    private GoodModel mModel = new GoodModel();

    private final int BUY_SUCCESS = 0;//成功
    private final int BUY_NET_ERROR = 1;//网络错误
    private final int BUY_COMPLETE = 2;//完成


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case BUY_SUCCESS:
                    BaseBean baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        getView().showToast("购买成功");
                        getView().finishActivity();//销毁活动
                    } else {
                        getView().showErrorHint(baseBean.getMsg());
                    }
                    break;
                case BUY_NET_ERROR:
                    getView().showErrorHint("网络错误");
                    break;
                case BUY_COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };

    @Override
    public void buyGood() {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {//没链接的话
            getView().showErrorHint("网络错误");
            return;
        }

        UserBean userBean = NowUserInfo.getNowUserInfo();
        if (userBean == null) {
            getView().showErrorHint("请先登录");
            return;
        }

        String name = getView().getName();//姓名
        String phone = getView().getPhone();//手机号
        String address = getView().getAddress();//地址
        String detail_address = getView().getDetailAddress();//细节
        String pass = getView().getPass();

        if (TextUtils.isEmpty(name)) {
            getView().showErrorHint("姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            getView().showErrorHint("手机号不能为空");
            return;
        }

        if (TextUtils.isEmpty(address)) {
            getView().showErrorHint("地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(detail_address)) {
            getView().showErrorHint("请填写详细地址");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            getView().showErrorHint("请输入支付密码");
            return;
        }

        getView().showLoading("信息加载中..");

        OrderRecordBean orderRecordBean = new OrderRecordBean(userBean.getUser_id(), pass, getView().getGoodId(), getView().getNum(),
                address, detail_address, name, phone);
        mModel.buyGood(orderRecordBean, new OnGetInfoListener<BaseBean>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(BUY_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(BUY_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = BUY_SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }
}
