package com.example.shop_module.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.common_lib.info.ServerInfo;
import com.example.shop_module.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodImageFragment extends Fragment {


    private String mImagePath;//图片路径

    public GoodImageFragment(String imagePath) {
        mImagePath = imagePath;
    }

    protected View mView;
    private PhotoView mImgView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.shop_fragment_good_image, container, false);
        initView();
        initData();
        initListener();
        return mView;
    }

    private void initView() {
        mImgView = mView.findViewById(R.id.imgView);
    }

    private void initData() {
        if (!TextUtils.isEmpty(mImagePath)) {
            Glide.with(getContext()).load(ServerInfo.getImageAddress(mImagePath)).
                    placeholder(R.drawable.image_loading).error(com.example.common_lib.R.drawable.image_error).into(mImgView);
            //设置图片
        }
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mImgView.setOnClickListener(v -> {
            if (mOnImageClickListener != null)
                mOnImageClickListener.onClick();//点击
        });
    }

    public interface OnImageClickListener {
        void onClick();//点击
    }

    private OnImageClickListener mOnImageClickListener;

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }
}
