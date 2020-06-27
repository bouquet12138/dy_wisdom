package com.example.withdraw_module.fragment;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.base.MVPQMUIFragment;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.withdraw_module.R;
import com.example.withdraw_module.adapter.WithdrawRecordAdapter;
import com.example.withdraw_module.contract.WithdrawRecordContract;
import com.example.withdraw_module.presenter.WithdrawRecordPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawRecordFragment extends MVPQMUIFragment implements WithdrawRecordContract.IView {

    private WithdrawRecordAdapter mAdapter;//适配器

    private SmartRefreshLayout mSmartRefreshLayout;//刷新view
    private RecyclerView mRecyclerView;//recyclerView

    private ViewGroup mNetErrorLayout;//网络错误布局
    private Button mRefresh;//刷新按钮

    private boolean mBottomNoMoreData;
    private String mType;//类型

    private WithdrawRecordPresenter mPresenter = new WithdrawRecordPresenter();
    private UserBean mUserBean;

    public WithdrawRecordFragment(String type) {
        mType = type;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.withdraw_fragment_record;
    }

    @Override
    protected void initAllMembersView() {
        initView();//初始化一下view
        initListener();
        mPresenter.attachView(this);//绑定一下
        mUserBean = NowUserInfo.getNowUserInfo();
        if (mUserBean == null)
            return;

        mPresenter.initWithdrawInfo(mUserBean.getUser_id(), mType);//初始化一下信息
    }

    /**
     * 初始化view
     */
    private void initView() {
        mSmartRefreshLayout = mView.findViewById(R.id.smartRefreshLayout);
        mSmartRefreshLayout.closeHeaderOrFooter();
        // mSmartRefreshLayout.setRefreshHeader(null);//没有刷新
        mRecyclerView = mView.findViewById(R.id.recyclerView);

        mNetErrorLayout = mView.findViewById(R.id.netErrorLayout);
        mRefresh = mView.findViewById(R.id.refresh);


        showNormalView();//展示正常view
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mRefresh.setOnClickListener(v -> {
            showNormalView();//展示正常view
            mPresenter.initWithdrawInfo(mUserBean.getUser_id(), mType);//初始化一下信息
        });
    }

    /**
     * 刷新监听 和 加载更多的监听
     */
    private void initRefreshListener() {

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            showNormalView();//展示正常view
            mPresenter.loadMoreWithdrawInfo(mUserBean.getUser_id(), mType);//加载更多积分信息
        });
    }

    @Override
    public void refreshWithdrawList(List<WithdrawBean> withdrawBeans) {
        if (mAdapter == null) {
            mAdapter = new WithdrawRecordAdapter(withdrawBeans);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(10)));
            initRefreshListener();//初始化刷新监听
        } else {
            mAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    @Override
    public void setFootNoMoreData() {
        mSmartRefreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
        mBottomNoMoreData = true;//底部没有更多数据
    }

    @Override
    public void completeLoadMore() {
        if (mBottomNoMoreData)
            setFootNoMoreData();//没有更多数据
        mSmartRefreshLayout.finishLoadMore(500);//结束加载更多
    }

    @Override
    public void showNetError() {
        mNetErrorLayout.setVisibility(View.VISIBLE);//可见
    }

    @Override
    public void showNormalView() {
        mSmartRefreshLayout.setVisibility(View.VISIBLE);//刷新view可见
        mNetErrorLayout.setVisibility(View.GONE);//网络错误不可见
    }


}

