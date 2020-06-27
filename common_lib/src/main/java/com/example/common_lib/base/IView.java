package com.example.common_lib.base;

import com.example.base_lib.base.IMVPBaseView;

import java.util.List;

interface IView<T> extends IMVPBaseView {

    /**
     * 刷新列表
     *
     * @param list 列表
     */
    void refreshList(List<T> list);

    /**
     * 底部没有数据
     */
    void setFootNoMoreData();

    /**
     * 完成加载更多
     */
    void completeLoadMore();

    /**
     * 展示网络错误
     */
    void showNetError();

    void showNormalView();//展示正常view
}
