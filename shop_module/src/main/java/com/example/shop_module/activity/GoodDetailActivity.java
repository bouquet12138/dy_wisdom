package com.example.shop_module.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.adapter.MyFragmentAdapter;
import com.example.common_lib.activity.ImageWatchActivity;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.fragment.PicFragment;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.GoodBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.shop_module.R;
import com.example.shop_module.contract.GoodDetailContract;
import com.example.shop_module.fragment.GoodImageFragment;
import com.example.shop_module.presenter.GoodDetailPresenter;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.ArrayList;
import java.util.List;


public class GoodDetailActivity extends AppMvpBaseActivity implements View.OnClickListener, GoodDetailContract.IView {

    private static final int CHANGE_ADDRESS = 0;//改变地址

    private ViewPager mViewPager;
    private TextView mImageNumText;

    private TextView mSubText;
    private TextView mNumText;
    private TextView mAddText;
    private TextView mAllCountText;

    private QMUIRoundButton mConfirmBt;
    private TextView mChangeAddress;

    private TextView mGoodTitleText;
    private TextView mGoodDescribeText;
    private TextView mMoneyText;//金额

    private TextView mTak_good_address;

    private MyEditText mPayPassword;
    private ShowPasswordView mPasswordBt;
    private GoodBean mGoodBean;
    private List<String> mImageUrlList;

    private String mName;
    private String mPhone;
    private String mAddress = "";
    private String mDetailAddress;

    private GoodDetailPresenter mPresenter = new GoodDetailPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        initView();
        initListener();
        initGoodData();
        initUserData();
        initAddressData();
        mPresenter.attachView(this);
    }

    /**
     * 初始化view
     */
    private void initView() {

        View buyView = LayoutInflater.from(GoodDetailActivity.this).inflate(R.layout.shop_layout_buy, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);//在父亲底部
        buyView.setLayoutParams(layoutParams);

        mRootView.addView(buyView);//添加一下

        mViewPager = findViewById(R.id.viewPager);
        mImageNumText = findViewById(R.id.imageNumText);

        mSubText = findViewById(R.id.subText);
        mNumText = findViewById(R.id.numText);
        mAddText = findViewById(R.id.addText);

        mAllCountText = findViewById(R.id.allCount);//总金额


        mGoodTitleText = findViewById(R.id.goodTitleText);
        mGoodDescribeText = findViewById(R.id.goodDescribeText);
        mMoneyText = findViewById(R.id.moneyText);//金钱

        mTak_good_address = findViewById(R.id.tak_good_address);
        mChangeAddress = findViewById(R.id.changeAddress);
        mPayPassword = findViewById(R.id.payPassword);
        mPasswordBt = findViewById(R.id.passwordBt);

        mPasswordBt.setEditText(mPayPassword.getEditText());//设置一下

        mConfirmBt = findViewById(R.id.confirmBt);

        QMUILinearLayout qmuiLinearLayout1 = findViewById(R.id.root1);
        QMUILinearLayout qmuiLinearLayout2 = findViewById(R.id.root2);

        qmuiLinearLayout1.setRadiusAndShadow(SizeUtils.dp2px(10), SizeUtils.dp2px(5), 0f);
        qmuiLinearLayout2.setRadiusAndShadow(SizeUtils.dp2px(10), SizeUtils.dp2px(5), 0f);

    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mImageNumText.setText((position + 1) + "/" + mImageUrlList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mSubText.setOnClickListener(this);
        mNumText.setOnClickListener(this);
        mAddText.setOnClickListener(this);
        mConfirmBt.setOnClickListener(this);
        mChangeAddress.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initGoodData() {

        mGoodBean = (GoodBean) getIntent().getSerializableExtra("goodBean");

        if (mGoodBean == null) {
            finish();
            return;
        }

        mGoodTitleText.setText(mGoodBean.getTitle());
        mGoodDescribeText.setText(mGoodBean.getIntroduce());
        mMoneyText.setText("￥" + mGoodBean.getPrice());//金额
        mAllCountText.setText("￥" + mGoodBean.getPrice());

        mImageUrlList = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < mGoodBean.getImage_list().size(); i++) {
            String imageUrl = mGoodBean.getImage_list().get(i).getImage_url();
            mImageUrlList.add(imageUrl);//添加一下

            GoodImageFragment picFragment = new GoodImageFragment(imageUrl);
            fragments.add(picFragment);//添加碎片

            int finalI = i;
            picFragment.setOnImageClickListener(() ->
                    ImageWatchActivity.actionStart(GoodDetailActivity.this, mImageUrlList, finalI));//图片点击
        }

        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(myFragmentAdapter);//设置适配器
        if (!CollectionUtils.isEmpty(mImageUrlList))
            mImageNumText.setText("1/" + mImageUrlList.size());
        else
            mImageNumText.setVisibility(View.GONE);//不可见
    }

    /**
     * 初始化用户数据
     */
    private void initUserData() {
        UserBean userBean = NowUserInfo.getNowUserInfo();//得到当前用户
        if (userBean == null)
            return;

        mName = userBean.getName();
        mPhone = userBean.getPhone();

        if (!TextUtils.isEmpty(userBean.getProvince()))
            mAddress += userBean.getProvince();
        if (!TextUtils.isEmpty(userBean.getCity()))
            mAddress += userBean.getCity();
        if (!TextUtils.isEmpty(userBean.getDistrict()))
            mAddress += userBean.getDistrict();
        mDetailAddress = userBean.getDetail_address();
    }

    /**
     * 初始化地址数据
     */
    private void initAddressData() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("姓名\u3000：");
        if (!TextUtils.isEmpty(mName))
            stringBuilder.append(mName);
        stringBuilder.append("\n");
        stringBuilder.append("手机号：");
        if (!TextUtils.isEmpty(mPhone))
            stringBuilder.append(mPhone);
        stringBuilder.append("\n");
        stringBuilder.append("地址\u3000：");
        stringBuilder.append(mAddress);

        if (!TextUtils.isEmpty(mDetailAddress))
            stringBuilder.append(mDetailAddress);//详细收货地址

        mTak_good_address.setText(stringBuilder.toString());
    }

    @Override
    protected String getTitleName() {
        return "商品详情";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.shop_layout_good_detail;
    }

    @Override
    protected void onRefresh() {

    }

    @Override
    protected void onFloatBtClick() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.subText) {
            changeNum(-1);
        } else if (id == R.id.addText) {
            changeNum(1);
        } else if (id == R.id.confirmBt) {
            UserBean userBean = NowUserInfo.getNowUserInfo();
            if (userBean == null) {
                showErrorHint("请先登录");
            } else {
                mPresenter.buyGood();//购买商品
            }
        } else if (id == R.id.changeAddress) {
            UserBean userBean = NowUserInfo.getNowUserInfo();
            if (userBean == null) {
                showErrorHint("请先登录");
            } else {
                Intent intent = new Intent(GoodDetailActivity.this, ModifyAddressActivity.class);

                intent.putExtra("name", mName);
                intent.putExtra("phone", mPhone);

                intent.putExtra("address", mAddress);
                intent.putExtra("detail_address", mDetailAddress);

                startActivityForResult(intent, CHANGE_ADDRESS);//启动活动
            }
        }
    }

    /**
     * 改变文字
     *
     * @param change
     */
    private void changeNum(int change) {
        int num = Integer.parseInt(mNumText.getText().toString());
        num += change;

        num = Math.max(1, num);

        num = Math.min(10, num);//最多10

        mAllCountText.setText("￥" + (mGoodBean.getPrice() * num));
        mNumText.setText(num + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_ADDRESS && resultCode == RESULT_OK) {//修改成功

            mName = data.getStringExtra("name");
            mPhone = data.getStringExtra("phone");
            mAddress = data.getStringExtra("address");
            mDetailAddress = data.getStringExtra("detail_address");

            initAddressData();//初始化一下地址信息

        }
    }

    public static void actionStart(Context context, GoodBean goodBean) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra("goodBean", goodBean);
        context.startActivity(intent);//启动活动
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getDetailAddress() {
        return mDetailAddress;
    }

    @Override
    public String getPass() {
        return mPayPassword.getText();//得到密码
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    @Override
    public int getGoodId() {
        return mGoodBean.getGood_id();
    }


    @Override
    public int getNum() {
        return Integer.parseInt(mNumText.getText().toString());
    }
}
