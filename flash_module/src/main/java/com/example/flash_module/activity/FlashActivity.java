package com.example.flash_module.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.FlashBean;
import com.example.flash_module.R;
import com.example.flash_module.adapter.FlashAdapter;
import com.example.flash_module.contract.FlashContract;
import com.example.flash_module.presenter.FlashPresenter;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.util.List;

@Route(path = ARouterContract.FLASH_FLASH)
public class FlashActivity extends AppMvpBaseActivity implements FlashContract.IView {

    private FlashAdapter mAdapter;//适配器
    private RecyclerView mRecyclerView;
    private FlashPresenter mPresenter = new FlashPresenter();
    private boolean mBottomNoMoreData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setSubmitEnable(false);//
        mPresenter.attachView(this);//绑定一下
        mPresenter.initFlashInfo();//初始化数据
    }

    /**
     * 刷新监听 和 加载更多的监听
     */
    private void initRefreshListener() {

        mSmartRefreshLayout.setPrimaryColors(getResources().getColor(R.color.app_theme_color));
        mSmartRefreshLayout.setEnableLoadMore(true);//加载更多可用
        mSmartRefreshLayout.setEnableHeaderTranslationContent(true);
        mSmartRefreshLayout.setEnablePureScrollMode(false);//不是只滑动

        TaurusHeader header = new TaurusHeader(this);
        mSmartRefreshLayout.setRefreshHeader(header);//设置头布局

        ClassicsFooter footer = new ClassicsFooter(this);//底部
        // footer.setBackgroundColor(Color.WHITE);//白色
        mSmartRefreshLayout.setRefreshFooter(footer);//底部布局

        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            showNormalView();//展示正常view
            mPresenter.refreshFlashInfo();//刷新快讯信息
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            showNormalView();//展示正常view
            mPresenter.loadMoreFlashInfo();//加载更多快讯信息
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);//recyclerView
    }

    @Override
    protected String getTitleName() {
        return "公司快讯";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }


    @Override
    protected int getNormalViewId() {
        return R.layout.flash_layout_flash;
    }

    @Override
    protected void onFloatBtClick() {

    }

    /**
     * 刷新的时候
     */
    @Override
    protected void onRefresh() {
        showNormalView();//展示正常view
        mPresenter.initFlashInfo();//初始化一下
    }

    private static final String TAG = "FlashActivity";

    @Override
    public void refreshFlashList(List<FlashBean> flashBeans) {
        if (mAdapter == null) {
            mAdapter = new FlashAdapter(flashBeans);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(5)));
            mAdapter.setOnItemClickListener(flashBean -> {
                Log.d(TAG, "refreshStoreList: 点击 ");
                // ServiceDetailsActivity.actionStart(CityServicesActivity.this, storeBean);//启动一下
                mPresenter.addReadVolume(flashBean);//增加阅读量
                FlashDetailActivity.actionStart(getContext(), flashBean);
            });
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
    public void completeRefresh() {
        if (mBottomNoMoreData)
            setFootNoMoreData();//没有更多数据
        super.completeRefresh();
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
