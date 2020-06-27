package com.example.wisdomconsumption.activity;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.AppBean;

import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.contract.AppContract;
import com.example.wisdomconsumption.presenter.AppPresenter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class PromotionActivity extends AppMvpBaseActivity implements AppContract.IView {

    private ImageView mAppImage;
    private AppPresenter mPresenter = new AppPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showNormalView();//展示正常view
        mSmartRefreshLayout.setBackgroundColor(getResources().getColor(R.color.app_theme_color));
        initView();
        setSubmitEnable(false);
        mPresenter.attachView(this);//绑定一下view
        mPresenter.getAppInfo();//得到
    }

    @Override
    protected String getTitleName() {
        return "App推广";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.main_layout_promotion;
    }



    @Override
    protected void onRefresh() {
        mPresenter.getAppInfo();//得到
    }

    @Override
    protected void onFloatBtClick() {

    }

    private void initView() {
        mNetErrorView.setBackgroundColor(0xffffffff);
        mAppImage = findViewById(R.id.appImage);
    }

    public Bitmap getQrCode(int width, int height, String content) {
        try {
            Map<EncodeHintType, Object> hints = null;
            String encoding = getEncoding(content);//获取编码格式
            if (encoding != null) {
                hints = new EnumMap<>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, encoding);
            }
            BitMatrix result = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);//通过字符串创建二维矩阵
            int[] pixels = new int[width * height];

            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;//根据二维矩阵数据创建数组
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//创建位图
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);//设置位图像素集为数组
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private static final String TAG = "PromotionActivity";

    @Override
    public void setAppInfo(AppBean appBean) {
        Log.d(TAG, "setAppInfo: " + appBean);
        if (appBean != null && appBean.getApp_url() != null)
            Glide.with(this).load(getQrCode(SizeUtils.dp2px(300),
                    SizeUtils.dp2px(300), ServerInfo.getAppDownloadAddress(appBean.getApp_url()))).into(mAppImage);
    }
}
