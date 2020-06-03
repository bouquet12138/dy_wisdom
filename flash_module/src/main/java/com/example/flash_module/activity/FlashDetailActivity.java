package com.example.flash_module.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.FlashBean;
import com.example.flash_module.R;
import com.example.flash_module.adapter.FlashContentAdapter;
import com.example.flash_module.contract.FlashDetailContract;
import com.example.flash_module.presenter.FlashDetailPresenter;

@Route(path = ARouterContract.FLASH_DETAIL)
public class FlashDetailActivity extends AppMvpBaseActivity implements FlashDetailContract.IView {

    @Autowired(name = "flashBean")
    public FlashBean mFlashBean;

    private TextView mTitle;
    private TextView mDateText;
    private RecyclerView mRecyclerView;
    private TextView mReadVolumeText;

    private FlashDetailPresenter mPresenter = new FlashDetailPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        initView();
        initData();
        mPresenter.attachView(this);
        mPresenter.addReadingVolume(mFlashBean.getFlash_id());//增加阅读量
        setSubmitEnable(false);//提交不可用
    }

    private void initView() {
        mTitle = findViewById(R.id.title);
        mDateText = findViewById(R.id.dateText);
        mRecyclerView = findViewById(R.id.recyclerView);
        mReadVolumeText = findViewById(R.id.readVolumeText);
    }

    private static final String TAG = "FlashDetailActivity";

    private void initData() {

        mFlashBean = (FlashBean) getIntent().getSerializableExtra("flashBean");

        if (mFlashBean == null)
            ARouter.getInstance().inject(this);//注入一下

        Log.d(TAG, "initData: " + mFlashBean);

        mTitle.setText(mFlashBean.getTitle());
        mDateText.setText(mFlashBean.getInsert_time());//发布时间
        mReadVolumeText.setText("阅读量：" + mFlashBean.getReading_volume());

        FlashContentAdapter prizeContentAdapter =
                new FlashContentAdapter(mFlashBean.getContent_list());
        mRecyclerView.setAdapter(prizeContentAdapter);//设置适配器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
    }

    @Override
    protected String getTitleName() {
        return "咨询详情";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }


    @Override
    protected int getNormalViewId() {
        return R.layout.flash_layout_detail;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {
    }

    /**
     * 启动活动
     *
     * @param context
     * @param flashBean
     */
    public static void actionStart(Context context, FlashBean flashBean) {
        Intent intent = new Intent(context, FlashDetailActivity.class);
        intent.putExtra("flashBean", flashBean);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
