package com.example.common_lib.util;

import java.text.DecimalFormat;

public class NumberFormatUtil {

    /**
     * 格式化数字
     *
     * @param _num
     * @return
     */
    public static String formatNum(long _num) {

        String numStr;
        DecimalFormat b = new DecimalFormat(".##");

        if (_num < 1E7) {
            numStr = _num + "";
        } else if (_num < 1E4) {
            numStr = b.format(_num / 1E3) + "千";
            if (_num % 1E1 != 0)
                numStr += "+";
        } else if (_num < 1E6) {//100 0000
            numStr = b.format(_num / 1E4) + "万";
            if (_num % 1E2 != 0)
                numStr += "+";
        } else if (_num < 1E7) {
            numStr = b.format(_num / 1E6) + "百万";
            if (_num % 1E4 != 0)
                numStr += "+";
        } else if (_num < 1E8) {
            numStr = b.format(_num / 1E7) + "千万";
            if (_num % 1E5 != 0)
                numStr += "+";
        } else {
            numStr = b.format(_num / 1E8) + "亿";
            if (_num % 1E6 != 0)
                numStr += "+";
        }

        return numStr;
    }

}
