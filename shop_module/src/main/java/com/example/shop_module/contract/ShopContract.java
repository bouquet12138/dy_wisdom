package com.example.shop_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.GoodBean;

import java.util.List;

public interface ShopContract {

    interface IView extends IAppMvpView {

        String getShopType();//得到商店类型

        /**
         * 刷新商品列表
         *
         * @param goodBeans 商品列表
         */
        void refreshGoodList(List<GoodBean> goodBeans);

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
         * 初始化数据
         *
         * @param good_type
         */
        void initData(String good_type);

        /**
         * 加载更多数据
         */
        void loadMoreData(String good_type);
    }


}
