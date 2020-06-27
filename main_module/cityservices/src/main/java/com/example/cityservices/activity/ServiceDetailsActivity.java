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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselib.util.DensityUtil;
import com.example.cityservices.R;
import com.example.cityservices.adapter.GoodAdapter;
import com.example.cityservices.utils.GridItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.StoreBean;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        setSubmitEnable(false);//隐藏提交
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
        } else
            Glide.with(this).
                    load(ServerInfo.getImageAddress(mBean.getHead_img().getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(mHeadImg);
        mDetailedAddress.setText(mBean.getDetailed_address());//详细地址
        mBusinessTime.setText("营业时间:" + mBean.getBusiness_hours());//营业时间
        mStoreDescribe.setText(mBean.getStore_describe());//详细
        mPhoneText.setText(mBean.getContact_phone());

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);

        GoodAdapter goodAdapter = new GoodAdapter(mBean.getProduct_imgs());//得到图片
        mRecyclerView.setAdapter(goodAdapter);


        GridItemDecoration gridItemDecoration = new GridItemDecoration(DensityUtil.dipToPx(5));
        mRecyclerView.addItemDecoration(gridItemDecoration);//设置分割
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);//瀑布布局
    }

    private void setTitle(String up, String down, TextView textView) {

        String str = up + "\n\n" + down;

        SpannableString spannableString = new SpannableString(str);

        spannableString.setSpan(new AbsoluteSizeSpan(DensityUtil.dipToPx(15)),
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
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mPhoneGroup.setOnClickListener(view -> callPhone());

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
    protected int getFloatBtImgRes() {
        return 0;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.city_layout_service_details;
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
