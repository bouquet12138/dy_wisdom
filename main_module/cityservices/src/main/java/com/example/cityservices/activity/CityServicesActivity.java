package com.example.cityservices.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.example.baselib.base.MVPBaseActivity;
import com.example.baselib.util.DensityUtil;
import com.example.baselib.view.SpacesItemDecoration;
import com.example.cityservices.R;
import com.example.cityservices.adapter.StoreAdapter;
import com.example.cityservices.contract.CityServicesContract;
import com.example.cityservices.presenter.CityServicesPresenter;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.StoreBean;
import com.example.common_view.editText.MyEditText;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

@Route(path = ARouterContract.CITY_SERVICES)
public class CityServicesActivity extends MVPBaseActivity implements View.OnClickListener, CityServicesContract.IView {

    private static final String TAG = "CityServicesActivity";

    private String mProvince;
    private String mCity;
    private String mDistrict;

    private TextView mCityText;
    private MyEditText mStoreNameText;

    private QMUIRoundButton mSearchBt;

    private ImageView mBackButton;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;

    private ViewGroup mNetErrorLayout;
    private Button mRefresh;

    private boolean mBottomNoMoreData;//没有更多数据了
    private StoreAdapter mStoreAdapter;//商店适配器

    private CityServicesPresenter mPresenter = new CityServicesPresenter();


    //城市选择器
    private CityPickerView mPicker = new CityPickerView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_activity_services);
        initView();
        initCity();
        initListener();
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
        mPresenter.attachView(this);
        mPresenter.initStoreInfo();//初始化一下
    }

    /**
     * 初始化城市
     */
    private void initCity() {

        CityConfig cityConfig = new CityConfig.Builder()
                .setShowGAT(true)//显示港澳台数据
                .confirTextColor("#4286E7")
                .provinceCyclic(false)//省份滚轮是否可以循环滚动
                .cityCyclic(false)//城市滚轮是否可以循环滚动
                .districtCyclic(false)//区县滚轮是否循环滚动
                .setLineColor("#fafafa")//中间横线的颜色
                .setLineHeigh(1)//中间横线的高度
                .setCustomItemLayout(R.layout.common_item_city)
                .setCustomItemTextViewId(R.id.item_city_name_tv)
                .build();
        mPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                mProvince = province.getName();
                mCity = city.getName();
                mDistrict = district.getName();
                mCityText.setText(province.getName() + "-" + city.getName() + "-" + district.getName());
                mPresenter.initStoreInfo();//初始化一下
            }

            @Override
            public void onCancel() {
            }
        });

    }

    /**
     * 初始化view
     */
    private void initView() {
        mCityText = findViewById(R.id.cityText);
        mStoreNameText = findViewById(R.id.storeName);//商店名字
        mSearchBt = findViewById(R.id.searchBt);

        mBackButton = findViewById(R.id.backButton);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        mRecyclerView = findViewById(R.id.recyclerView);

        mNetErrorLayout = findViewById(R.id.netErrorLayout);
        mRefresh = findViewById(R.id.refresh);

        mNetErrorLayout.setVisibility(View.GONE);//不可见

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mCityText.setOnClickListener(this);//点击监听
        mSearchBt.setOnClickListener(this);

        mBackButton.setOnClickListener(this);
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

        mStoreNameText.setOnTextChangedListener(() -> {//文字改变时
            mPresenter.initStoreInfo();//初始化一下
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cityText) {//展示城市
            showCity();
        } else if (id == R.id.searchBt) {
            mPresenter.initStoreInfo();//初始化一下
        } else if (id == R.id.backButton) {
            finish();
        }
    }


    /**
     * 展示城市
     */
    private void showCity() {

        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
        Log.d(TAG, "showCity: " + R.color.app_title_color);
        KeyboardUtils.hideSoftInput(mCityText);//隐藏键盘
        //显示
        mPicker.showCityPicker();

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
        return TextUtils.isEmpty(mStoreNameText.getText()) ? "" : mStoreNameText.getText();
    }

    /**
     * 刷新商店列表
     */
    @Override
    public void refreshStoreList(List<StoreBean> storeList) {

        if (mStoreAdapter == null) {
            mStoreAdapter = new StoreAdapter(storeList);
            mRecyclerView.setAdapter(mStoreAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtil.dipToPx(5)));
            mStoreAdapter.setOnItemClickListener(storeBean -> {
                Log.d(TAG, "refreshStoreList: 点击 ");
                ServiceDetailsActivity.actionStart(CityServicesActivity.this, storeBean);//启动一下
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
        mRecyclerView.setVisibility(View.VISIBLE);//可见
        if (!mBottomNoMoreData)
            mRefreshLayout.setEnableLoadMore(true);
        mNetErrorLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNetError() {
        mRecyclerView.setVisibility(View.GONE);//可见
        if (!mBottomNoMoreData)
            mRefreshLayout.setEnableLoadMore(false);
        mNetErrorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorHint(String msg) {
        QMUITipDialog errorDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(msg)
                .create();
        errorDialog.show();//展示一下
        mBackButton.postDelayed(() -> errorDialog.dismiss(), 500);
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
     * 销毁
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

}
