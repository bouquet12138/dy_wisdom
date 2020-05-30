package com.example.wisdomconsumption.activity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.wisdomconsumption.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainStartActivity extends MVPBaseActivity {

    private static final String TAG = "MainStartActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();
        setContentView(R.layout.main_activity_start);

        ARouter.getInstance().build(ARouterContract.LOGIN_START) //跳转到登陆页面
                .navigation();
        Log.e(TAG, "onCreate: " + AppUtils.getAppSignatureMD5());
        finish();

    }


}
