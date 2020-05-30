package com.example.common_view.bean;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.common_view.R;


public class ClipViewProperty {

    public static final int RECT = 0;//正方形
    public static final int CIRCLE = 1;//圆

    private int mShape;
    private int mTranslucentColor;
    private float mFraction;
    private float mStrokeWidth;
    private int mStrokeColor;

    public void getAttrs(Context context, AttributeSet attrs) {
        getAttrs(context);
        float scale = context.getResources().getDisplayMetrics().density;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClipView);

        mShape = typedArray.getInt(R.styleable.ClipView_shape, mShape);
        mTranslucentColor = typedArray.getColor(R.styleable.ClipView_translucentColor, mTranslucentColor);
        mFraction = typedArray.getFloat(R.styleable.ClipView_fraction, mFraction);
        mStrokeWidth = typedArray.getDimension(R.styleable.ClipView_strokeWidth, mStrokeWidth);
        mStrokeColor = typedArray.getColor(R.styleable.ClipView_strokeColor, mStrokeColor);

        typedArray.recycle();
    }


    public void getAttrs(Context context) {
        mShape = RECT;
        mTranslucentColor = 0x99000000;
        mFraction = 3 / 4f;
        mStrokeWidth = dipToPx(context, 2);
        mStrokeColor = 0xffffffff;
    }

    public int getShape() {
        return mShape;
    }

    public void setShape(int shape) {
        mShape = shape;
    }

    public int getTranslucentColor() {
        return mTranslucentColor;
    }

    public void setTranslucentColor(int translucentColor) {
        mTranslucentColor = translucentColor;
    }

    public float getFraction() {
        return mFraction;
    }

    public void setFraction(float fraction) {
        mFraction = fraction;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
    }

    public int getStrokeColor() {
        return mStrokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
    }

    private int dipToPx(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
