package com.example.common_view.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.common_view.R;


public class MyCalendarProperty {


    private float mTitleSize;
    private int mTitleColor;
    private float mDaySize;
    private int mDayColor;
    private float mSpaceY;
    private boolean mDrawLunar;
    private float mLunarSize;
    private int mLunarColor;
    private int mLegalColor;
    private int mTodayCircleColor;
    private int mTodayTextColor;
    private float mTodayCircleRadius;
    private int mSelectedBackColor;
    private int mSelectedTextColor;

    public void init(Context context) {
        mTitleSize = dipToPx(context, 16);//标题栏尺寸
        mTitleColor = 0xFF94aebb;//标题栏颜色
        mDaySize = dipToPx(context, 16);//日期尺寸
        mDayColor = 0xFF94aebb;
        mSpaceY = dipToPx(context, 20);//间距
        mDrawLunar = true;//绘制阴历什么的
        mLunarSize = dipToPx(context, 12);//小字大小
        mLunarColor = 0xFF94aebb;//阴历颜色

        mLegalColor = 0xFFC98F47;//法定节假日颜色

        mTodayCircleColor = 0xFF94aebb;
        mTodayTextColor = 0xFFFFFFFF;

        mTodayCircleRadius = dipToPx(context, 15);

        mSelectedBackColor = 0xFF94aebb;
        mSelectedTextColor = 0xFFFFFFFF;
    }

    public void init(Context context, AttributeSet attrs) {
        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCalendarView);

        mTitleSize = typedArray.getDimension(R.styleable.MyCalendarView_titleSize, mTitleSize);
        mTitleColor = typedArray.getColor(R.styleable.MyCalendarView_titleColor, mTitleColor);
        mDaySize = typedArray.getDimension(R.styleable.MyCalendarView_daySize, mDaySize);
        mDayColor = typedArray.getColor(R.styleable.MyCalendarView_dayColor, mDayColor);
        mSpaceY = typedArray.getDimension(R.styleable.MyCalendarView_spaceY, mSpaceY);
        mDrawLunar = typedArray.getBoolean(R.styleable.MyCalendarView_drawLunar, mDrawLunar);
        mLunarSize = typedArray.getDimension(R.styleable.MyCalendarView_lunarSize, mLunarSize);
        mLunarColor = typedArray.getColor(R.styleable.MyCalendarView_lunarColor, mLunarColor);
        mLegalColor = typedArray.getColor(R.styleable.MyCalendarView_legalColor, mLegalColor);
        mTodayCircleColor = typedArray.getColor(R.styleable.MyCalendarView_todayCircleColor, mTodayCircleColor);
        mTodayTextColor = typedArray.getColor(R.styleable.MyCalendarView_todayTextColor, mTodayTextColor);
        mTodayCircleRadius = typedArray.getDimension(R.styleable.MyCalendarView_todayCircleRadius, mTodayCircleRadius);

        mSelectedBackColor = typedArray.getColor(R.styleable.MyCalendarView_selectedBackColor, mSelectedBackColor);
        mSelectedTextColor = typedArray.getColor(R.styleable.MyCalendarView_selectedTextColor, mSelectedTextColor);

        typedArray.recycle();
    }

    public float getTitleSize() {
        return mTitleSize;
    }

    public int getTitleColor() {
        return mTitleColor;
    }

    public float getDaySize() {
        return mDaySize;
    }

    public int getDayColor() {
        return mDayColor;
    }

    public float getSpaceY() {
        return mSpaceY;
    }

    public boolean isDrawLunar() {
        return mDrawLunar;
    }

    public float getLunarSize() {
        return mLunarSize;
    }

    public int getLunarColor() {
        return mLunarColor;
    }

    public int getLegalColor() {
        return mLegalColor;
    }

    public int getTodayCircleColor() {
        return mTodayCircleColor;
    }

    public int getTodayTextColor() {
        return mTodayTextColor;
    }

    public float getTodayCircleRadius() {
        return mTodayCircleRadius;
    }

    public int getSelectedBackColor() {
        return mSelectedBackColor;
    }

    public int getSelectedTextColor() {
        return mSelectedTextColor;
    }

    private float dipToPx(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

}
