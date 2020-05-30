package com.example.common_lib.presenter;

import android.os.Handler;
import android.os.Message;

import com.example.base_lib.base.MVPBasePresenter;
import com.example.base_lib.util.SaveImageUtil;
import com.example.common_lib.contract.PicContract;
import com.example.common_lib.info.AppInfo;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.util.DownloadUtil;

import java.io.File;

public class PicPresenter extends MVPBasePresenter<PicContract.IView>
        implements PicContract.IPresenter {

    private final int DOWN_LOAD_SUCCESS = 0;
    private final int DOWN_LOAD_NET_ERROR = 1;
    private final int DOWN_LOAD_PROGRESS = 2;
    private final int FILE_EXIST = 3;

    private boolean mIsDowning = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())//没有视图绑定直接返回
                return;
            switch (msg.what) {
                case DOWN_LOAD_SUCCESS:
                    mIsDowning = false;
                    getView().showToast("下载成功");
                    getView().showProgress(false);
                    break;
                case DOWN_LOAD_NET_ERROR:
                    mIsDowning = false;
                    getView().showToast("下载失败");
                    getView().showProgress(false);
                    break;
                case DOWN_LOAD_PROGRESS:
                    getView().setProgress(msg.arg1);//设置进度
                    break;
                case FILE_EXIST:
                    mIsDowning = false;//不是正在下载了
                    getView().showProgress(false);//隐藏进度框
                    getView().showToast("图片已存在");
                    break;
            }
        }
    };

    @Override
    public void downLoadImg(String imagePath) {

        if (!isViewAttached())//没有视图绑定直接返回
            return;

        mIsDowning = true;//正在下载

        String destFileName = SaveImageUtil.getImageFilePath(AppInfo.IMAGE_DIR, imagePath);
        getView().showProgress(true);//展示进度框

        DownloadUtil.download(ServerInfo.getImageAddress(imagePath), destFileName, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onFileExist() {
                mHandler.sendEmptyMessage(FILE_EXIST);//图片已存在
            }

            @Override
            public void onDownloadSuccess(File file) {
                SaveImageUtil.sendBroadCast(file);
                mHandler.sendEmptyMessage(DOWN_LOAD_SUCCESS);
            }

            @Override
            public void onDownloading(int progress) {
                Message msg = mHandler.obtainMessage();
                msg.arg1 = progress;
                msg.what = DOWN_LOAD_PROGRESS;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onDownloadFailed(Exception e) {
                mHandler.sendEmptyMessage(DOWN_LOAD_NET_ERROR);
            }
        });

    }

    @Override
    public boolean isDowning() {
        return mIsDowning;
    }
}
