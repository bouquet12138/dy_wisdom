package com.example.shop_module.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SizeUtils;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.GoodBean;
import com.example.shop_module.R;
import com.example.shop_module.adapter.GoodAdapter;
import com.example.shop_module.contract.ShopContract;
import com.example.shop_module.presenter.ShopPresenter;
import com.example.shop_module.utils.GridItemDecoration;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.header.StoreHouseHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.SHOP_SHOP)
public class ShopActivity extends AppMvpBaseActivity implements ShopContract.IView {

    @Autowired(name = "shopType")
    protected String mShopType;//商店类型

    private RecyclerView mRecyclerView;
    private GoodAdapter mGoodAdapter;

    private ShopPresenter mPresenter = new ShopPresenter();
    private boolean mBottomNoMoreData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        mPresenter.attachView(this);
        mPresenter.initData(mShopType);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        ARouter.getInstance().inject(this);//绑定一下
        mTitleText.setText(mShopType);//设置标题
    }


    @Override
    protected String getTitleName() {
        return "在线商城";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.shop_layout_shop;
    }

    @Override
    protected void onRefresh() {
        mPresenter.initData(mShopType);
    }

    @Override
    protected void onFloatBtClick() {

    }

    @Override
    public String getShopType() {
        return mShopType;
    }

    @Override
    public void refreshGoodList(List<GoodBean> goodBeans) {
        showNormalView();//展示正常view
        if (mGoodAdapter == null) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                    StaggeredGridLayoutManager.VERTICAL);
            mGoodAdapter = new GoodAdapter(goodBeans);
            mRecyclerView.setAdapter(mGoodAdapter);
            GridItemDecoration gridItemDecoration = new GridItemDecoration(SizeUtils.dp2px(5));
            mRecyclerView.addItemDecoration(gridItemDecoration);//设置分割
            mRecyclerView.setLayoutManager(staggeredGridLayoutManager);//瀑布布局

            mGoodAdapter.setOnItemClickListener(goodBean -> {
                GoodDetailActivity.actionStart(this, goodBean);//启动一下
            });
            initRefreshListener();
        } else {
            mGoodAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    private void initRefreshListener() {
        mSmartRefreshLayout.setPrimaryColors(getResources().getColor(R.color.app_back_color));
        mSmartRefreshLayout.setEnableLoadMore(true);//加载更多可用
        mSmartRefreshLayout.setEnableHeaderTranslationContent(true);
        mSmartRefreshLayout.setEnablePureScrollMode(false);//不是只滑动

        DeliveryHeader header = new DeliveryHeader(this);
        mSmartRefreshLayout.setRefreshHeader(header);//设置头布局

        ClassicsFooter footer = new ClassicsFooter(this);//底部
        footer.setBackgroundColor(Color.WHITE);//白色
        mSmartRefreshLayout.setRefreshFooter(footer);//底部布局

        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMoreData(mShopType);
        });

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            new Handler().postDelayed(() -> {
                mSmartRefreshLayout.finishRefresh();//完成刷新
                if (mBottomNoMoreData)
                    setFootNoMoreData();//没有更多数据
            }, 1000);
        });

    }

    /**
     * 销毁的时候
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
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
