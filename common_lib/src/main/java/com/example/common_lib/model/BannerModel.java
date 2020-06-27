package com.example.common_lib.model;



import com.example.base_lib.listener.OnGetInfoListener;
import com.example.common_lib.java_bean.BannerBean;
import com.example.common_lib.java_bean.BaseBean;

import java.util.List;

public class BannerModel {

    /**
     * 初始化一下轮播图信息
     */
    public void initBanner(OnGetInfoListener<BaseBean<List<BannerBean>>> listener) {

        ModelListUtil<BannerBean> modelListUtil = new ModelListUtil<>();
        modelListUtil.getList("init_banner", "", listener, BannerBean.class);
    }

}
