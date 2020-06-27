package com.example.base_lib.util;

import android.text.TextUtils;

public class FileNameUtil {

    public static String getFilSuffix(String fileName) {
        if (TextUtils.isEmpty(fileName))
            return "";
        else if (fileName.lastIndexOf(".") != -1)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        return "";
    }

    public static boolean isGif(String imgName) {
        if (getFilSuffix(imgName).equalsIgnoreCase("gif"))
            return true;
        return false;
    }

}
