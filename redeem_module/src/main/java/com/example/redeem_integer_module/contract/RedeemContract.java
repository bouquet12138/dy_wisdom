package com.example.redeem_integer_module.contract;

import com.example.common_lib.contract.LoadMoreContract;
import com.example.common_lib.java_bean.RedeemRecordBean;

import java.util.List;

public interface RedeemContract {

    interface IView extends LoadMoreContract.IView {

        /**
         * 刷新销售列表
         *
         * @param saleShareRecordBeans 销售列表
         */
        void refreshRedeemList(List<RedeemRecordBean> saleShareRecordBeans);

        String getType();

    }

    interface IPresenter extends LoadMoreContract.IPresenter {
    }

}
