package com.example.sale_integer_module.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.fragment.LoadMoreFragment;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.sale_integer_module.adapter.SaleShareAdapter;

import com.example.sale_integer_module.contract.SaleShareContract;
import com.example.sale_integer_module.presenter.SaleSharePresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SaleShareFragment extends LoadMoreFragment implements SaleShareContract.IView {

    private SaleShareAdapter mAdapter;//适配器

    private String mType;//类型

    private SaleSharePresenter mPresenter = new SaleSharePresenter();

    public SaleShareFragment(String type) {
        mType = type;
    }

    @Override
    protected void initAllMembersView() {
        super.initAllMembersView();
        mPresenter.attachView(this);//绑定一下
        mPresenter.initData();//初始化一下信息
    }

    @Override
    public String getType() {
        return mType;
    }

    @Override
    public void refreshSaleShareList(List<SaleShareRecordBean> saleShareRecordBeans) {
        if (mAdapter == null) {
            mAdapter = new SaleShareAdapter(saleShareRecordBeans);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(10)));
            initRefreshListener();//初始化刷新监听
        } else {
            mAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();//解除绑定
        super.onDestroyView();
    }

    @Override
    public void onLoadMore() {
        mPresenter.loadMoreData();
    }

    @Override
    public void onRefreshBtClick() {
        mPresenter.initData();//初始化一下信息
    }
}
