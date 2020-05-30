package com.example.common_view.swipeView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class SwipeView extends FrameLayout {

    private static final String TAG = "SwipeView";

    private int mPL, mPR, mPT, mPB;

    private View contentView;// item内容区域的view
    private View deleteView;// delete区域的view

    private int deleteWidth;// delete区域的宽度
    private int contentWidth;// content区域的宽度
    private int contentHeight;// delete区域的高度

    private ViewDragHelper viewDragHelper;

    public SwipeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwipeView(Context context) {
        super(context);
        init();
    }

    enum SwipeState {
        Open, Close
    }

    private SwipeState currentState = SwipeState.Close;//当前默认是关闭状态

    private void init() {
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        deleteView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        contentHeight = contentView.getMeasuredHeight();
        deleteWidth = deleteView.getMeasuredWidth();
        contentWidth = contentView.getMeasuredWidth();


        LayoutParams layoutParams = new LayoutParams(deleteWidth, contentHeight);
        deleteView.setLayoutParams(layoutParams);//设置layout
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mPL = getPaddingLeft();
        mPR = getPaddingRight();
        mPT = getPaddingTop();
        mPB = getPaddingBottom();

        contentView.layout(mPL, mPT, contentWidth + mPL, mPT + contentHeight);
        deleteView.layout(getWidth(), mPT, getWidth()
                + deleteWidth, mPT + contentHeight);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean result = viewDragHelper.shouldInterceptTouchEvent(ev);

        //如果当前有打开的，则需要直接拦截，交给onTouch处理
        if (!SwipeManager.getInstance().isShouldSwipe(this)) {
            //先关闭已经打开的layout
            SwipeManager.getInstance().closeCurrentLayout();

            result = true;
        }

        Log.d(TAG, "onInterceptTouchEvent: " + result);

        return result;
    }

    private float downX, downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        //如果当前有打开的，则下面的逻辑不能执行
        if (!SwipeManager.getInstance().isShouldSwipe(this)) {
            requestDisallowInterceptTouchEvent(true);
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //1.获取x和y方向移动的距离
                float moveX = event.getX();
                float moveY = event.getY();
                float deltaX = moveX - downX;//x方向移动的距离
                float deltaY = moveY - downY;//y方向移动的距离
                Log.d(TAG, "onTouchEvent: " + deltaX);

                //更新downX，downY
                downX = moveX;
                downY = moveY;

                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    //表示移动是偏向于水平方向，那么应该SwipeLayout应该处理，请求listView不要拦截
                    requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == contentView || child == deleteView;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return deleteWidth;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                if (left > mPL) left = mPL;
                if (left < -deleteWidth) left = -deleteWidth;
            } else if (child == deleteView) {
                if (left > getWidth()) left = getWidth();
                if (left < (getWidth() - deleteWidth)) left = getWidth() - deleteWidth;
            }
            return left;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top,
                                          int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (changedView == contentView) {
                //手动移动deleteView
                deleteView.layout(deleteView.getLeft() + dx, deleteView.getTop(),
                        deleteView.getRight() + dx, deleteView.getBottom());
            } else if (deleteView == changedView) {
                //手动移动contentView
                contentView.layout(contentView.getLeft() + dx, contentView.getTop(),
                        contentView.getRight() + dx, contentView.getBottom());
            }

            //判断开和关闭的逻辑
            if (contentView.getLeft() == 0 && currentState != SwipeState.Close) {
                //说明应该将state更改为关闭
                currentState = SwipeState.Close;

                //回调接口关闭的方法
                if (listener != null) {
                    listener.onClose(getTag());
                }

                //说明当前的SwipeLayout已经关闭，需要让Manager清空一下
                SwipeManager.getInstance().clearCurrentLayout();
            } else if (contentView.getLeft() == -deleteWidth && currentState != SwipeState.Open) {
                //说明应该将state更改为开
                currentState = SwipeState.Open;

                //回调接口打开的方法
                if (listener != null) {
                    listener.onOpen(getTag());
                }
                //当前的SwipeLayout已经打开，需要让Manager记录一下下
                SwipeManager.getInstance().setSwipeLayout(SwipeView.this);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            Log.d(TAG, "onViewReleased: " + xvel);

            if (xvel > 1000) {//根据速度判断是否打开关闭
                close();
            } else if (xvel < 1000) {
                open();
            } else {
                if (contentView.getLeft() < -deleteWidth / 2) {
                    //应该打开
                    open();
                } else {
                    //应该关闭
                    close();
                }
            }
        }
    };

    /**
     * 打开的方法
     */
    public void open() {
        viewDragHelper.smoothSlideViewTo(contentView, -deleteWidth, contentView.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeView.this);
    }

    public boolean isOpen() {
        return currentState == SwipeState.Open;
    }

    /**
     * 关闭的方法
     */
    public void close() {
        viewDragHelper.smoothSlideViewTo(contentView, mPL, contentView.getTop());
        ViewCompat.postInvalidateOnAnimation(SwipeView.this);
    }

    public void computeScroll() {
        if (viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private OnSwipeStateChangeListener listener;

    public void setOnSwipeStateChangeListener(OnSwipeStateChangeListener listener) {
        this.listener = listener;
    }

    public interface OnSwipeStateChangeListener {
        void onOpen(Object tag);

        void onClose(Object tag);
    }

    /**
     * 为了避免ScrollView横向滑动冲突
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 要求父控件不拦截事件
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (x - downX);
                int deltaY = (int) (y - downY);
                // 如果是上下滑动
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    // 要求父控件拦截事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    // 要求父控件不拦截事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        downX = x;
        downY = y;
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 从视图解除绑定就清除
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: ");
        SwipeManager.getInstance().clearCurrentLayout();//清除一下
    }
}
