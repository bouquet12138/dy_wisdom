package com.example.spread_integer_module.contract;

import com.example.common_lib.contract.LoadMoreContract;
import com.example.common_lib.java_bean.SpreadRecordBean;

import java.util.List;

public interface SpreadContract {

    interface IView extends LoadMoreContract.IView {

        /**
         * 刷新销售列表
         *
         * @param spreadRecordBeans 销售列表
         */
        void refreshSpreadList(List<SpreadRecordBean> spreadRecordBeans);

        /**
         * 得到类型
         *
         * @return
         */
        String getType();

    }

    interface IPresenter extends LoadMoreContract.IPresenter {
    }

}
