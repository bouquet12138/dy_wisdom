package com.example.my_team_module.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.TeamInfoBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.my_team_module.R;
import com.example.my_team_module.contract.ShowTeamContract;
import com.example.my_team_module.presenter.ShowTeamPresenter;

public class ShowTeamActivity extends AppMvpBaseActivity implements ShowTeamContract.IView, View.OnClickListener {

    private TextView mBusinessNum;
    private TextView mCoopNum;
    private TextView mAgentNum;
    private TextView mServiceNum;
    private TextView mServiceCenterNum;

    private int mUserId;//传递过来的数据
    private ShowTeamPresenter mPresenter = new ShowTeamPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
        mPresenter.attachView(this);//绑定一下
        mPresenter.showTeam(mUserId);
    }


    /**
     * 初始化view
     */
    private void initView() {
        mSmartRefreshLayout.setBackgroundColor(getResources().getColor(R.color.app_title_color));
        mBusinessNum = mNormalView.findViewById(R.id.businessNum);
        mCoopNum = mNormalView.findViewById(R.id.coopNum);
        mAgentNum = mNormalView.findViewById(R.id.agentNum);
        mServiceNum = mNormalView.findViewById(R.id.serviceNum);
        mServiceCenterNum = mNormalView.findViewById(R.id.serviceCenterNum);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mBusinessNum.setOnClickListener(this);
        mCoopNum.setOnClickListener(this);
        mAgentNum.setOnClickListener(this);
        mServiceNum.setOnClickListener(this);
        mServiceCenterNum.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mUserId = getIntent().getIntExtra("user_id", 0);
        String userName = getIntent().getStringExtra("user_name");
        mTitleText.setText(userName + "的部门统计");
    }

    @Override
    protected String getTitleName() {
        return "部门统计";
    }

    @Override
    protected String getRightTextName() {
        return null;
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.my_team_layout_show_team;
    }

    @Override
    protected void onRefresh() {
        mPresenter.showTeam(mUserId);
    }

    @Override
    protected void onFloatBtClick() {

    }

    /**
     * 启动活动
     *
     * @param context
     * @param user_id
     */
    public static void actionStart(Context context, int user_id, String user_name) {
        Intent intent = new Intent(context, ShowTeamActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_name", user_name);
        context.startActivity(intent);//启动活动
    }

    /**
     * 设置数据
     *
     * @param teamInfoBean 团队信息
     */
    @Override
    public void setTeamInfo(TeamInfoBean teamInfoBean) {
        if (teamInfoBean == null)
            return;
        mBusinessNum.setText(UserBean.ROLE_ALLIANCE_BUSINESS + " " + teamInfoBean.getBusiness_num() + "人");
        mCoopNum.setText(UserBean.ROLE_COOPERATIVE_PARTNER + " " + teamInfoBean.getCoop_num() + "人");
        mAgentNum.setText(UserBean.ROLE_AGENT + " " + teamInfoBean.getAgent_num() + "人");
        mServiceNum.setText(UserBean.ROLE_SERVICE + " " + teamInfoBean.getService_num() + "人");
        mServiceCenterNum.setText(UserBean.ROLE_SERVICE_CENTER + " " + teamInfoBean.getService_center_num() + "人");
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.businessNum) {
            ARouter.getInstance().build(ARouterContract.SALE_SHARE_TEAM).withString("role", UserBean.ROLE_ALLIANCE_BUSINESS) //加盟商
                    .withInt("user_id", mUserId).navigation();
        } else if (id == R.id.coopNum) {
            ARouter.getInstance().build(ARouterContract.SALE_SHARE_TEAM).withString("role", UserBean.ROLE_COOPERATIVE_PARTNER) //合作商
                    .withInt("user_id", mUserId).navigation();
        } else if (id == R.id.agentNum) {
            ARouter.getInstance().build(ARouterContract.SALE_SHARE_TEAM).withString("role", UserBean.ROLE_AGENT) //代理商
                    .withInt("user_id", mUserId).navigation();
        } else if (id == R.id.serviceNum) {
            ARouter.getInstance().build(ARouterContract.SALE_SHARE_TEAM).withString("role", UserBean.ROLE_SERVICE) //服务商
                    .withInt("user_id", mUserId).navigation();
        } else if (id == R.id.serviceCenterNum) {
            ARouter.getInstance().build(ARouterContract.SALE_SHARE_TEAM).withString("role", UserBean.ROLE_SERVICE_CENTER) //服务中心
                    .withInt("user_id", mUserId).navigation();
        }
    }
}
