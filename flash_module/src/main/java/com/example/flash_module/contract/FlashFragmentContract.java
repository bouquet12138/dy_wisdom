package com.example.flash_module.contract;


import com.example.base_lib.base.IMVPBaseView;
import com.example.common_lib.java_bean.FlashBean;

import java.util.List;

public interface FlashFragmentContract {


    interface IView extends IMVPBaseView {

        /**
         * 刷新快讯列表
         *
         * @param flashBeans 快讯列表
         */
        void refreshFlashList(List<FlashBean> flashBeans);
    }

    interface IPresenter {

        /**
         * 得到快讯信息
         */
        void initFlashInfo();

        /**
         * 增加阅读量
         *
         * @param _flashBean
         */
        public void addReadVolume(FlashBean _flashBean);
    }

}
