package com.example.login_module.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.bumptech.glide.Glide;

import com.example.common_lib.activity.ImageClipActivity;
import com.example.common_lib.activity.SelectImagePermissionActivity;
import com.example.common_lib.bean.ImageUploadBean;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.ImageBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_lib.util.HeadImageUtil;
import com.example.common_view.custom_view.BottomPopDateSelect;
import com.example.common_view.editText.MyEditText;
import com.example.login_module.R;
import com.example.login_module.contract.InformationContract;
import com.example.login_module.presenter.InformationPresenter;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//TODO:跳转还没处理
@Route(path = ARouterContract.LOGIN_INFO)
public class InformationActivity extends SelectImagePermissionActivity
        implements View.OnClickListener, InformationContract.IView {

    private int mUserId = 0;//用户id

    private static final int CLIP_IMG = 2;//裁剪图片

    private TextView mChangeHeadTextView;
    private QMUIRadiusImageView mHeadView;
    private MyEditText mNameText;

    private RadioButton mMen;
    private RadioButton mWomen;
    private LinearLayout mBirthdayLayout;
    private TextView mBirthdayText;
    private LinearLayout mCityLayout;
    private TextView mCityText;

    private MyEditText mDetailAddressText;
    private MyEditText mIdCard;
    private MyEditText mBandCard;

    private InformationPresenter mPresenter = new InformationPresenter();
    private UserBean mModifyUserBean = new UserBean();//修改后的用户信息
    private ImageUploadBean mImageUploadBean = new ImageUploadBean();//上传图片

    private BottomPopDateSelect mDateSelect;//底部日期选择器
    //城市选择器
    private CityPickerView mPicker = new CityPickerView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view

        setSubmitEnable(true);//提交按钮可用
        initView();
        initData();//初始化一些数据
        initListener();
        initCity();
        mPresenter.attachView(this);//绑定一下view
        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mUserId = getIntent().getIntExtra("user_id", 0);

        Log.d(TAG, "initData: " + mUserId);

        if (mUserId != 0) {
            mModifyUserBean.setUser_id(mUserId);//设置用户id
            return;
        }
        UserBean bean = NowUserInfo.getNowUserInfo();
        if (bean != null) {

            mUserId = bean.getUser_id();

            mModifyUserBean.setUser_id(mUserId);//设置用户id
            mModifyUserBean.setHead_img_id(0);
            mModifyUserBean.setInfo(bean);//设置进去信息

            HeadImageUtil.setUserHead(bean, this, mHeadView);//设置头像

            if (TextUtils.isEmpty(bean.getName()))
                mNameText.setText("");//设置姓名
            else {
                mNameText.setText(bean.getName());//设置姓名
                mNameText.setCursorPosition(bean.getName().length());
            }
            if ("女".equals(bean.getSex()))
                mWomen.setChecked(true);//选中
            mBirthdayText.setText(bean.getBirthday());

            String region = "";
            if (!TextUtils.isEmpty(bean.getProvince()))
                region += bean.getProvince();
            if (!TextUtils.isEmpty(bean.getCity()))
                region += bean.getCity();
            if (!TextUtils.isEmpty(bean.getDistrict()))
                region += bean.getDistrict();

            mCityText.setText(region);//设置地区
            mIdCard.setText(bean.getId_card());//身份证号
            mBandCard.setText(bean.getBank_num());//银行卡号
        }

    }

    @Override
    protected String getTitleName() {
        return "完善个人信息";
    }

    @Override
    protected String getRightTextName() {
        return "提交";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.login_layout_information;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {
        if (TextUtils.isEmpty(mNameText.getText())) {
            showErrorHint("姓名不能为空");
            return;
        }
        mPresenter.submit();//提交数据
    }

    private void initView() {
        mChangeHeadTextView = findViewById(R.id.changeHeadTextView);
        mHeadView = findViewById(R.id.headView);
        mNameText = findViewById(R.id.nameText);

        mMen = findViewById(R.id.men);
        mWomen = findViewById(R.id.women);
        mBirthdayLayout = findViewById(R.id.birthdayLayout);
        mBirthdayText = findViewById(R.id.birthdayText);
        mCityLayout = findViewById(R.id.cityLayout);
        mCityText = findViewById(R.id.cityText);

        mDetailAddressText = findViewById(R.id.detailAddressText);//详细地址
        mIdCard = findViewById(R.id.idCard);
        mBandCard = findViewById(R.id.bandCard);

        mDateSelect = new BottomPopDateSelect(this);
    }

    private void initListener() {
        mChangeHeadTextView.setOnClickListener(this);//更改头像
        mBirthdayLayout.setOnClickListener(this);//点击监听
        mCityLayout.setOnClickListener(this::onClick);

        mDateSelect.setOnConfirmListener((currentStr) -> {
            mBirthdayText.setText(currentStr);
            mDateSelect.dismiss();//日期选择消失
        });//设置当前文本


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.changeHeadTextView) {
            selectImageAuthor();//从相册选择图片
        } else if (i == R.id.birthdayLayout) {
            mDateSelect.show();//展示数据选择
        } else if (i == R.id.cityLayout) {
            KeyboardUtils.hideSoftInput(mCityText);//隐藏键盘
            mPicker.showCityPicker();//展示城市
        }
    }

    @Override
    protected void addImage(List<Uri> uriList, List<String> stringList) {
        if (uriList == null || uriList.size() == 0) {
            Toast.makeText(this, "选中图片不能为空", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ImageClipActivity.class);
            intent.putExtra("filePath", stringList.get(0));
            startActivityForResult(intent, CLIP_IMG);//裁剪图片
        }
    }


    @Override
    public UserBean getModifyUserBean() {

        mModifyUserBean.setName(mNameText.getText());
        if (mMen.isChecked())
            mModifyUserBean.setSex("男");
        else
            mModifyUserBean.setSex("女");
        mModifyUserBean.setBirthday(mBirthdayText.getText().toString());//生日
        mModifyUserBean.setId_card(mIdCard.getText());//身份证号
        mModifyUserBean.setBank_num(mBandCard.getText());//银行卡号
        return mModifyUserBean;
    }

    /**
     * 得到本地图片
     *
     * @return
     */
    @Override
    public String getImageLocalPath() {
        Log.d(TAG, "getImageLocalPath: " + mImageUploadBean.getImageLocalUrl());
        return mImageUploadBean.getImageLocalUrl();
    }


    /**
     * 是否修改了头像
     *
     * @return
     */
    @Override
    public boolean isModifyHeadImage() {
        return !mImageUploadBean.isUpload();
    }

    @Override
    public void setImageBean(@NotNull ImageBean imageBean) {
        mModifyUserBean.setHead_img(imageBean);//设置图片信息
        mModifyUserBean.setHead_img_id(imageBean.getImage_id());
        Log.d(TAG, "setImageBean: " + GsonUtils.toJson(mModifyUserBean));
        Log.d(TAG, "setImageBean: " + mModifyUserBean.getHead_img());
    }

    @Override
    public int getUserID() {
        return mUserId;
    }

    private static final String TAG = "InformationActivity";

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
                mCityText.setText(province.getName() + "-" + city.getName() + "-" + district.getName());
                mModifyUserBean.setProvince(province.getName());
                mModifyUserBean.setCity(city.getName());
                mModifyUserBean.setDistrict(district.getName());
            }

            @Override
            public void onCancel() {
            }
        });
    }

    /**
     * 其他城市返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CLIP_IMG && resultCode == RESULT_OK) {

            String filePath = data.getStringExtra("filePath");

            Glide.with(this).load(filePath).into(mHeadView);//加载图像

            mImageUploadBean.setImageLocalUrl(filePath);//设置图片地址
            mImageUploadBean.setUpload(false);//未上传
        }

    }

    /**
     * 启动activity
     */
    public static void actionStart(Context context, int user_id) {
        Intent intent = new Intent(context, InformationActivity.class);
        intent.putExtra("user_id", user_id);
        context.startActivity(intent);
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();//解除绑定
    }
}
