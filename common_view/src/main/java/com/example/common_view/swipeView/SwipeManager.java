package com.example.common_view.swipeView;

public class SwipeManager {

    private SwipeManager() {
    }

    private static SwipeManager mInstance = new SwipeManager();

    public static SwipeManager getInstance() {
        return mInstance;
    }

    private SwipeView currentLayout;//用来记录当前打开的SwipeLayout

    public void setSwipeLayout(SwipeView layout) {
        this.currentLayout = layout;
    }

    /**
     * 清空当前所记录的已经打开的layout
     */
    public void clearCurrentLayout() {
        currentLayout = null;
    }

    /**
     * 关闭当前已经打开的SwipeLayout
     */
    public void closeCurrentLayout() {
        if (currentLayout != null) {
            currentLayout.close();
        }
    }

    /**
     * 是否有打开的
     *
     * @return
     */
    public boolean hasOpen() {
        return currentLayout != null;
    }

    /**
     * 判断当前是否应该能够滑动，如果没有打开的，则可以滑动。
     * 如果有打开的，则判断打开的layout和当前按下的layout是否是同一个
     *
     * @return
     */
    public boolean isShouldSwipe(SwipeView swipeLayout) {
       // return currentLayout == null;
       if (currentLayout == null) {
            //说明当前木有打开的layout
            return true;
        } else {
            //说明有打开的layout
            return currentLayout == swipeLayout;
        }
    }
}
