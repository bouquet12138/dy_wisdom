package com.example.common_view.custom_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


import com.example.common_view.utils.BitmapCompressUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

import androidx.annotation.Nullable;

/**
 * 图片缩放
 */
public class ZoomImageView extends androidx.appcompat.widget.AppCompatImageView {

    private static final String TAG = "ZoomImageView";

    private Context mContext;

    private Rect mLimit;//限制的Rect

    private Matrix mMatrix = new Matrix();//矩阵
    private Matrix mSaveMatrix = new Matrix();//用于保存的矩阵

    private final int NONE = 0;//没有
    private final int DRAG = 1;//拖拽
    private final int ZOOM = 2;//变焦

    private int mCurrentMode = NONE;//当前状态

    private float mOldDist = 1f;
    private float[] mValues = new float[9];//存放值的数组

    private PointF mStart = new PointF();//开始点
    private PointF mMid = new PointF();//中间点

    private int mMaxHigh, mMaxWidth;//最大宽高 为了不让它无限缩放

    public ZoomImageView(Context context) {
        super(context);
        init(context);
    }

    public ZoomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    /**
     * 初始化
     */
    private void init(Context context) {
        mContext = context;
        setScaleType(ScaleType.MATRIX);//采用矩阵缩放
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (getDrawable() == null)//如果图片源为空直接返回
            return false;
        Rect rect = getDrawable().getBounds();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mMatrix.set(getImageMatrix());
                mSaveMatrix.set(mMatrix);
                mStart.set(event.getX(), event.getY());//设置开始位置
                mCurrentMode = DRAG;//当前状态为拖拽
                break;
            //第二个手指按下时，标记为缩放模式
            case MotionEvent.ACTION_POINTER_DOWN:
                mOldDist = distance(event);//计算两指之间距离
                if (mOldDist > 10f) {
                    mSaveMatrix.set(mMatrix);
                    midPoint(mMid, event);
                    mCurrentMode = ZOOM;//当前模式为变焦
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mCurrentMode = NONE;//当前模式为空
                break;
            //手指移动时，根据当前是移动模式还是缩放模式做相应处理
            case MotionEvent.ACTION_MOVE:
                if (mCurrentMode == DRAG) {//如果当前模式为拖拽
                    mMatrix.set(mSaveMatrix);//将matrix 的数值拷贝到saveMatrix里
                    mMatrix.postTranslate(event.getX() - mStart.x, event.getY() - mStart.y);//平移
                    checkMatrix(rect);//检查一下
                } else if (mCurrentMode == ZOOM) {
                    float newDist = distance(event);//计算两指之间距离
                    if (newDist > 10f) {
                        mMatrix.set(mSaveMatrix);
                        float scale = newDist / mOldDist;
                        mMatrix.postScale(scale, scale, mMid.x, mMid.y);
                        checkMatrix(rect);//检查一下
                    }
                }
                break;
        }
        invalidate();//重绘
        return true;
    }

    /**
     * 检查矩阵
     *
     * @param rect
     */
    private void checkMatrix(Rect rect) {
        if (mLimit == null) {
            return;
        }

        if (mCurrentMode == ZOOM) {//如果是缩放
            mMatrix.getValues(mValues);//将矩阵中的数据传递到数组中
            if (rect.width() * mValues[0] < mLimit.width()) {
                float scale = mLimit.width() / (float) rect.width() / mValues[0];
                mMatrix.postScale(scale, scale, mMid.x, mMid.y);
            } else if (rect.width() * mValues[0] > mMaxWidth) {//如果超过了最大缩放
                float scale = mMaxWidth / (float) rect.width() / mValues[0];
                mMatrix.postScale(scale, scale, mMid.x, mMid.y);
            }
            mMatrix.getValues(mValues);//重新得到各个值
            if (rect.height() * mValues[4] < mLimit.height()) {
                float scale = mLimit.height() / (float) rect.height() / mValues[4];
                mMatrix.postScale(scale, scale, mMid.x, mMid.y);
            } else if (rect.height() * mValues[4] > mMaxHigh) {
                float scale = mMaxHigh / (float) rect.height() / mValues[4];
                mMatrix.postScale(scale, scale, mMid.x, mMid.y);
            }
        }

        mMatrix.getValues(mValues);//再将矩阵中的值取出来
        if (mValues[2] >= mLimit.left) {
            mMatrix.postTranslate(mLimit.left - mValues[2], 0);//向左移动
        }
        mMatrix.getValues(mValues);//再将矩阵中的值取出来
        if (mValues[2] + rect.width() * mValues[0] < mLimit.right) {
            mMatrix.postTranslate(mLimit.right - rect.width() * mValues[0] - mValues[2], 0);//向右移动一下
        }
        mMatrix.getValues(mValues);//再将矩阵中的值取出来
        if (mValues[5] > mLimit.top) {
            mMatrix.postTranslate(0, mLimit.top - mValues[5]);
        }
        mMatrix.getValues(mValues);//再将矩阵中的值取出来
        if (mValues[5] + rect.height() * mValues[4] < mLimit.bottom) {
            mMatrix.postTranslate(0, mLimit.bottom - rect.height() * mValues[4] - mValues[5]);
        }
    }

    /**
     * 计算两个点之间的中点,用于缩放
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() != null) {
            setImageMatrix(mMatrix);//设置一下矩阵
        }
        super.onDraw(canvas);
    }

    /**
     * 计算双指距离
     *
     * @param event
     * @return
     */
    private float distance(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        if (mLimit == null) {
            mLimit = new Rect(0, 0, w, h);
        }
        mMaxHigh = 16 * h;
        mMaxWidth = 16 * w;
    }

    /**
     * 设置缩放限制
     * 并将imageView 居中
     *
     * @param limit
     */
    public void setLimit(Rect limit) {
        mLimit = limit;
        if (getDrawable() != null) {

            float displayWidth = getMeasuredWidth();
            float displayHeight = getMeasuredHeight();
            if (displayWidth > 0 && displayHeight > 0) {

                float imageWidth = getDrawable().getIntrinsicWidth();
                float imageHeight = getDrawable().getIntrinsicHeight();

                if (imageWidth > 0 && imageHeight > 0) {

                    float scale;
                    //如果计算fit center状态所需的scale大小
                    if (imageWidth / imageHeight < mLimit.width() / mLimit.height()) {
                        scale = mLimit.width() / imageWidth;
                    } else {
                        scale = mLimit.height() / imageHeight;
                    }
                    //设置fit center状态的scale和位置
                    mMatrix.postScale(scale, scale, imageWidth / 2f, imageHeight / 2f);
                    mMatrix.postTranslate((displayWidth - imageWidth) / 2f, (displayHeight - imageHeight) / 2f);
                    invalidate();//重绘一下
                }
            }
        }
    }

    /**
     * 储存并返回储存路径
     *
     * @return
     */
    public String save(Path cropPath) {
        if (getDrawable() != null) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            this.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(mLimit.width(), mLimit.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.TRANSPARENT);
            int lId = canvas.saveLayer(0, 0, mLimit.width(), mLimit.height(), null, Canvas.ALL_SAVE_FLAG);
            Path path = new Path();
            path.addPath(cropPath, -mLimit.left, -mLimit.top);
            canvas.drawPath(path, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(getDrawingCache(), -mLimit.left, -mLimit.top, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(lId);
            setDrawingCacheEnabled(false);
            bitmap = BitmapCompressUtil.compressBySize(bitmap, 300, 300);//尺寸压缩为400 *400
            return saveBitmap(bitmap);
        }
        return null;
    }

    /**
     * 将图片保存到缓存路径
     *
     * @param bitmap
     * @return
     */
    private String saveBitmap(Bitmap bitmap) {

        Random random = new Random();
        String fileName = "head" + random.nextInt(Integer.MAX_VALUE) + ".png";
        File file = new File(getDiskCachePath(), fileName);
        while (file.exists()) {
            fileName = "head" + random.nextInt(Integer.MAX_VALUE) + ".png";
            file = new File(getDiskCachePath(), fileName);
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    new FileOutputStream(file));
            return file.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 得到应用缓存路径
     *
     * @return
     */
    private String getDiskCachePath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return mContext.getExternalCacheDir().getPath();
        } else {
            return mContext.getCacheDir().getPath();
        }
    }

}
