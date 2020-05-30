package com.example.common_view.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.common_view.bean.DateBean;
import com.example.common_view.bean.MyCalendarProperty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


public class MyCalendarView extends LinearLayout {

    private MyCalendarProperty mProperty = new MyCalendarProperty();//属性

    private ViewPager mViewPager;
    private int PAGER_SIZE = 2388;//2400页

    private int mStartYear = 1901;//开始的年

    private static int mCurrentYear, mCurrentMonth, mCurrentDay;//当前年 月 日
    private static int mSelectedYear, mSelectedMonth, mSelectedDay;//选中的 年 月 日

    private int mCurrentPosition;//当亲位置
    private CalendarAdapter mAdapter;

    private static List<DateBean> sSignList = new ArrayList<>();//签到的日期

    public MyCalendarView(Context context) {
        super(context);
        mProperty.init(context);//初始化一下属性
        init();
    }

    public MyCalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mProperty.init(context, attrs);//初始化一下属性
        init();
    }

    private void init() {
        initView();
        initData();
        initListener();
    }


    /**
     * 初始化View
     */
    private void initView() {
        mViewPager = new ViewPager(getContext());//viewPager
        addView(mViewPager);//添加viewPager
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;//记录一下位置
                int year = mStartYear + position / 12;
                int month = position % 12 + 1;
                if (mOnSelectListener != null)
                    mOnSelectListener.change(year, month);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Calendar calendar = Calendar.getInstance();
        mCurrentYear = calendar.get(Calendar.YEAR);
        mCurrentMonth = calendar.get(Calendar.MONTH) + 1;
        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH);

        mCurrentPosition = getPosition(mCurrentYear, mCurrentMonth);

        mAdapter = new CalendarAdapter();
        mViewPager.setAdapter(mAdapter);//设置适配器
        mViewPager.setCurrentItem(mCurrentPosition);//设置当前item
    }

    private int getPosition(int year, int month) {
        return (year - mStartYear) * 12 + month - 1;
    }


    private class CalendarAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return PAGER_SIZE;//页的尺寸
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            MyCalendar calendar = getContent(position);
            container.addView(calendar);//添加进去
            return calendar;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            // mDestroyViews.add((MyCalendar) object);//添加进去
            container.removeView((View) object);//将view移出去
        }
    }

    /**
     * 得到要显示的日历内容，包括签到的日期。
     *
     * @param position
     * @return
     */
    private MyCalendar getContent(int position) {
        MyCalendar myCalendar;

        int year = mStartYear + position / 12;
        int month = position % 12 + 1;
        myCalendar = new MyCalendar(getContext());//日历
        myCalendar.setDate(year, month, mProperty);
        //myCalendar.setBackgroundResource(R.drawable.shape_date_back);//设置背景图

        return myCalendar;//返回calendar
    }

    public static int getCurrentYear() {
        return mCurrentYear;
    }

    public static int getCurrentMonth() {
        return mCurrentMonth;
    }

    public static int getCurrentDay() {
        return mCurrentDay;
    }

    /**
     * 选择监听
     */
    public interface OnSelectListener {
        void change(int year, int month);

        void onSelected(int year, int month, int day);//选中的年月日
    }

    private static OnSelectListener mOnSelectListener;

    /**
     * 选择监听
     *
     * @param onSelectListener
     */
    public void setSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    /**
     * 设置当前年月
     *
     * @param year
     * @param month
     */
    public void setCurrentPosition(int year, int month) {
        mCurrentPosition = getPosition(year, month);
        mViewPager.setCurrentItem(mCurrentPosition, false);
    }

    /**
     * 回到今天
     */
    public void today() {
        setCurrentPosition(mCurrentYear, mCurrentMonth);
    }

    /**
     * 向左移动
     */
    public void left() {
        mCurrentPosition--;
        if (mCurrentPosition < 0)
            mCurrentPosition = 0;
        mViewPager.setCurrentItem(mCurrentPosition, false);//设置当前位置
    }

    /**
     * 向右移动
     */
    public void right() {
        mCurrentPosition++;
        if (mCurrentPosition >= PAGER_SIZE)
            mCurrentPosition = PAGER_SIZE - 1;
        mViewPager.setCurrentItem(mCurrentPosition, false);//设置当前位置
    }

    /**
     * 是否选中
     */
    public static boolean isSelected(int year, int month, int day) {
        return (mSelectedYear == year && mSelectedMonth == month && mSelectedDay == day);
    }

    /**
     * 设置当前选中的年月日
     */
    public static void setCurrentSelect(int year, int month, int day) {
        mSelectedYear = year;
        mSelectedMonth = month;
        mSelectedDay = day;

        if (mOnSelectListener != null)
            mOnSelectListener.onSelected(year, month, day);//设置选中
    }


    /**
     * 设置签到列表
     *
     * @param signList
     */
    public static void setSignList(List<DateBean> signList) {
        sSignList = signList;
    }

    /**
     * 是否签到
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static boolean isSign(int year, int month, int day) {

        if (sSignList != null) {
            DateBean dateBean = new DateBean(year, month, day);
            return sSignList.contains(dateBean);
        }
        return false;
    }

}
