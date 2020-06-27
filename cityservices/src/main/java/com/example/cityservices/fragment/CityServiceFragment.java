package com.example.cityservices.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.base.BaseFragment;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.cityservices.R;
import com.example.cityservices.activity.CityServicesActivity;
import com.example.cityservices.activity.ServiceDetailsActivity;
import com.example.cityservices.adapter.StoreAdapter;
import com.example.cityservices.contract.CityServicesContract;
import com.example.cityservices.presenter.CityServicesPresenter;
import com.example.common_lib.java_bean.StoreBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityServiceFragment extends BaseFragment implements CityServicesContract.IView {

    private String mProvince;
    private String mCity;
    private String mDistrict;
    private String mStoreName;//商店名

    public CityServiceFragment(String province, String city, String district, String storeName) {
        mProvince = province;
        mCity = city;
        mDistrict = district;
        mStoreName = storeName;
    }

    private static final String TAG = "CityServiceFragment";

    private CityServicesPresenter mPresenter = new CityServicesPresenter();

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private ViewGroup mNetErrorLayout;
    private Button mRefresh;

    private boolean mBottomNoMoreData;//没有更多数据了
    private StoreAdapter mStoreAdapter;//商店适配器


    @Override
    protected int getContentViewId() {
        return R.layout.city_fragment_service;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initView();
        initListener();
        mPresenter.attachView(this);
        mPresenter.initStoreInfo();//初始化一下
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRefreshLayout = mView.findViewById(R.id.refreshLayout);
        mRecyclerView = mView.findViewById(R.id.recyclerView);

        mNetErrorLayout = mView.findViewById(R.id.netErrorLayout);
        mRefresh = mView.findViewById(R.id.refresh);

        mNetErrorLayout.setVisibility(View.GONE);//不可见
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            mPresenter.refreshStoreInfo();//刷新商店信息
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMoreStoreInfo();//加载更多信息
        });
        mRefresh.setOnClickListener(view -> {
            mNetErrorLayout.setVisibility(View.GONE);//不可见
            mPresenter.initStoreInfo();//初始化一下
        });
    }

    @Override
    public String getProvince() {
        return TextUtils.isEmpty(mProvince) ? "" : mProvince;
    }

    @Override
    public String getCity() {
        return TextUtils.isEmpty(mCity) ? "" : mCity;
    }

    @Override
    public String getDistrict() {
        return TextUtils.isEmpty(mDistrict) ? "" : mDistrict;
    }

    @Override
    public String getStoreName() {
        return mStoreName;
    }

    /**
     * 刷新商店列表
     */
    @Override
    public void refreshStoreList(List<StoreBean> storeList) {

        if (mStoreAdapter == null) {
            mStoreAdapter = new StoreAdapter(storeList);
            mRecyclerView.setAdapter(mStoreAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(5)));
            mStoreAdapter.setOnItemClickListener(storeBean -> {
                Log.d(TAG, "refreshStoreList: 点击 ");
                ServiceDetailsActivity.actionStart(getContext(), storeBean);//启动一下
            });
        } else {
            mStoreAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    /**
     * 没有更多数据
     */
    @Override
    public void setFootNoMoreData() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
        mBottomNoMoreData = true;//底部没有更多数据
    }

    @Override
    public void setFootHasData() {
        mBottomNoMoreData = false;//底部有数据
        mRefreshLayout.setNoMoreData(false);//还有数据
    }

    @Override
    public void smoothScrollToPosition(int position) {
        mRecyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void showNormalView() {
        mRefreshLayout.setVisibility(View.VISIBLE);
        mNetErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNetError() {
        mRefreshLayout.setVisibility(View.GONE);
        mNetErrorLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public void completeRefresh() {
        if (mBottomNoMoreData)
            setFootNoMoreData();//没有更多数据
        mRefreshLayout.finishRefresh();//结束刷新
    }

    @Override
    public void completeLoadMore() {
        mRefreshLayout.finishLoadMore(500);//完成加载更多
    }

    /**
     * 销毁时
     */
    @Override
    public void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

}
