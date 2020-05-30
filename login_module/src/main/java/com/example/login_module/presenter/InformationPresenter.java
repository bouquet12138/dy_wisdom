package com.example.login_module.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.BaseBean;
import com.example.common_lib.java_bean.ImageBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.model.UserModel;
import com.example.login_module.activity.UploadModel;
import com.example.login_module.contract.InformationContract;

import java.io.File;

public class InformationPresenter extends MVPBasePresenter<InformationContract.IView>
        implements InformationContract.IPresenter {

    private static final String TAG = "EditorStudentPresenter";

    private UserModel mModel = new UserModel();
    private UploadModel mUploadModel = new UploadModel();

    private final int UPLOAD_IMAGE_SUCCESS = 0;
    private final int UPLOAD_IMAGE_NET_ERROR = 1;
    private final int UPLOAD_IMAGE_COMPLETE = 2;

    private final int SUBMIT_SUCCESS = 3;
    private final int SUBMIT_NET_ERROR = 4;
    private final int SUBMIT_COMPLETE = 5;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())//没有视图绑定直接返回
                return;
            switch (msg.what) {
                case UPLOAD_IMAGE_SUCCESS:
                    BaseBean<ImageBean> baseBean = (BaseBean<ImageBean>) msg.obj;

                    if (baseBean.getCode() == 1) {
                        getView().setImageBean(baseBean.getData());//设置数据
                        uploadAllInfo();//上传所有信息
                    } else {
                        getView().showErrorHint("网络错误！");
                        getView().hideLoading();//隐藏加载框
                    }
                    break;
                case UPLOAD_IMAGE_NET_ERROR:
                    getView().showErrorHint("网络错误！");
                    getView().hideLoading();//隐藏加载框
                    break;
                case UPLOAD_IMAGE_COMPLETE:
                    break;
                case SUBMIT_SUCCESS:
                    BaseBean baseBean1 = (BaseBean) msg.obj;
                    //getView().showToast(baseBean1.getMsg());
                    if (baseBean1.getCode() == 1) {
                        if (NowUserInfo.getNowUserId() == getView().getUserID()) {
                            UserBean nowUserInfo = NowUserInfo.getNowUserInfo();
                            nowUserInfo.setInfo(getView().getModifyUserBean());//设置修改的信息
                            NowUserInfo.setNowUserInfo(nowUserInfo);//更新一下信息
                        }
                        getView().showToast(baseBean1.getMsg());
                        getView().finishActivity();//销毁活动
                    } else {
                        getView().showErrorHint(baseBean1.getMsg());
                    }
                    break;
                case SUBMIT_NET_ERROR:
                    getView().showErrorHint("网络错误！");
                    break;
                case SUBMIT_COMPLETE:
                    getView().hideLoading();//隐藏加载框
                    break;
            }
        }
    };

    @Override
    public void submit() {
        if (!isViewAttached())
            return;

        if (!NetworkUtils.isConnected()) {
            getView().showErrorHint("网络不可用！");
            return;
        }

        getView().showLoading("信息修改中...");
        if (getView().isModifyHeadImage())
            uploadImage();//上传图片
        else
            uploadAllInfo();
    }

    /**
     * 上传图片
     */
    private void uploadImage() {
        File file = new File(getView().getImageLocalPath());
        if (!file.exists()) {
            getView().showErrorHint("图片破损");
            getView().hideLoading();
            return;
        }

        Bitmap bitmap = BitmapFactory.decodeFile(getView().getImageLocalPath());
        mUploadModel.uploadImage(getView().getImageLocalPath(), file.getName(),
                bitmap.getWidth(), bitmap.getHeight(),
                "head_img", "head_image",
                new OnGetInfoListener<BaseBean<ImageBean>>() {
                    @Override
                    public void onComplete() {
                        mHandler.sendEmptyMessage(UPLOAD_IMAGE_COMPLETE);
                    }

                    @Override
                    public void onNetError() {
                        mHandler.sendEmptyMessage(UPLOAD_IMAGE_NET_ERROR);
                    }

                    @Override
                    public void onResult(BaseBean<ImageBean> info) {
                        Message msg = mHandler.obtainMessage();
                        msg.what = UPLOAD_IMAGE_SUCCESS;
                        msg.obj = info;
                        Log.d(TAG, "onResult: " + msg.getData());
                        mHandler.sendMessage(msg);//发送信息
                    }
                });
    }

    /**
     * 上传所有信息
     */
    private void uploadAllInfo() {

        UserBean userBean = getView().getModifyUserBean();

        if (userBean.getHead_img_id() == 0 && userBean.getHead_img() != null) {
            userBean.setHead_img_id(userBean.getHead_img().getImage_id());//设置头像id
        }

        mModel.modifyUserInfo(userBean, new OnGetInfoListener<BaseBean>() {
            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(SUBMIT_COMPLETE);
            }

            @Override
            public void onNetError() {
                mHandler.sendEmptyMessage(SUBMIT_NET_ERROR);
            }

            @Override
            public void onResult(BaseBean info) {
                Message msg = mHandler.obtainMessage();
                msg.what = SUBMIT_SUCCESS;
                msg.obj = info;
                mHandler.sendMessage(msg);//发送信息
            }
        });

    }

}
