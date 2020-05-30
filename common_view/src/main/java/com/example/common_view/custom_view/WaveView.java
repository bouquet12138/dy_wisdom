package com.example.common_view.custom_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.common_view.R;
import com.example.common_view.utils.DensityUtil;

import androidx.annotation.Nullable;


/**
 * 水波view
 * Created by xiaohan on 2017/11/17.
 */

public class WaveView extends View {

    private Context mContext;
    private int A;//振幅
    private int K;//偏局
    private int waveColor;
    private float φ;//初相
    private float waveSpeed;//波形移动速度
    private double period;//周期
    private float waveNum;//波数
    private double startPeriod;//开始位置相差多少个周期
    private boolean waveStart;//是否直接开启波形
    private Path path;//路径
    private Paint paint;
    private int waveFillType;//波纹填充方向
    private ValueAnimator valueAnimator;//值动画
    private static final int Top = 0;
    private static final int Bottom = 1;

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttr(attrs);
        K = A;
        initPaint();//初始化画笔
        initAnimation();//初始化动画
    }

    //初始化画笔
    private void initPaint() {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(waveColor);//画笔颜色
    }

    private void getAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.WaveView);

        waveFillType = typedArray.getInt(R.styleable.WaveView_waveFillType, Top);
        A = typedArray.getInt(R.styleable.WaveView_waveAmplitude, DensityUtil.dipToPx(getContext(), 10));
        waveColor = typedArray.getColor(R.styleable.WaveView_waveColor, 0x88ffffff);
        waveNum = typedArray.getFloat(R.styleable.WaveView_waveNum, 2);
        waveStart = typedArray.getBoolean(R.styleable.WaveView_waveStart, true);//默认是开启动画的
        waveSpeed = typedArray.getFloat(R.styleable.WaveView_waveSpeed, 3f);
        φ = typedArray.getFloat(R.styleable.WaveView_PrimaryPhase, 0);//初相
        startPeriod = typedArray.getFloat(R.styleable.WaveView_startPeriod, 0);
        typedArray.recycle();
    }

    //初始化动画
    private void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, getWidth());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.addUpdateListener(valueAnimator -> invalidate());
        //如果水波开始，那么就动画开始
        if (waveStart) {
            valueAnimator.start();
        }
    }

    //当尺寸改变
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        period = Math.PI / getWidth();
    }

    private static final String TAG = "WaveView";

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "onDraw: ");
        switch (waveFillType) {
            case Top:
                drawTop(canvas);
                break;
            case Bottom:
                drawBottom(canvas);
                break;
        }

    }

    //向下画
    private void drawBottom(Canvas canvas) {
        φ -= waveSpeed / 100;
        float y;

        path.reset();
        path.moveTo(0, getHeight());

        for (float x = 0; x <= getWidth(); x += 20) {
            y = (float) (A * Math.sin(period * waveNum * x + startPeriod * Math.PI + φ) + K);//振幅
            path.lineTo(x, getHeight() - y);
        }

        path.lineTo(getWidth(), 0);
        path.lineTo(0, 0);
        path.close();//路径关闭

        canvas.drawPath(path, paint);
    }

    //向上画
    private void drawTop(Canvas canvas) {

        φ -= waveSpeed / 100;
        float y;

        path.reset();
        path.moveTo(0, 0);

        //x 向右移
        for (float x = 0; x <= getWidth(); x += 20) {
            y = (float) (A * Math.sin(period * waveNum * x + φ + Math.PI * startPeriod) + K);
            path.lineTo(x, y);
        }

        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onDetachedFromWindow() {
        valueAnimator.end();//动画结束
        super.onDetachedFromWindow();
    }
}
