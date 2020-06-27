package com.example.sale_integer_module.contract;

import com.example.base_lib.base.IMVPBaseView;
import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.UserBean;

public interface TransfersContract {

    interface IView extends IMVPBaseView {

        String getTransactionType();

        /**
         * 积分转让成功
         */
        void transferSuccess();

        /**
         * 设置用户信息
         *
         * @param userBean
         */
        void setUserInfo(UserBean userBean);

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
         * 得到目标用户的id
         *
         * @return
         */
        String getTargetUserPhone();
    }

    interface IPresenter {

        /**
         * 转账
         */
        void payrollTransfers();

        /**
         * 得到用户信息
         *
         * @param phone_num
         */
        void getUserInfo(String phone_num);

    }

}
