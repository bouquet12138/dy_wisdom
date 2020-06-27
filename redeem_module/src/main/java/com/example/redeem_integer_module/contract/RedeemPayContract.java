package com.example.redeem_integer_module.contract;

import com.example.base_lib.base.IMVPBaseView;

public interface RedeemPayContract {

    interface IView extends IMVPBaseView {

        /**
         * 积分转换成功
         */
        void paySuccess();

        /**
         * 得到金额
         *
         * @return
         */
        int getIntegralAmount();

        /**
         * 得到支付密码
         *
         * @return
         */
        String getPayPassword();

        /**
         * 得到备注
         *
         * @return
         */
        String getRemark();

        /**
         * 得到目标用户id
         *
         * @return
         */
        int getTargetUserId();

    }

    interface IPresenter {

        /**
         * 转换
         */
        void redeemPay();
    }

}
