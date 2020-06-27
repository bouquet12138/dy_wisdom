package com.example.set_module.contract;


import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.SetBean;

import java.util.List;

public interface SetShopContract {

    interface IView extends IAppMvpView {

        /**
         * 刷新套餐列表
         *
         * @param setBeans 套餐列表
         */
        void refreshSetList(List<SetBean> setBeans);

        /**
         * 得到支付密码
         * @return
         */
        String getPayPass();

    }

    interface IPresenter {
        /**
         * 购买套餐
         *
         * @param set_id
         */
        void buy_set(int set_id);

        /**
         * 得到套餐列表
         */
        void getSetList();
    }

}
