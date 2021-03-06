package com.example.wisdomconsumption.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.GsonUtils;
import com.example.common_lib.contract.ARouterContract;
import com.example.wisdomconsumption.R;
import com.example.wisdomconsumption.java_bean.QRBean;
import com.google.gson.JsonSyntaxException;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class ScanActivity extends AppCompatActivity {

    private static final String TAG = "SignActivity";
    private TextView mPatienceWaitText;//等待TextView

    private boolean mAnalyseSuccess = false;//解析
    private CaptureFragment mCaptureFragment;
    private QMUITipDialog mErrorDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main_activity_scan);
        initView();//初始化view

    }

    /**
     * 初始化view
     */
    private void initView() {

        // 执行扫面Fragment的初始化操作
        mCaptureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(mCaptureFragment, R.layout.main_camera);

        mCaptureFragment.setAnalyzeCallback(analyzeCallback);

        //替换扫描控件
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, mCaptureFragment).commit();

        mPatienceWaitText = findViewById(R.id.patienceWaitText);
    }


    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            if (!mAnalyseSuccess) {//如果没有解析成功
                mPatienceWaitText.setVisibility(View.INVISIBLE);//耐心等待不可见

                Log.d(TAG, "onAnalyzeSuccess: " + result);
                QRBean qrBean = null;
                try {
                    qrBean = GsonUtils.fromJson(result, QRBean.class);
                } catch (JsonSyntaxException e) {
                }

                if (qrBean == null) {
                    showErrorHint("请扫描正确的二维码");
                } else if (QRBean.SALE_SHARE.equals(qrBean.getType())) {
                    mAnalyseSuccess = true;
                    ARouter.getInstance().build(ARouterContract.SALE_SHARE_TRANSFER).withString("targetPhone", qrBean.getPhone_num())
                            .navigation();
                    finish();//销毁自己
                } else if (QRBean.SPREAD.equals(qrBean.getType())) {
                    mAnalyseSuccess = true;
                    ARouter.getInstance().build(ARouterContract.SALE_SHARE_TRANSFER).withString("targetPhone", qrBean.getPhone_num()).withInt("position", 1)
                            .navigation();
                    finish();//销毁自己
                } else if (QRBean.MERCHANTS.equals(qrBean.getType())) {
                    ARouter.getInstance().build(ARouterContract.REDEEM_PAY).withInt("target_user_id", qrBean.getUser_id())
                            .navigation();
                    finish();//销毁自己
                } else {
                    showErrorHint("请扫描正确的二维码");
                }

            } else {
                showErrorHint("请扫描正确的二维码");
            }
        }

        @Override
        public void onAnalyzeFailed() {
            showErrorHint("扫描失败");
        }
    };

    /**
     * 展示错误信息
     *
     * @param hintStr
     */
    public void showErrorHint(String hintStr) {
        mErrorDialog = new QMUITipDialog.Builder(ScanActivity.this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(hintStr)
                .create();
        mErrorDialog.show();//展示一下
        mPatienceWaitText.postDelayed(() -> mErrorDialog.dismiss(), 1000);
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        if (mErrorDialog != null && mErrorDialog.isShowing())//消失
            mErrorDialog.dismiss();
        super.onDestroy();
    }


}
