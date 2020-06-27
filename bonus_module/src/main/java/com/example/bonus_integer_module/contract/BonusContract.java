package com.example.bonus_integer_module.contract;

import com.example.common_lib.contract.LoadMoreContract;
import com.example.common_lib.java_bean.BonusRecordBean;

import java.util.List;

public interface BonusContract {

    interface IView extends LoadMoreContract.IView {

        /**
         * 刷新销售列表
         *
         * @param saleShareRecordBeans 销售列表
         */
        void refreshBonusList(List<BonusRecordBean> saleShareRecordBeans);

        String getIntegralType();
    }

    interface IPresenter extends LoadMoreContract.IPresenter {
    }

}
