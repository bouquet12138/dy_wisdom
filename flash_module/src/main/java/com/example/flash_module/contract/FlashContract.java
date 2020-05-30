package com.example.flash_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.FlashBean;

import java.util.List;

public interface FlashContract {

    /**
     *
     */
    interface IView extends IAppMvpView {
        
        
        
        /**
         * 刷新快讯列表
         *
         * @param flashBeans 快讯列表
         */
        void refreshFlashList(List<FlashBean> flashBeans);

        void setFootNoMoreData();

        void completeLoadMore();//完成加载更多
    }

    interface IPresenter {

        /**
         * 得到快讯信息
         */
        void initFlashInfo();

        /**
         * 刷新快讯信息
         */
        void refreshFlashInfo();

        /**
         * 加载更多快讯信息
         */
        void loadMoreFlashInfo();

        void addReadVolume(FlashBean flashBean);

    }

}
