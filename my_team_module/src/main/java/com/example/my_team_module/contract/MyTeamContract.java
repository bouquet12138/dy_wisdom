package com.example.my_team_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.UserBean;

import java.util.List;

public interface MyTeamContract {

    interface IView extends IAppMvpView {

        /**
         * 设置团队信息
         *
         * @param userBeans 团队成员
         */
        void setTeamInfo(List<UserBean> userBeans);
    }

    interface IPresenter {

        /**
         * 得到我团队的信息
         */
        void getMyTeamInfo(int user_id, boolean place);

    }

}
