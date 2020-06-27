package com.example.shop_module.contract;

import com.example.common_lib.contract.LoadMoreContract;
import com.example.common_lib.java_bean.OrderRecordBean;

import java.util.List;

public interface OrderContract {

    interface IView extends LoadMoreContract.IView {

        String getOrderType();//得到订单类型

        void refreshOrderList(List<OrderRecordBean> orderRecordBeans);

    }

    interface IPresenter extends LoadMoreContract.IPresenter {
    }

}
