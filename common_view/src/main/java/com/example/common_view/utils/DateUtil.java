package com.example.common_view.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static final String TAG = "DateUtil";

    /**
     * 返回某年某月有几天
     *
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(year + "-" + month + "-" + "01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null)
            return 0;//0天
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 实现给定某日期，判断是星期几
     *
     * @return
     */
    public static int getWeekday(int year, int month) {//必须yyyy-MM-dd
        Calendar calendar = Calendar.getInstance();//获取一个实例
        calendar.set(year, month - 1, 1);//这个month 要减1
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;//周日 对应1 周一对应2
        Log.d(TAG, "getWeekday: week " + week);
        return week;
    }


    private static int mCurrentYear;//当前年
    private static int mCurrentMonth;//当前月
    private static int mCurrentDay;//当前日

    static {
        Calendar calendar = Calendar.getInstance();//得到日期实例
        mCurrentYear = calendar.get(Calendar.YEAR);//年
        mCurrentMonth = calendar.get(Calendar.MONTH) + 1;//月
        mCurrentDay = calendar.get(Calendar.DAY_OF_MONTH);//得到日
    }


    //得到日期
    public static int getDays(int yearInt, int monthInt) {

        //如果年份等于当前年份，月份等于当前月份返回当前天数
        if (yearInt == mCurrentYear && monthInt == mCurrentMonth)
            return mCurrentDay;

        return getCommonDays(yearInt, monthInt);
    }

    //得到日期
    public static int getCommonDays(int yearInt, int monthInt) {

        switch (monthInt) {

            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if ((yearInt % 4 == 0 && yearInt % 100 != 0) || yearInt % 400 == 0)
                    return 29;
                else return 28;
        }
        return 30;
    }


}
