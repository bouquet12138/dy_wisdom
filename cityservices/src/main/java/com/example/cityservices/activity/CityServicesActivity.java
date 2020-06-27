package com.example.cityservices.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.KeyboardUtils;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.cityservices.R;

import com.example.cityservices.fragment.CityServiceFragment;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_view.editText.MyEditText;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;


@Route(path = ARouterContract.CITY_SERVICES)
public class CityServicesActivity extends MVPBaseActivity implements View.OnClickListener {

    private static final String TAG = "CityServicesActivity";

    private String mProvince;
    private String mCity;
    private String mDistrict;

    private TextView mCityText;
    private MyEditText mStoreNameText;

    private QMUIRoundButton mSearchBt;

    private ImageView mBackButton;


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
        replaceFragment();//替换一下碎片
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
                replaceFragment();//替换一下碎片
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
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mCityText.setOnClickListener(this);//点击监听
        mSearchBt.setOnClickListener(this);
        mBackButton.setOnClickListener(this);

        mStoreNameText.setOnTextChangedListener(() -> {//文字改变时
            replaceFragment();
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cityText) {//展示城市
            showCity();
        } else if (id == R.id.searchBt) {
            replaceFragment();
        } else if (id == R.id.backButton) {
            finish();
        }
    }

    /**
     * 替换碎片
     */
    private void replaceFragment() {

        CityServiceFragment fragment = new CityServiceFragment(mProvince, mCity, mDistrict, mStoreNameText.getText());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        //在按下返回键的时候不是退出程序而是退到第一个碎片使用transaction的addToBackStack方法，值一般为null
        //transaction.addToBackStack(null);
        transaction.commit();
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


}
