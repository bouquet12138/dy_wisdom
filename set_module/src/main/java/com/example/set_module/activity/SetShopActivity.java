package com.example.set_module.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.java_bean.SetBean;
import com.example.common_view.custom_view.ShowPasswordView;
import com.example.common_view.editText.MyEditText;
import com.example.set_module.R;
import com.example.set_module.adapter.SetAdapter;
import com.example.set_module.contract.SetShopContract;
import com.example.set_module.presenter.SetShopPresenter;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.List;

@Route(path = ARouterContract.SET_SET_SHOP)
public class SetShopActivity extends AppMvpBaseActivity implements SetShopContract.IView {

    private SetAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private TextView mMoneyDetailText;
    private LinearLayout mWe_chat_pay;
    private LinearLayout mAli_pay;
    private LinearLayout mIntegral_pay;
    private MyEditText mMyPayPassword;
    private ShowPasswordView mPasswordBt3;
    private QMUIRoundButton mConfirmBt;

    private SetShopPresenter mPresenter = new SetShopPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        mPresenter.attachView(this);//绑定一下view
        mPresenter.getSetList();//得到套餐列表
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mPasswordBt3.setEditText(mMyPayPassword.getEditText());
        mConfirmBt.setOnClickListener(v -> {
            if (mAdapter != null)
                mPresenter.buy_set(mAdapter.getSelectSet());//购买套餐
        });
    }

    /**
     * 初始化view
     */
    private void initView() {
        mRecyclerView = mNormalView.findViewById(R.id.recyclerView);
        mMoneyDetailText = mNormalView.findViewById(R.id.moneyDetailText);
        mWe_chat_pay = mNormalView.findViewById(R.id.we_chat_pay);
        mAli_pay = mNormalView.findViewById(R.id.ali_pay);
        mIntegral_pay = mNormalView.findViewById(R.id.integral_pay);
        mMyPayPassword = mNormalView.findViewById(R.id.myPayPassword);
        mPasswordBt3 = mNormalView.findViewById(R.id.passwordBt3);
        mConfirmBt = mNormalView.findViewById(R.id.confirmBt);
    }

    @Override
    protected String getTitleName() {
        return "套餐店铺";
    }

    @Override
    protected String getRightTextName() {
        return "";
    }

    @Override
    protected int getNormalViewId() {
        return R.layout.set_activity_set_shop;
    }

    @Override
    protected void onRefresh() {
        mPresenter.getSetList();//得到套餐列表
    }

    @Override
    protected void onFloatBtClick() {
    }

    @Override
    public void refreshSetList(List<SetBean> setBeans) {
        if (mAdapter == null) {

            if (!CollectionUtils.isEmpty(setBeans))
                setBeans.get(0).setSelected(true);

            mAdapter = new SetAdapter(setBeans);
            mRecyclerView.setAdapter(mAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置布局管理器
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(10)));
        } else {
            mAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    /**
     * 得到支付密码
     *
     * @return
     */
    @Override
    public String getPayPass() {
        return mMyPayPassword.getText();
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
