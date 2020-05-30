package com.example.common_view.custom_view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.example.common_view.R;
import com.example.common_view.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;

public class BottomPopFutureDate extends Dialog {


    private List<String> mYearList, mMonthList, mDayList, mHourList, mMinuteList;//存放年月日时分的集合

    private TextView mCancelText;//取消
    private TextView mCurrentDate;//显示当前年月的文本
    private TextView mConfirmText;//确定文本

    private DatePickerView mYearDatePicker;//年
    private DatePickerView mMonthDatePicker;//月
    private DatePickerView mDayDatePicker;//日选择器

    private DatePickerView mHourDatePicker;//日选择器
    private DatePickerView mMinuteDatePicker;//日选择器


    private Calendar mCalendar;
    private int mCurrentYear, mCurrentMonth, mCurrentDay;

    public BottomPopFutureDate(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);

        setContentView(R.layout.layout_future_date);//设置一下内容

        initView();
        initData();

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = 0.9f;//透明度为0.9
        layoutParams.gravity = Gravity.BOTTOM;//设置居低并且宽度全屏
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setAttributes(layoutParams);

        initListener();

    }

    /**
     * 初始化view
     */
    private void initView() {
        mCancelText = findViewById(R.id.cancelText);
        mCurrentDate = findViewById(R.id.currentDate);
        mConfirmText = findViewById(R.id.confirmText);
        mYearDatePicker = findViewById(R.id.yearDatePicker);
        mMonthDatePicker = findViewById(R.id.monthDatePicker);
        mDayDatePicker = findViewById(R.id.dayDatePicker);

        mHourDatePicker = findViewById(R.id.hourDatePicker);//时
        mMinuteDatePicker = findViewById(R.id.minutePicker);//分

    }

    /**
     * 初始化数据
     */
    private void initData() {

        mCalendar = Calendar.getInstance();//得到日历示例
        mCurrentYear = mCalendar.get(Calendar.YEAR);//结束年
        mCurrentMonth = mCalendar.get(Calendar.MONTH) + 1;//当前月
        mCurrentDay = mCalendar.get(Calendar.DAY_OF_MONTH);//当前日

        mYearList = new ArrayList<>();//年
        mMonthList = new ArrayList<>();//月
        mDayList = new ArrayList<>();//日
        mHourList = new ArrayList<>();
        mMinuteList = new ArrayList<>();

        for (int i = mCurrentYear; i <= mCurrentYear + 100; i++) {
            mYearList.add(i + "");
        }

        mYearDatePicker.setDateList(mYearList);

        for (int i = 1; i <= 12; i++) {
            if (i < 10)
                mMonthList.add("0" + i);
            else
                mMonthList.add(i + "");//一年12个月
        }
        mMonthDatePicker.setDateList(mMonthList);

        for (int i = 1; i <= 31; i++) {
            if (i < 10)
                mDayList.add("0" + i);
            else
                mDayList.add(i + "");
        }
        mDayDatePicker.setDateList(mDayList);//设置一下日期picker的数据

        for (int i = 0; i <= 23; i++) {
            if (i < 10)
                mHourList.add("0" + i);
            else
                mHourList.add(i + "");
        }

        mHourDatePicker.setDateList(mHourList);//设置时间

        for (int i = 0; i <= 59; i++) {
            if (i < 10)
                mMinuteList.add("0" + i);
            else
                mMinuteList.add(i + "");
        }
        mMinuteDatePicker.setDateList(mMinuteList);//设置分钟
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        //设置确定按钮的监听
        mConfirmText.setOnClickListener((v) -> {

                    StringBuilder currentDate = new StringBuilder();
                    currentDate.append(mYearList.get(mYearDatePicker.getCurrentSelected()));
                    currentDate.append("-");
                    currentDate.append(mMonthList.get(mMonthDatePicker.getCurrentSelected()));
                    currentDate.append("-");
                    currentDate.append(mDayList.get(mDayDatePicker.getCurrentSelected()));

                    currentDate.append(" ");
                    currentDate.append(mHourList.get(mHourDatePicker.getCurrentSelected()));
                    currentDate.append(":");
                    currentDate.append(mMinuteList.get(mMinuteDatePicker.getCurrentSelected()));

                    if (mOnConfirmListener != null)//确定监听不为空
                        mOnConfirmListener.onConfirm(currentDate.toString());//将当前日期返回出去
                }
        );

        mCancelText.setOnClickListener((view) -> {
                    if (mOnCancelListener == null) {
                        dismiss();//让弹窗消失
                    } else {
                        mOnCancelListener.onCancel();//随便拿去
                    }
                }
        );

        mYearDatePicker.setOnSelectChangeListener((currentSelectedIndex) -> {

            int year = mYearDatePicker.getCurrentSelected() + mCurrentYear;

            int month = mMonthDatePicker.getCurrentSelected() + 1;

            int days = DateUtil.getCommonDays(year, month);//根据年月得到天数

            if (mDayList.size() == days) ;//相等啥也不做
            else {
                if (days <= mDayDatePicker.getCurrentSelected()) {
                    mDayDatePicker.setCurrentSelected(days - 1);//选最后一个
                }
                changeListSize(mDayList, days);
                mDayDatePicker.setDateList(mDayList);
            }

        });

        mMonthDatePicker.setOnSelectChangeListener((currentSelectIndex) -> {

            int year = mYearDatePicker.getCurrentSelected() + mCurrentYear;
            int moth = mMonthDatePicker.getCurrentSelected() + 1;

            int days = DateUtil.getCommonDays(year, moth);//根据年月得到天数

            if (mDayList.size() == days) ;//相等啥也不做
            else {
                if (days <= mDayDatePicker.getCurrentSelected()) {
                    mDayDatePicker.setCurrentSelected(days - 1);//选最后一个
                }
                changeListSize(mDayList, days);
                mDayDatePicker.setDateList(mDayList);
            }

        });

        mYearDatePicker.setCurrentSelected(0);//
        mMonthDatePicker.setCurrentSelected(mCurrentMonth - 1);//设置一下当前选中
        mDayDatePicker.setCurrentSelected(mCurrentDay - 1);
    }

    //点击确定后的监听
    public interface OnConfirmListener {
        void onConfirm(String currentDate);//当用户点击了确定之后
    }

    private BottomPopFutureDate.OnConfirmListener mOnConfirmListener;

    /**
     * 设置确定监听
     *
     * @param onConfirmListener
     */
    public void setOnConfirmListener(OnConfirmListener onConfirmListener) {
        mOnConfirmListener = onConfirmListener;
    }

    //点击取消按钮后的监听
    public interface OnCancelListener {
        void onCancel();
    }

    public BottomPopDateSelect.OnCancelListener mOnCancelListener;

    /**
     * 设置取消监听
     *
     * @param onCancelListener
     */
    public void setOnCancelListener(BottomPopDateSelect.OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
    }

    /**
     * 改变list集合的长度
     *
     * @param dateList
     * @param newLength
     */
    private void changeListSize(List<String> dateList, int newLength) {

        if (dateList.size() == newLength)//如果长度相等直接返回
            return;

        if (dateList.size() > newLength) {
            for (int i = dateList.size() - 1; i >= newLength; i--)//移除多余数据
                dateList.remove(i);
        } else {
            for (int i = dateList.size() + 1; i <= newLength; i++) {
                if (i < 10)
                    dateList.add("0" + i);
                else
                    dateList.add(i + "");
            }
        }
    }

}
