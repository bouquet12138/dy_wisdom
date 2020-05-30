package com.example.common_view.bottom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.example.common_view.bean.BottomBean;
import com.example.common_view.bean.BottomNavProperty;
import com.example.common_view.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class BottomNavigationView extends View {

    private static final String TAG = "BottomNavigationView";

    private Paint mPaint;//画笔
    private TextPaint mTextPaint;//画笔

    private BottomNavProperty mProperty = new BottomNavProperty();

    private int mSelectedWidth;//被选中的宽度
    private int mUnSelectedWidth;//未选中的宽度

    private int mPosition;//位置
    private float mOffset;//偏移距离

    private int mCurrentSelected = 0;//当前选中

    private List<BottomBean> mBottomBeans;
    private List<Bitmap> mBitmaps;//图片

    public BottomNavigationView(Context context) {
        super(context);
        mProperty.init(context);
        init();
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mProperty.init(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mTextPaint = new TextPaint();
        mTextPaint.setColor(mProperty.getTextColor());
        mTextPaint.setTextSize(mProperty.getTextSize());

        mTextPaint.setTextAlign(Paint.Align.CENTER);//居中绘制
    }

    /**
     * 绘制的方法
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBottomBeans == null || mBottomBeans.size() == 0)
            return;

        int metaWidth = getWidth() / ((mBottomBeans.size() - 1) * mProperty.getUnSelectedRatio()
                + mProperty.getSelectedRatio());
        mUnSelectedWidth = metaWidth * mProperty.getUnSelectedRatio();//未被选中的占的比例
        mSelectedWidth = metaWidth * mProperty.getSelectedRatio();//选中的占的比例


        int dValue = mSelectedWidth - mUnSelectedWidth;//差值

        int left;
        int right = 0;    // 容器已经占据的宽度
        for (int i = 0; i < mBottomBeans.size(); i++) {

            left = right;

            if (i == mCurrentSelected) //选中的情况
                right += mSelectedWidth;//选中的宽度
            else //未选中的情况
                right += mUnSelectedWidth;//未选中的宽度

            if (mPosition == mCurrentSelected) {
                if (i == mPosition)
                    right -= mOffset * dValue;
                else if (i == mPosition + 1)
                    right += mOffset * dValue;
            } else {
                if (i == mPosition)
                    right += dValue * (1 - mOffset);
                else if (i == mPosition + 1)
                    right -= dValue * (1 - mOffset);
            }


            drawBottom(i, left, right, canvas);//绘制底部
        }


    }

    /**
     * 绘制底部
     */
    private void drawBottom(int i, int left, int right, Canvas canvas) {

        left += ((right - left) - mProperty.getImgWidth()) / 2;
        right = (int) (left + mProperty.getImgWidth());

        Rect src = new Rect(0, 0, mBitmaps.get(i * 2).getWidth(), mBitmaps.get(i * 2).getHeight());

        if (i == mCurrentSelected) {//如果选中

            BottomBean bean = mBottomBeans.get(i);

            Rect dst = new Rect(left, DensityUtil.dipToPx(getContext(), 5),
                    right, (int) (DensityUtil.dipToPx(getContext(), 5) + mProperty.getImgHeight()));

            Log.d(TAG, "onDraw: " + src + " " + dst);

            canvas.drawBitmap(mBitmaps.get(i * 2), src, dst, mPaint);//绘制bitmap

            canvas.drawText(bean.getName(), (left + right) / 2,
                    (int) (DensityUtil.dipToPx(getContext(), 18) + mProperty.getImgHeight()), mTextPaint);//绘制文字

        } else {

            Rect dst = new Rect(left, (int) (getHeight() - DensityUtil.dipToPx(getContext(), 5)
                    - mProperty.getImgHeight()),
                    right, getHeight() - DensityUtil.dipToPx(getContext(), 5));

            canvas.drawBitmap(mBitmaps.get(i * 2 + 1), src, dst, mPaint);//绘制bitmap
        }
    }


    /**
     * 设置底部bean
     *
     * @param bottomBeans
     */
    public void setBottomBeans(@NonNull List<BottomBean> bottomBeans) {
        mBottomBeans = bottomBeans;
        mBitmaps = new ArrayList<>();
        for (BottomBean bottomBean : mBottomBeans) {
            mBitmaps.add(BitmapFactory.decodeResource(getResources(), bottomBean.getSelectImg()));//选中的图片
            mBitmaps.add(BitmapFactory.decodeResource(getResources(), bottomBean.getUnSelectImg()));//未选中的图片
        }
    }

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int dValue = mSelectedWidth - mUnSelectedWidth;//差值

            int left;
            int right = 0;    // 容器已经占据的宽度
            for (int i = 0; i < mBottomBeans.size(); i++) {

                left = right;

                if (i == mCurrentSelected) //选中的情况
                    right += mSelectedWidth;//选中的宽度
                else //未选中的情况
                    right += mUnSelectedWidth;//未选中的宽度

                if (mPosition == mCurrentSelected) {
                    if (i == mPosition)
                        right -= mOffset * dValue;
                    else if (i == mPosition + 1)
                        right += mOffset * dValue;
                } else {
                    if (i == mPosition)
                        right += dValue * (1 - mOffset);
                    else if (i == mPosition + 1)
                        right -= dValue * (1 - mOffset);
                }

                if (event.getX() >= left && event.getX() <= right)//点击监听
                    mOnItemClickListener.onClick(i);
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 移动的方法
     *
     * @param position
     * @param positionOffset
     */
    public void move(int position, float positionOffset) {
        mPosition = position;
        mOffset = positionOffset;
        invalidate();//重绘
    }

    /**
     * 设置当前选中
     *
     * @param currentSelected
     */
    public void setCurrentSelected(int currentSelected) {
        mCurrentSelected = currentSelected;
        invalidate();//重绘
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
