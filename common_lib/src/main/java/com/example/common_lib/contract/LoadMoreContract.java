package com.example.common_lib.contract;

import com.example.base_lib.base.IMVPBaseView;


public interface LoadMoreContract {

    interface IView extends IMVPBaseView {


        void onLoadMore();//加载更多调这个

        /**
         * 底部没有数据
         */
        void setFootNoMoreData();

        /**
         * 完成加载更多
         */
        void completeLoadMore();

        /**
         * 展示网络错误
         */
        void showNetError();

        void showNormalView();//展示正常view

        void onRefreshBtClick();//刷新按钮监听
    }

    interface IPresenter {
        /**
         * 初始化数据
         *
         */
        void initData();

        /**
         * 加载更多数据
         */
        void loadMoreData();
    }


}
