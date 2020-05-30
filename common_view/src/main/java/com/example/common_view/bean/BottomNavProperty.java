package com.example.common_view.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.common_view.R;
import com.example.common_view.utils.DensityUtil;


public class BottomNavProperty {


    private int mSelectedRatio;
    private int mUnSelectedRatio;

    private float mImgWidth;
    private float mImgHeight;

    private int mTextColor;
    private float mTextSize;

    public void init(Context context) {
        mSelectedRatio = 5;
        mUnSelectedRatio = 3;
        mImgWidth = DensityUtil.dipToPx(context, 30);
        mImgHeight = mImgWidth;
        mTextColor = 0xff58b4fa;
        mTextSize = DensityUtil.dipToPx(context, 12);
    }

    public void init(Context context, AttributeSet attrs) {
        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomNavigationView);

        mSelectedRatio = typedArray.getInt(R.styleable.BottomNavigationView_selectedRatio, mSelectedRatio);
        mUnSelectedRatio = typedArray.getInt(R.styleable.BottomNavigationView_unSelectedRatio, mUnSelectedRatio);
        mImgWidth = typedArray.getDimension(R.styleable.BottomNavigationView_imgWidth, mImgWidth);
        mImgHeight = typedArray.getDimension(R.styleable.BottomNavigationView_imgHeight, mImgHeight);
        mTextColor = typedArray.getColor(R.styleable.BottomNavigationView_textColor, mTextColor);
        mTextSize = typedArray.getDimension(R.styleable.BottomNavigationView_textSize, mTextSize);

        typedArray.recycle();
    }

    public int getSelectedRatio() {
        return mSelectedRatio;
    }

    public int getUnSelectedRatio() {
        return mUnSelectedRatio;
    }

    public float getImgWidth() {
        return mImgWidth;
    }

    public float getImgHeight() {
        return mImgHeight;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public float getTextSize() {
        return mTextSize;
    }
}
