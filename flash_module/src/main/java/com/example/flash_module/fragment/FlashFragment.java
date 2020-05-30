package com.example.flash_module.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.base.BaseFragment;
import com.example.base_lib.view.SpacesItemDecoration;
import com.example.common_lib.java_bean.FlashBean;
import com.example.flash_module.R;
import com.example.flash_module.activity.FlashActivity;
import com.example.flash_module.activity.FlashDetailActivity;
import com.example.flash_module.adapter.FlashMoreAdapter;
import com.example.flash_module.contract.FlashFragmentContract;
import com.example.flash_module.presenter.FlashFragmentPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlashFragment extends BaseFragment implements FlashFragmentContract.IView {

    private RecyclerView mRecyclerView;
    private FlashMoreAdapter mFlashAdapter;
    private FlashFragmentPresenter mPresenter = new FlashFragmentPresenter();


    @Override
    protected int getContentViewId() {
        return R.layout.flash_fragment;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initView();
        mPresenter.attachView(this);//绑定一下
        mPresenter.initFlashInfo();//初始化一下数据
    }

    private void initView() {
        mRecyclerView = mView.findViewById(R.id.recyclerView);
    }


    @Override
    public void refreshFlashList(List<FlashBean> flashBeans) {
        if (mFlashAdapter == null) {
            mFlashAdapter = new FlashMoreAdapter(flashBeans);
            mRecyclerView.setAdapter(mFlashAdapter);//设置适配器
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.addItemDecoration(new SpacesItemDecoration(SizeUtils.dp2px(5)));
            mFlashAdapter.setOnItemClickListener(new FlashMoreAdapter.OnItemClickListener() {
                @Override
                public void onClick(FlashBean flashBean) {
                    mPresenter.addReadVolume(flashBean);
                    FlashDetailActivity.actionStart(getContext(), flashBean);//启动一下
                }

                @Override
                public void onFooterClick() {
                    Intent intent = new Intent(getContext(), FlashActivity.class);
                    startActivity(intent);//启动活动
                }
            });
        } else {
            mFlashAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
