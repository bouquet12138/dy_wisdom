package com.example.withdraw_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.WithdrawBean;


public interface WithdrawContract {

    interface IView extends IAppMvpView {

        /**
         * 提现成功
         */
        void withdrawSuccess();

    }

    interface IPresenter {

        /**
         * 提现
         */
        void withdrawInfo(WithdrawBean withdrawBean);

    }

}
