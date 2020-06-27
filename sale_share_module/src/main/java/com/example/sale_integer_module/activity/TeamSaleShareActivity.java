package com.example.sale_integer_module.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.sale_integer_module.R;
import com.example.sale_integer_module.adapter.SaleTeamShareAdapter;
import com.example.sale_integer_module.contract.TeamSaleShareContract;
import com.example.sale_integer_module.presenter.TeamSaleSharePresenter;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.util.List;

@Route(path = ARouterContract.SALE_SHARE_TEAM)
public class TeamSaleShareActivity extends AppMvpBaseActivity implements TeamSaleShareContract.IView {

    private boolean mBottomNoMoreData;

    @Autowired(name = "role")
    protected String mRole;

    @Autowired(name = "user_id")
    protected int mUserId;

    private SaleTeamShareAdapter mAdapter;//适配器
    private RecyclerView mRecyclerView;//recyclerView
    private TeamSaleSharePresenter mPresenter = new TeamSaleSharePresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        showNormalView();//展示正常view
        initData();
        mPresenter.attachView(this);
        mPresenter.initData(mUserId, mRole);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);

        mSmartRefreshLayout.setPrimaryColors(getResources().getColor(R.color.app_theme_color));
        mSmartRefreshLayout.setEnableLoadMore(true);//加载更多可用
        mSmartRefreshLayout.setEnableHeaderTranslationContent(true);
        mSmartRefreshLayout.setEnablePureScrollMode(false);//不是只滑动

        TaurusHeader header = new TaurusHeader(this);
        mSmartRefreshLayout.setRefreshHeader(header);//设置头布局

        ClassicsFooter footer = new ClassicsFooter(this);//底部
        // footer.setBackgroundColor(Color.WHITE);//白色
        mSmartRefreshLayout.setRefreshFooter(footer);//底部布局
    }

    /**
     * 初始化一下数据
     */
    private void initData() {
        ARouter.getInstance().inject(this);//绑定一下数据
    }


    @Override
    protected String getTitleName() {
        return "团队收益明细";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.sale_share_layout_team;
    }

    @Override
    protected void onRefresh() {
        mPresenter.initData(mUserId, mRole);
    }

    @Override
    protected void onFloatBtClick() {

    }


    @Override
    public void refreshSaleShareList(List<SaleShareRecordBean> saleShareRecordBeans) {
        if (mAdapter == null) {
            mAdapter = new SaleTeamShareAdapter(saleShareRecordBeans);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(10)));
            initRefreshListener();//初始化刷新监听
        } else {
            mAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    /**
     * 刷新监听 和 加载更多的监听
     */
    private void initRefreshListener() {

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            showNormalView();//展示正常view
            mPresenter.loadMoreData(mUserId, mRole);//加载更多积分信息
        });
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

}
