package com.example.shop_module.contract;

import com.example.common_lib.base.IAppMvpView;

public interface GoodDetailContract {

    interface IView extends IAppMvpView {

        String getName();

        String getPhone();

        String getAddress();

        String getDetailAddress();

        /**
         * 得到密码
         *
         * @return
         */
        String getPass();

        /**
         * 得到商品id
         *
         * @return
         */
        int getGoodId();

        /**
         * 得到数量
         *
         * @return
         */
        int getNum();

    }

    interface IPresenter {

        /**
         * 买商品
         */
        void buyGood();
    }

}
