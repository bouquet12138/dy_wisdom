package com.example.common_lib.contract;


import com.example.base_lib.base.IMVPBaseView;

public interface PicContract {

    interface IView extends IMVPBaseView {

        void showProgress(boolean show);

        void setProgress(int progress);

    }

    interface IPresenter {

        /**
         * 下载图片
         *
         * @param imageName
         */
        void downLoadImg(String imageName);

        /**
         * 是否正在下载
         *
         * @return
         */
        boolean isDowning();

    }

}
