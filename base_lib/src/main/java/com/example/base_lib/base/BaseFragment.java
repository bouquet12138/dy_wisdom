package com.example.base_lib.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base_lib.util.NetworkManager;


/**
 * 基础碎片
 * Created by xiaohan on 2018/3/4.
 */

public abstract class BaseFragment extends Fragment implements IMVPBaseView {

    protected Context mContext;
    protected View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getContentViewId(), container, false);
            this.mContext = getActivity();
            initAllMembersView(savedInstanceState);
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;
    }

    protected abstract int getContentViewId();

    protected abstract void initAllMembersView(Bundle savedInstanceState);


    /**
     * 展示加载进度条
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        if (getActivity() != null)
            ((MVPBaseActivity) mContext).showLoading(msg);
    }


    /**
     * 隐藏加载进度条
     */
    @Override
    public void hideLoading() {
        if (getActivity() != null)
            ((MVPBaseActivity) mContext).hideLoading();
    }

    /**
     * 弹出信息
     *
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        if (getActivity() != null)
            ((MVPBaseActivity) mContext).showToast(msg);
    }

    /**
     * activity是否被销毁
     *
     * @return
     */
    protected boolean isAttachedContext() {
        return getActivity() != null;
    }

    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

    public void showLongToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLogin() {
        ActivityCollector.finishAll();//销毁所有activity
        ARouter.getInstance().build("/login/login") // 目标页面
                .navigation();
    }

    /**
     * 销毁活动
     */
    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void showErrorHint(String hintStr) {
        if (getActivity() != null)
            ((MVPBaseActivity) mContext).showErrorHint(hintStr);
    }

    @Override
    public void hideErrorHint() {
        if (getActivity() != null)
            ((MVPBaseActivity) mContext).hideErrorHint();
    }

    @Override
    public void showSuccessHint(String hintStr) {
        if (getActivity() != null)
            ((MVPBaseActivity) mContext).showSuccessHint(hintStr);
    }

    @Override
    public void hideSuccessHint() {
        if (getActivity() != null)
            ((MVPBaseActivity) mContext).hideSuccessHint();
    }

    @Override
    public void registerNetworkListener() {
        if (getActivity() != null)
            ((MVPBaseActivity) mContext).registerNetworkListener();
    }


    @Override
    public void onDestroyView() {
        NetworkManager.getDefault().logout();
        super.onDestroyView();
    }
}
