package com.example.shop_module.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.SizeUtils;
import com.example.common_lib.fragment.LoadMoreFragment;
import com.example.common_lib.java_bean.OrderRecordBean;
import com.example.shop_module.adapter.OrderAdapter;
import com.example.shop_module.contract.OrderContract;
import com.example.shop_module.presenter.OrderPresenter;
import com.example.shop_module.utils.GridItemDecoration;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends LoadMoreFragment implements OrderContract.IView {

    private String mType;
    private OrderAdapter mOrderAdapter;
    private OrderPresenter mOrderPresenter = new OrderPresenter();

    public OrderFragment(String type) {
        mType = type;
    }

    @Override
    public String getOrderType() {
        return mType;
    }

    @Override
    protected void initAllMembersView() {
        super.initAllMembersView();
        mOrderPresenter.attachView(this);
        mOrderPresenter.initData();
    }

    @Override
    public void refreshOrderList(List<OrderRecordBean> orderRecordBeans) {
        showNormalView();//展示正常view
        if (mOrderAdapter == null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            mOrderAdapter = new OrderAdapter(orderRecordBeans);
            mRecyclerView.setAdapter(mOrderAdapter);
            GridItemDecoration gridItemDecoration = new GridItemDecoration(SizeUtils.dp2px(5));
            mRecyclerView.addItemDecoration(gridItemDecoration);//设置分割
            mRecyclerView.setLayoutManager(layoutManager);//瀑布布局

            initRefreshListener();
        } else {
            mOrderAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }


    @Override
    public void onLoadMore() {
        mOrderPresenter.loadMoreData();//加载数据
    }

    @Override
    public void onRefreshBtClick() {
        mOrderPresenter.initData();//初始化数据
    }

    @Override
    public void onDestroyView() {
        mOrderPresenter.detachView();//解除绑定
        super.onDestroyView();
    }
}
