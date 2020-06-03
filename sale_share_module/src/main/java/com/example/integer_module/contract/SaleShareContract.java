package com.example.integer_module.contract;

import com.example.base_lib.base.IMVPBaseView;
import com.example.common_lib.java_bean.SaleShareRecordBean;

import java.util.List;

public interface SaleShareContract {

    interface IView extends IMVPBaseView {

        /**
         * 刷新销售列表
         *
         * @param saleShareRecordBeans 销售列表
         */
        void refreshSaleShareList(List<SaleShareRecordBean> saleShareRecordBeans);

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
    }

    interface IPresenter {
        /**
         * 初始化数据
         *
         * @param integral_type
         */
        void initData(String integral_type);

        /**
         * 加载更多数据
         */
        void loadMoreData(String integral_type);
    }

}
