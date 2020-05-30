package com.example.common_view.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.common_view.R;


public class DatePickerProperty {


    private float mMaxTextSize;
    private float mMinTextSize;
    private int mMaxTextAlpha;
    private int mMinTextAlpha;
    private int mData1Color;
    private int mData2Color;

    private boolean mCanScroll = true;//默认是可以滚动的

    public void init(Context context) {

        float scale = context.getResources().getDisplayMetrics().density;

        mMaxTextSize = 40 * scale;
        mMinTextSize = 20 * scale;
        mMaxTextAlpha = 255;
        mMinTextAlpha = 100;
        mData1Color = 0xffdda4e4;
        mData2Color = 0xff444444;
    }

    public void init(Context context, AttributeSet attrs) {
        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DatePickerView);

        mMaxTextSize = typedArray.getDimension(R.styleable.DatePickerView_maxTextSize, mMaxTextSize);
        mMinTextSize = typedArray.getDimension(R.styleable.DatePickerView_minTextSize, mMinTextSize);
        mMaxTextAlpha = typedArray.getInt(R.styleable.DatePickerView_maxTextAlpha, mMaxTextAlpha);
        mMinTextAlpha = typedArray.getInt(R.styleable.DatePickerView_minTextAlpha, mMinTextAlpha);
        mData1Color = typedArray.getColor(R.styleable.DatePickerView_data1Color, mData1Color);
        mData2Color = typedArray.getColor(R.styleable.DatePickerView_data2Color, mData2Color);

        mMaxTextAlpha = Math.min(255, mMaxTextAlpha);


        typedArray.recycle();
    }

    /**
     * 得到最大尺寸
     *
     * @return
     */
    public float getMaxTextSize() {
        return mMaxTextSize;
    }

    /**
     * 得到最小尺寸
     *
     * @return
     */
    public float getMinTextSize() {
        return mMinTextSize;
    }

    public int getMaxTextAlpha() {
        return mMaxTextAlpha;
    }

    public int getMinTextAlpha() {
        return mMinTextAlpha;
    }

    public int getData1Color() {
        return mData1Color;
    }

    public int getData2Color() {
        return mData2Color;
    }

    public void setMaxTextSize(float maxTextSize) {
        mMaxTextSize = maxTextSize;
    }

    public void setMinTextSize(float minTextSize) {
        mMinTextSize = minTextSize;
    }

    /**
     * 得到文字尺寸的差值
     *
     * @return
     */
    public float getTextSizeDValue() {
        return mMaxTextSize - mMinTextSize;
    }

    /**
     * 得到文字透明度的差值
     *
     * @return
     */
    public int getTextAlphaDValue() {
        return mMaxTextAlpha - mMinTextAlpha;
    }

    public boolean isCanScroll() {
        return mCanScroll;
    }

    public void setCanScroll(boolean canScroll) {
        mCanScroll = canScroll;
    }
}
