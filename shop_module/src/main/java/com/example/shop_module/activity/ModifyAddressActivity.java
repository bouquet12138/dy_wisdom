package com.example.shop_module.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_view.editText.MyEditText;
import com.example.shop_module.R;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

public class ModifyAddressActivity extends AppMvpBaseActivity {

    private MyEditText mNameText;
    private MyEditText mTelPhone;
    private ViewGroup mCityLayout;
    private TextView mCityText;
    private MyEditText mDetailAddressText;

    private CityPickerView mPicker = new CityPickerView(); //城市选择器


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        initView();
        initData();
        mPicker.init(this); //预先加载仿iOS滚轮实现的全部数据
        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mCityLayout.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(mCityText);//隐藏键盘
            mPicker.showCityPicker();//展示城市
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        mNameText = findViewById(R.id.nameText);
        mTelPhone = findViewById(R.id.telPhone);
        mCityLayout = findViewById(R.id.cityLayout);
        mCityText = findViewById(R.id.cityText);
        mDetailAddressText = findViewById(R.id.detailAddressText);
    }

    /**
     * 初始化数据
     */
    private void initData() {

        /**
         *  intent.putExtra("name", mNameText.getText());
         *         intent.putExtra("phone", mTelPhone.getText());
         *         intent.putExtra("address", mCityText.getText());
         *         intent.putExtra("detail_address", mDetailAddressText.getText());
         */

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String address = intent.getStringExtra("address");
        String detail_address = intent.getStringExtra("detail_address");

        mNameText.setText(name);
        mNameText.setCursorPosition();
        mTelPhone.setText(phone);

        mCityText.setText(address);//设置地区
        mDetailAddressText.setText(detail_address);//详细地址

        initCity();//初始化城市
    }

    private static final String TAG = "ModifyAddressActivity";

    /**
     * 展示城市
     */
    private void initCity() {
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo

        Log.d(TAG, "showCity: " + R.color.app_title_color);

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
                mCityText.setText(province.getName() + city.getName() + district.getName());
            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    protected String getTitleName() {
        return "修改收货地址";
    }

    @Override
    protected String getRightTextName() {
        return "修改";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.shop_layout_address;//收货地址界面
    }

    @Override
    protected void onRefresh() {

    }


    @Override
    protected void onFloatBtClick() {

        Intent intent = new Intent();
        intent.putExtra("name", mNameText.getText());
        intent.putExtra("phone", mTelPhone.getText());
        intent.putExtra("address", mCityText.getText());
        intent.putExtra("detail_address", mDetailAddressText.getText());
        setResult(RESULT_OK, intent);
        finish();

    }
}
