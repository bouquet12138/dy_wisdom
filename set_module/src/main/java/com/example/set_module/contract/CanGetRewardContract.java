package com.example.set_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.SetRecordBean;

import java.util.List;

public interface CanGetRewardContract {

    interface IView extends IAppMvpView {

        /**
         * 刷新套餐列表
         *
         * @param setRecordBeans 套餐列表
         */
        void refreshSetRecordList(List<SetRecordBean> setRecordBeans);

        /**
         * 底部没有数据
         */
        void setFootNoMoreData();

        /**
         * 完成加载更多
         */
        void completeLoadMore();

    }

    interface IPresenter {

        /**
         * 初始化可领奖励
         */
        void initCanGetReward();


        /**
         * 加载更多可领奖励
         */
        void loadMoreCanGetReward();

    }

}
