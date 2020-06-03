package com.example.integer_module.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.base_lib.base.ActivityCollector;
import com.example.base_lib.base.BaseFragment;
import com.example.base_lib.base.IMVPBaseView;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.base_lib.util.NetworkManager;
import com.qmuiteam.qmui.arch.QMUIFragment;

public abstract class MVPQMUIFragment extends QMUIFragment implements IMVPBaseView {


    protected Context mContext;
    protected View mView;


    @Override
    protected View onCreateView() {

        if (mView == null) {
            mView = LayoutInflater.from(getContext()).inflate(getContentViewId(), null);
            this.mContext = getActivity();
            initAllMembersView();
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;

    }

    protected abstract int getContentViewId();

    protected abstract void initAllMembersView();


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
            throw new BaseFragment.ActivityNotAttachedException();
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
