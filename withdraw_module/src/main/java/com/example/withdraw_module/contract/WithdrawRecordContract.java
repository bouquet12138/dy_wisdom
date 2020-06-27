package com.example.withdraw_module.contract;

import com.example.base_lib.base.IMVPBaseView;
import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.common_lib.java_bean.WithdrawBean;

import java.util.List;

public interface WithdrawRecordContract {

    interface IView extends IMVPBaseView {

        /**
         * 刷新销售列表
         *
         * @param withdrawBeans 提现记录列表
         */
        void refreshWithdrawList(List<WithdrawBean> withdrawBeans);

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
         * 初始化提现的信息
         */
        void initWithdrawInfo(int user_id, String withdraw_type);

        /**
         * 加载更多
         *
         * @param user_id
         * @param withdraw_type
         */
        void loadMoreWithdrawInfo(int user_id, String withdraw_type);

    }

}
