package com.example.wisdomconsumption.contract;

import com.example.base_lib.base.IMVPBaseView;

public interface OrderContract {

    interface IView extends IMVPBaseView {

        void setWaitDelivery(int num);

        void setHadDelivery(int num);
    }

    interface IPresenter {
        /**
         * 得到各种商品数目
         */
        void getDeliverGoodNum();
    }

}
