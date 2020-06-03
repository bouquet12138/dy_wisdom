package com.example.my_team_module.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.UserBean;
import com.example.my_team_module.R;
import com.example.my_team_module.adapter.MemberAdapter;
import com.example.my_team_module.contract.MyTeamContract;
import com.example.my_team_module.presenter.MyTeamPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = ARouterContract.MY_TEAM_MY_TEAM)
public class MyTeamActivity extends AppMvpBaseActivity implements MyTeamContract.IView {

    private MyTeamPresenter mPresenter = new MyTeamPresenter();
    private RecyclerView mRecyclerView;
    private MemberAdapter mMemberAdapter;

    @Autowired(name = "user_id")
    protected int mUserId;//用户id

    @Autowired(name = "user_name")
    protected String mUserName;//用户名称

    private List<UserBean> mUserBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mPresenter.attachView(this);//绑定一下
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //用户信息

        ARouter.getInstance().inject(this);

        if (mUserId == NowUserInfo.getNowUserInfo().getUser_id())
            setSubmitEnable(true);//右边可以用
        else {
            setSubmitEnable(false);//右边可以用
            mRightText.setText("");//没有文字
            mTitleText.setText(mUserName + "的团队");
        }

        mPresenter.getMyTeamInfo(mUserId);//获取我的团队信息
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);//列表布局
    }

    @Override
    protected String getTitleName() {
        return "我的团队";
    }

    @Override
    protected String getRightTextName() {
        return "添加成员";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.my_team_activity;
    }

    @Override
    protected void onFloatBtClick() {
        ARouter.getInstance().build(ARouterContract.LOGIN_REGISTER) //跳转到注册新用户页面
                .navigation();
    }

    private static final String TAG = "MyTeamActivity";

    @Override
    public void setTeamInfo(List<UserBean> userBeans) {

        mUserBeans.clear();//清空一下
        mUserBeans.addAll(userBeans);//添加进来

        if (CollectionUtils.isEmpty(mUserBeans)) {
            showNoMoreData();//展示没有更多数据
        } else {
            showNormalView();//展示正常view
            if (mMemberAdapter == null) {
                mMemberAdapter = new MemberAdapter(this, mUserBeans);
                mRecyclerView.setAdapter(mMemberAdapter);//设置适配器
                mRecyclerView.setLayoutManager(new LinearLayoutManager(MyTeamActivity.this));
                mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(10)));
                mMemberAdapter.setOnItemClickListener(userBean -> {
                    ARouter.getInstance().build(ARouterContract.MY_TEAM_MY_TEAM).withString("user_name", userBean.getName()) //跳转到用户信息页面
                            .withInt("user_id", userBean.getUser_id()).navigation();
                });
            } else {
                mMemberAdapter.notifyDataSetChanged();//唤醒数据更新
            }
        }
    }


    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 刷新
     */
    @Override
    protected void onRefresh() {
        if (mUserId != 0) {//用户id不是0
            showNormalView();//展示正常view
            mPresenter.getMyTeamInfo(mUserId);//获取我的团队信息
        }
    }
}

