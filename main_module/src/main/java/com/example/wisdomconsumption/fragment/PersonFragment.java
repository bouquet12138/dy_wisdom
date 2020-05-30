package com.example.wisdomconsumption.fragment;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.base.BaseFragment;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.util.NumberFormatUtil;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.activity.AccountManagerActivity;
import com.example.wisdomconsumption.activity.ScanActivity;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private final int SCAN = 0;//扫码

    private ImageView mHead;
    private TextView mNameText;
    private TextView mRoleText;
    private TextView mPhoneText;
    private TextView mEditText;
    private Button mSaleWithdraw;
    private Button mMerchantWithdraw;
    private TextView mSale_share_integral;
    private TextView mRedeem_integral;
    private TextView mBonus_integral;
    private TextView mSpread_integral;

    private ViewGroup mMyTeam;
    private ViewGroup mCan_get;
    private ViewGroup mWithdraw;
    private ViewGroup mPromotionRecord;
    private ViewGroup mAccountManager;

    private ImageView mScanImg;
    private ImageView mQrImg;
    private ImageView mSetImg;


    public PersonFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentViewId() {
       /* if (NowUserInfo.getNowUserInfo() == null)//TODO:这里等着解开
            return R.layout.main_fragment_no_user;
        else*/
        return R.layout.main_fragment_person;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
       /* if (NowUserInfo.getNowUserInfo() == null) {
            TextView login = mView.findViewById(R.id.login);
            login.setOnClickListener(v -> startLogin());//跳转登陆
        } else */
        {
            initView();
            initData();//初始化数据
            initListener();
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        QMUILinearLayout cardLayout = mView.findViewById(R.id.cardLayout);
        cardLayout.setRadiusAndShadow(SizeUtils.dp2px(10), SizeUtils.dp2px(5), 0.5f);
        cardLayout = mView.findViewById(R.id.cardLayout2);//第二个cardLayout
        cardLayout.setRadiusAndShadow(SizeUtils.dp2px(10), SizeUtils.dp2px(5), 0.5f);

        mHead = mView.findViewById(R.id.head);
        mNameText = mView.findViewById(R.id.nameText);
        mRoleText = mView.findViewById(R.id.roleText);
        mPhoneText = mView.findViewById(R.id.phoneText);

        mEditText = mView.findViewById(R.id.editText);

        mSaleWithdraw = mView.findViewById(R.id.saleWithdraw);
        mMerchantWithdraw = mView.findViewById(R.id.merchantWithdraw);
        mSale_share_integral = mView.findViewById(R.id.sale_share_integral);
        mRedeem_integral = mView.findViewById(R.id.redeem_integral);
        mBonus_integral = mView.findViewById(R.id.bonus_integral);
        mSpread_integral = mView.findViewById(R.id.spread_integral);

        mMyTeam = mView.findViewById(R.id.myTeam);
        mCan_get = mView.findViewById(R.id.can_get);
        mWithdraw = mView.findViewById(R.id.withdraw);
        mPromotionRecord = mView.findViewById(R.id.promotionRecord);
        mAccountManager = mView.findViewById(R.id.accountManager);

        mScanImg = mView.findViewById(R.id.scanImg);
        mQrImg = mView.findViewById(R.id.qrImg);
        mSetImg = mView.findViewById(R.id.setImg);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        setTitle(0, "销售积分", mSale_share_integral);//销售分润
        setTitle(0, "兑换积分", mRedeem_integral);//兑换积分
        setTitle(0, "分润积分", mBonus_integral);//分润积分
        setTitle(0, "推广积分", mSpread_integral);//推广积分
    }

    /**
     * 设置标题
     *
     * @param _num
     * @param _str
     * @param textView
     */
    private void setTitle(long _num, String _str, TextView textView) {

        String numStr = NumberFormatUtil.formatNum(_num);
        String str = numStr + "\n" + _str;

        SpannableString spannableString = new SpannableString(str);

        spannableString.setSpan(new AbsoluteSizeSpan(SizeUtils.dp2px(13)),
                numStr.length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.app_text_color)),
                numStr.length(), str.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        textView.setText(spannableString);

    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mEditText.setOnClickListener(this);

        mMyTeam.setOnClickListener(this);
        mCan_get.setOnClickListener(this);
        mWithdraw.setOnClickListener(this);
        mPromotionRecord.setOnClickListener(this);
        mScanImg.setOnClickListener(this);
        mQrImg.setOnClickListener(this);
        mSetImg.setOnClickListener(this);
        mAccountManager.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editText:
                ARouter.getInstance().build(ARouterContract.LOGIN_INFO) //跳转到用户信息页面
                        .navigation();
                break;
            case R.id.myTeam:
                break;
            case R.id.can_get:
                break;
            case R.id.withdraw:
                break;
            case R.id.promotionRecord:
                break;
            case R.id.scanImg:
                String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

                if (EasyPermissions.hasPermissions(getContext(), perms))//判断有没有权限
                    startScan();//去扫码
                else
                    EasyPermissions.requestPermissions(this, "扫码", SCAN, perms);
                break;
            case R.id.qrImg:
                break;
            case R.id.accountManager://账号管理
            case R.id.setImg:
                startActivity(new Intent(getContext(), AccountManagerActivity.class));
                break;
        }
    }

    /**
     * 跳转到扫码界面
     */
    private void startScan() {
        startActivity(new Intent(getContext(), ScanActivity.class));//启动活动
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == SCAN) {//如果是扫码
            startScan();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        showToast("用户拒接权限，无法扫码");
    }

}
