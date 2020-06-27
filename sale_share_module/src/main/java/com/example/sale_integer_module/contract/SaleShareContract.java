package com.example.sale_integer_module.contract;

import com.example.common_lib.contract.LoadMoreContract;
import com.example.common_lib.java_bean.SaleShareRecordBean;

import java.util.List;

public interface SaleShareContract {

    interface IView extends LoadMoreContract.IView {

        String getType();//得到类型

        /**
         * 刷新销售列表
         *
         * @param saleShareRecordBeans 销售列表
         */
        void refreshSaleShareList(List<SaleShareRecordBean> saleShareRecordBeans);

    }

    interface IPresenter extends LoadMoreContract.IPresenter {
    }

}
