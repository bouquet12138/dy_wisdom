package com.example.my_team_module.contract;

import com.example.common_lib.base.IAppMvpView;
import com.example.common_lib.java_bean.TeamInfoBean;

public interface ShowTeamContract {

    interface IView extends IAppMvpView {

        /**
         * 设置团队信息
         *
         * @param teamInfoBean 团队信息
         */
        void setTeamInfo(TeamInfoBean teamInfoBean);
    }

    interface IPresenter {

        /**
         * 得到团队的信息
         */
        void showTeam(int user_id);

    }

}
