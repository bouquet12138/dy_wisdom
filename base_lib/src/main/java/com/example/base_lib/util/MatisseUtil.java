package com.example.base_lib.util;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;

import com.example.base_lib.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;


/**
 * 开启matisse工具类
 */
public class MatisseUtil {


    public static void selectImage(Activity activity, int maxImgNum, Resources resources, int resultCode) {
        Matisse.from(activity)
                .choose(MimeType.ofImage(), false)
                .
                        capture(true)
                .captureStrategy(new CaptureStrategy(true, "PhotoPicker"))
                .countable(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.example.wisdomconsumption.fileprovider"))
                .maxSelectable(maxImgNum)
                .addFilter(new GifSizeFilter(20, 20, 5 * Filter.K * Filter.K))
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .originalEnable(true)
                .maxOriginalSize(10)
                .forResult(resultCode);
    }

}
