package com.example.wisdomconsumption.contract;

import com.example.base_lib.base.IMVPBaseView;
import com.example.common_lib.java_bean.BannerBean;

import java.util.List;

public interface HomeContract {

    interface IView extends IMVPBaseView {

        /**
         * 设置轮播图信息
         */
        void setBannerInfo(List<BannerBean> bannerBeans);
    }


    interface IPresenter {

        /**
         * 得到轮播图信息
         */
        void getBannerInfo();

    }

}
