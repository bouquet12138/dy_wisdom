package com.example.sale_integer_module.contract;

import com.example.base_lib.base.IMVPBaseView;

public interface ConversionContract {

    interface IView extends IMVPBaseView {

        /**
         * 得到转换类型
         *
         * @return
         */
        String getConversionType();

        /**
         * 积分转换成功
         */
        void conversionSuccess();

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

    }

    interface IPresenter {

        /**
         * 转换
         */
        void conversion();
    }

}
