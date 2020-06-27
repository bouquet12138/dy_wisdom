package com.example.cityservices.activity;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.example.cityservices.R;
import com.example.cityservices.adapter.GoodAdapter;
import com.example.cityservices.utils.GridItemDecoration;
import com.example.common_lib.activity.ImageWatchActivity;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.StoreBean;
import com.example.common_lib.java_bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务详情
 */
public class ServiceDetailsActivity extends AppMvpBaseActivity {

    private TextView mStoreName;
    private ImageView mHeadImg;
    private ImageView mLocationImg;
    private TextView mDetailedAddress;

    private ViewGroup mPhoneGroup;//电话
    private TextView mPhoneText;

    private TextView mBusinessTime;
    private TextView mStoreDescribe;
    private RecyclerView mRecyclerView;
    private StoreBean mBean;

    private Button mConfirmBt;//确认按钮
    private List<String> mHeadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//隐藏提交
        //lightBack();//淡色的
        intView();
        initData();
        initListener();
    }

    private void initData() {
        Intent intent = getIntent();
        mBean = (StoreBean) intent.getSerializableExtra("storeBean");
        if (mBean == null) {
            finish();
            return;
        }
        setTitle(mBean.getStore_name(), mBean.getStore_type(), mStoreName);

        if (mBean.getHead_img() == null) {
            mHeadImg.setImageResource(R.drawable.image_loading);
        } else {
            mHeadUrl = new ArrayList<>();
            mHeadUrl.add(mBean.getHead_img().getImage_url());
            Glide.with(this).
                    load(ServerInfo.getImageAddress(mBean.getHead_img().getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(mHeadImg);
        }
        mDetailedAddress.setText(mBean.getDetailed_address());//详细地址
        mBusinessTime.setText("营业时间:" + mBean.getBusiness_hours());//营业时间
        mStoreDescribe.setText(mBean.getStore_describe());//详细
        mPhoneText.setText(mBean.getContact_phone());

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);

        GoodAdapter goodAdapter = new GoodAdapter(mBean.getProduct_imgs());//得到图片
        mRecyclerView.setAdapter(goodAdapter);


        GridItemDecoration gridItemDecoration = new GridItemDecoration(SizeUtils.dp2px(10));
        mRecyclerView.addItemDecoration(gridItemDecoration);//设置分割
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);//瀑布布局
    }

    private void setTitle(String up, String down, TextView textView) {

        String str = up + "\n\n" + down;

        SpannableString spannableString = new SpannableString(str);

        spannableString.setSpan(new AbsoluteSizeSpan(SizeUtils.dp2px(15)),
                up.length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_text_color)),
                up.length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(spannableString);

    }

    /**
     * 初始化view
     */
    private void intView() {
        mStoreName = findViewById(R.id.storeName);
        mHeadImg = findViewById(R.id.headImg);
        mLocationImg = findViewById(R.id.locationImg);
        mDetailedAddress = findViewById(R.id.detailedAddress);
        mBusinessTime = findViewById(R.id.businessTime);
        mStoreDescribe = findViewById(R.id.storeDescribe);
        mRecyclerView = findViewById(R.id.recyclerView);
        mPhoneText = findViewById(R.id.phoneText);
        mPhoneGroup = findViewById(R.id.phoneView);//电话

        mConfirmBt = findViewById(R.id.confirmBt);
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mPhoneGroup.setOnClickListener(view -> callPhone());
        mConfirmBt.setOnClickListener(view -> {
            UserBean userBean = NowUserInfo.getNowUserInfo();//得到当前用户
            if (userBean == null) {
                showErrorHint("请先登录");
            } else
                ARouter.getInstance().build(ARouterContract.REDEEM_PAY).withInt("target_user_id", mBean.getUser_id()).withString("remark_str", mBean.getStore_name() + "消费") //跳转到用户信息页面
                        .navigation();
        });

        mHeadImg.setOnClickListener(v -> {
            if (!CollectionUtils.isEmpty(mHeadUrl))
                ImageWatchActivity.actionStart(ServiceDetailsActivity.this, mHeadUrl, 0);
        });

    }

    private static final String TAG = "ServiceDetailsActivity";

    /**
     * 拨打电话
     */
    private void callPhone() {

        if (TextUtils.isEmpty(mBean.getContact_phone())) {
            showErrorHint("商家未留电话");
            return;
        }
        Log.d(TAG, "callPhone: " + mBean.getContact_phone());

        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + mBean.getContact_phone()));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            showErrorHint("该手机没有拨打电话的功能");
        }
    }

    @Override
    protected String getTitleName() {
        return "服务详情";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }


    @Override
    protected int getNormalViewId() {
        return R.layout.city_layout_service_details;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {

    }

    public static void actionStart(Context context, StoreBean storeBean) {
        Intent intent = new Intent(context, ServiceDetailsActivity.class);
        intent.putExtra("storeBean", storeBean);
        context.startActivity(intent);
    }

}
