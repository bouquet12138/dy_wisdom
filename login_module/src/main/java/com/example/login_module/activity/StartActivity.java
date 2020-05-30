package com.example.login_module.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.login_module.R;
import com.example.login_module.contract.StartContract;
import com.example.login_module.presenter.StartPresenter;

@Route(path = ARouterContract.LOGIN_START)//路径
public class StartActivity extends MVPBaseActivity implements StartContract.IView {

    private StartPresenter mPresenter;
    private ImageView mIcon;//图标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fullScreen();//全屏显示
        setContentView(R.layout.login_activity_start);
        initView();
        mPresenter = new StartPresenter();//初始化一下
        mPresenter.attachView(this);//绑定一下
        mPresenter.initInfo();//初始化信息
    }

    /**
     * 初始化view
     */
    private void initView() {
        mIcon = findViewById(R.id.icon);
        ValueAnimator animator = ValueAnimator.ofFloat(2, 1);
        animator.setDuration(2500);//播放时长
        //animator.setRepeatCount(0);//重放次数
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            mIcon.setScaleX(value);
            mIcon.setScaleY(value);
        });
        animator.start();//启动一下
    }

    /**
     * 启动主界面
     */
    @Override
    public void startMainActivity() {
        overridePendingTransition(R.anim.app_fade_in, R.anim.app_fade_out);//淡入淡出
        ARouter.getInstance().build(ARouterContract.MAIN_MAIN) // 目标页面
                .navigation();
        finish();//销毁自己
    }

    /**
     * 启动登陆页面
     */
    @Override
    public void startLoginActivity() {
        overridePendingTransition(R.anim.app_fade_in, R.anim.app_fade_out);//淡入淡出
        startActivity(new Intent(StartActivity.this, LoginActivity.class));
        finish();//销毁自己
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();//解除绑定
    }

}

