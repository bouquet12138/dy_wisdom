package com.example.flash_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.FlashBean;

import java.util.List;

public interface FlashDetailContract {

    /**
     * IView
     */
    interface IView extends IAppMvpView {

    }

    interface IPresenter {

        /**
         * 添加阅读量
         *
         * @param flash_id
         */
        void addReadingVolume(int flash_id);

    }

}
