package com.example.my_team_module.presenter;

import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.UserModel;
import com.example.my_team_module.contract.MyTeamContract;

import java.util.List;

public class MyTeamPresenter extends MVPBasePresenter<MyTeamContract.IView>
        implements MyTeamContract.IPresenter {

    private UserModel mModel = new UserModel();

    private final int SUCCESS = 0;//成功
    private final int NET_ERROR = 1;//网络错误
    private final int COMPLETE = 2;//完成

    private boolean mSuccess = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    BaseBean<List<UserBean>> baseBean = (BaseBean) msg.obj;
                    if (baseBean.getCode() == 1) {
                        mSuccess = true;//信息获取成功
                        getView().setTeamInfo(baseBean.getData());//设置团队信息
                    } else {
                        getView().showErrorHint(baseBean.getMsg());//展示提示信息
                        if (!mSuccess)
                            getView().showNetError();//展示网络错误
                    }
                    break;
                case NET_ERROR:
                    if (!mSuccess)
                        getView().showNetError();//网络错误
                    else getView().showToast("网络错误");
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度框
                    break;
            }
        }
    };

    private static final String TAG = "MyTeamPresenter";

    @Override
    public void getMyTeamInfo(int userId, boolean isPlace) {
        if (!isViewAttached())
            return;
        if (!NetworkUtils.isConnected()) {
            getView().showNetError();//展示网络错误
            getView().registerNetworkListener();//注册网络监听
            return;
        }

        getView().showLoading("信息加载中..");

        mModel.myTeam(userId, isPlace, new OnGetInfoListener<BaseBean<List<UserBean>>>() {
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
