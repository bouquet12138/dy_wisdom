package com.example.bonus_integer_module.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.bonus_integer_module.adapter.BonusAdapter;
import com.example.common_lib.fragment.LoadMoreFragment;
import com.example.common_lib.java_bean.BonusRecordBean;
import com.example.bonus_integer_module.contract.BonusContract;
import com.example.bonus_integer_module.presenter.BonusPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BonusFragment extends LoadMoreFragment implements BonusContract.IView {

    private BonusAdapter mAdapter;//适配器

    private String mType;//类型

    private BonusPresenter mPresenter = new BonusPresenter();

    public BonusFragment(String type) {
        mType = type;
    }


    @Override
    protected void initAllMembersView() {
        super.initAllMembersView();//初始化
        mPresenter.attachView(this);//绑定一下
        mPresenter.initData();//初始化一下信息
    }


    @Override
    public void refreshBonusList(List<BonusRecordBean> bonusRecordBeans) {
        if (mAdapter == null) {
            mAdapter = new BonusAdapter(bonusRecordBeans);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(10)));
            initRefreshListener();//初始化刷新监听
        } else {
            mAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    @Override
    public String getIntegralType() {
        return mType;
    }


    @Override
    public void onLoadMore() {
        mPresenter.loadMoreData();//加载更多
    }

    @Override
    public void onRefreshBtClick() {
        mPresenter.initData();//初始化
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();//解除绑定
        super.onDestroyView();
    }
}
