package com.example.common_view.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.example.common_view.bean.ClipViewProperty;

import androidx.annotation.Nullable;


public class ClipView extends View {

    private Paint mStrokePaint;//描边画笔
    private Paint mPaint;
    private Path mCropPath;
    private Rect mRect;

    private ClipViewProperty mPro = new ClipViewProperty();
    private int mRectWidth;

    public ClipView(Context context) {
        super(context);
        mPro.getAttrs(context);
        init();
    }

    public ClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPro.getAttrs(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mPro.getTranslucentColor());//得到透明颜色

        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);//样式为描边
        mStrokePaint.setColor(mPro.getStrokeColor());//设置描边颜色
        mStrokePaint.setStrokeWidth(mPro.getStrokeWidth());//设置描边宽
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        canvas.drawColor(Color.TRANSPARENT);

        Path path = new Path();
        path.addRect(0, 0, width, height, Path.Direction.CW);
        mCropPath = new Path();

        if (mPro.getShape() == ClipViewProperty.RECT) {
            mCropPath.addRect(mRect.left, mRect.top, mRect.right, mRect.bottom, Path.Direction.CW);
            canvas.drawRect(mRect, mStrokePaint);//绘制方形描边
        } else {
            mCropPath.addCircle(width / 2, height / 2, mRectWidth / 2, Path.Direction.CW);
            canvas.drawCircle(width / 2, height / 2, mRectWidth / 2, mStrokePaint);//绘制圆形描边
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            path.op(mCropPath, Path.Op.DIFFERENCE);//去掉不同的部分
            canvas.drawPath(path, mPaint);
        } else {

            //此方法可以去掉锯齿
            //在这里saveLayer然后restoreToCount的操作不能少，否则不会得到想要的效果
            int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawPath(path, mPaint);
            //已经绘制的可以看做为目标图
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawPath(mCropPath, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(layerId);
        }
    }

    /**
     * 得到那个空白矩形
     *
     * @return
     */
    public Rect getRect() {
        return mRect;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int shortWidth = Math.min(w, h);//选择宽和高中小的一个
        mRectWidth = (int) (shortWidth * mPro.getFraction());

        mRect = new Rect((w - mRectWidth) / 2, (h - mRectWidth) / 2,
                (w + mRectWidth) / 2, (h + mRectWidth) / 2);

        if (mOnDrawListener != null) {
            mOnDrawListener.onDraw();
        }
    }

    /**
     * 其实就是得到尺寸后
     */
    public interface OnDrawListener {
        void onDraw();
    }

    private OnDrawListener mOnDrawListener;

    /**
     * 设置监听
     *
     * @param onDrawListener
     */
    public void setOnDrawListener(OnDrawListener onDrawListener) {
        mOnDrawListener = onDrawListener;
    }

    /**
     * 得到路径
     *
     * @return
     */
    public Path getCropPath() {
        return mCropPath;
    }
}
