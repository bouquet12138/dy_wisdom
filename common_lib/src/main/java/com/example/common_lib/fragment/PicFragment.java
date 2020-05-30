package com.example.common_lib.fragment;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.NetworkUtils;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.base_lib.base.BaseFragment;
import com.example.base_lib.base.MyApplication;
import com.example.base_lib.util.SaveImageUtil;
import com.example.common_lib.R;
import com.example.common_lib.contract.PicContract;
import com.example.common_lib.info.AppInfo;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.presenter.PicPresenter;
import com.qmuiteam.qmui.widget.QMUIProgressBar;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@link Fragment} subclass.
 */
public class PicFragment extends BaseFragment implements PicContract.IView {

    private String mImagePath;//图片路径

    private Bitmap mBitmap;
    private boolean mSaveIng = false;

    private PhotoView mImgView;

    private ViewGroup mProgressViewGroup;
    private QMUIProgressBar mQMUIProgressBar;

    private PicPresenter mPresenter = new PicPresenter();

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.common_fragment_pic;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        initView();
        initData();
        initListener();
        mPresenter.attachView(this);//绑定view
    }

    /**
     * 初始化view
     */
    private void initView() {
        mImgView = mView.findViewById(R.id.imgView);
        mProgressViewGroup = mView.findViewById(R.id.progressViewGroup);
        mQMUIProgressBar = mView.findViewById(R.id.circleProgressBar);
    }

    //初始化数据
    private void initData() {
        mQMUIProgressBar.setQMUIProgressBarTextGenerator((progressBar, value, maxValue) -> 100 * value / maxValue + "%");
        mQMUIProgressBar.setMaxValue(100);

        RequestListener<Drawable> listener = new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mImgView.enable();//启用
                if (resource instanceof BitmapDrawable) {
                    BitmapDrawable bd = (BitmapDrawable) resource;
                    mBitmap = bd.getBitmap();
                }
                return false;
            }
        };
        if (!TextUtils.isEmpty(mImagePath)) {
            Glide.with(getContext()).load(ServerInfo.getImageAddress(mImagePath)).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).listener(listener).into(mImgView);
            //设置图片
        }
    }

    private void initListener() {
        mImgView.setOnClickListener(v -> {
            if (mOnPicClickListener != null)
                mOnPicClickListener.onClick();
        });

        mImgView.setOnLongClickListener(v -> {
            if (mOnPicClickListener != null)
                mOnPicClickListener.onLongClick();
            return true;
        });
    }

    @Override
    public void showProgress(boolean show) {
        if (show)
            mProgressViewGroup.setVisibility(View.VISIBLE);//可见
        else
            mProgressViewGroup.setVisibility(View.GONE);//不可见
    }

    @Override
    public void setProgress(int progress) {
        mQMUIProgressBar.setProgress(progress);
    }

    public interface OnPicClickListener {
        void onClick();//点击

        void onLongClick();//长按
    }


    private OnPicClickListener mOnPicClickListener;

    public void setOnPicClickListener(OnPicClickListener onPicClickListener) {
        mOnPicClickListener = onPicClickListener;
    }


    /**
     * 保存图片
     */
    public void saveImage() {

        if (mSaveIng || mPresenter.isDowning()) {
            Toast.makeText(getContext(), "正在保存，请稍后", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mBitmap == null) {
            if (!NetworkUtils.isAvailable()) {
                Toast.makeText(getContext(), "网络错误保存失败!", Toast.LENGTH_SHORT).show();
                return;
            }
            mPresenter.downLoadImg(mImagePath);//下载图片
        } else {
            if (SaveImageUtil.isExist(AppInfo.IMAGE_DIR, mImagePath)) {
                Toast.makeText(getContext(), "图片已存在", Toast.LENGTH_SHORT).show();
                return;
            }
            saveImage2();
        }
    }


    private void saveImage2() {
        if (mBitmap == null)
            return;

        new Thread(() -> {
            if (mSaveIng)//正在保存
                return;
            mSaveIng = true;
            boolean save = SaveImageUtil.saveImage(AppInfo.IMAGE_DIR, mImagePath, mBitmap);//保存图片

            getActivity().runOnUiThread(() -> {
                if (save)
                    Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MyApplication.getContext(), "储存卡不可用", Toast.LENGTH_SHORT).show();
            });
            mSaveIng = false;
        }).start();

    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();//解除绑定
        super.onDestroyView();
    }
}
