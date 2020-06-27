package com.example.redeem_integer_module.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.fragment.LoadMoreFragment;
import com.example.common_lib.java_bean.RedeemRecordBean;
import com.example.redeem_integer_module.adapter.RedeemAdapter;
import com.example.redeem_integer_module.contract.RedeemContract;
import com.example.redeem_integer_module.presenter.RedeemPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RedeemFragment extends LoadMoreFragment implements RedeemContract.IView {

    private RedeemAdapter mAdapter;//适配器

    private String mType;//类型

    private RedeemPresenter mPresenter = new RedeemPresenter();

    public RedeemFragment(String type) {
        mType = type;
    }


    @Override
    protected void initAllMembersView() {
        super.initAllMembersView();
        mPresenter.attachView(this);//绑定一下
        mPresenter.initData();//初始化一下信息
    }


    @Override
    public void refreshRedeemList(List<RedeemRecordBean> redeemRecordBeans) {
        if (mAdapter == null) {
            mAdapter = new RedeemAdapter(redeemRecordBeans);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(10)));
            initRefreshListener();//初始化刷新监听
        } else {
            mAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    @Override
    public String getType() {
        return mType;
    }


    @Override
    public void onLoadMore() {
        mPresenter.loadMoreData();
    }

    @Override
    public void onRefreshBtClick() {
        mPresenter.initData();
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }
}
