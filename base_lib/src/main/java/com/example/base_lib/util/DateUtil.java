package com.example.base_lib.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 格式化字符串
     *
     * @return
     */
    public static String formatDate(String date) {
        return formatDate(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 格式化字符串
     *
     * @param dateText
     * @return
     */
    public static String formatDate(String dateText, String pattern) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = new Date();

        if (TextUtils.isEmpty(dateText))
            return "";
        try {

            Date date2 = simpleDateFormat.parse(dateText);

            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);

            int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
            int subMonth = cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
            int subDay = cal1.get(Calendar.DAY_OF_MONTH) - cal2.get(Calendar.DAY_OF_MONTH);

            String date = "";

            if (subYear == 0 && subMonth == 0 && Math.abs(subDay) <= 2) {

                if (subDay == 0)
                    date += "今天";
                else if (subDay == 1)
                    date += "昨天";
                else if (subDay == -1)
                    date += "明天";
                else if (subDay == -2)
                    date += "后天";
                else
                    date += "前天";
            }

            if (subYear != 0) {
                date += cal2.get(Calendar.YEAR) + "年";
                if ((cal2.get(Calendar.MONTH) + 1) < 10)
                    date += "0";
                date += (cal2.get(Calendar.MONTH) + 1) + "月";

                if (cal2.get(Calendar.DAY_OF_MONTH) < 10)
                    date += "0";
                date += cal2.get(Calendar.DAY_OF_MONTH) + "日";
            }
            if (subYear == 0 && (subMonth != 0 || subDay > 3)) {
                if ((cal2.get(Calendar.MONTH) + 1) < 10)
                    date += "0";
                date += (cal2.get(Calendar.MONTH) + 1) + "月";

                if (cal2.get(Calendar.DAY_OF_MONTH) < 10)
                    date += "0";
                date += cal2.get(Calendar.DAY_OF_MONTH) + "日";
            }

            if (cal2.get(Calendar.HOUR_OF_DAY) < 10)
                date += "0";
            date += cal2.get(Calendar.HOUR_OF_DAY) + ":";
            if (cal2.get(Calendar.MINUTE) < 10)
                date += "0";
            return date + cal2.get(Calendar.MINUTE);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateText;
    }

    /**
     * 日期是否过期
     *
     * @param date 日期
     */
    public static boolean isOverDue(String date) {
        return isOverDue(date, "yyyy-MM-dd HH:mm");
    }

    /**
     * 日期是否过期
     *
     * @param date    日期
     * @param pattern 日期格式
     */
    public static boolean isOverDue(String date, String pattern) {

        //"yyyy-MM-dd HH:mm"
        if (!TextUtils.isEmpty(date)) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date1 = new Date();
            try {
                Date date2 = simpleDateFormat.parse(date);
                if (date1.compareTo(date2) > 0)
                    return true;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 比较时间
     * @param dateStr1
     * @param dateStr2
     * @param pattern
     * @return
     */
    public static int completeDate(String dateStr1, String dateStr2, String pattern) {

        //"yyyy-MM-dd HH:mm"
        if (!TextUtils.isEmpty(dateStr1) && !TextUtils.isEmpty(dateStr2)) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                Date date1 = simpleDateFormat.parse(dateStr1);
                Date date2 = simpleDateFormat.parse(dateStr2);
                return date1.compareTo(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return 0;
    }


}
