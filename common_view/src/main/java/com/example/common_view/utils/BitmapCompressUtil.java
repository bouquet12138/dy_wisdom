package com.example.common_view.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class BitmapCompressUtil {

    public static final String TAG = "BitmapCompressUtil";

    private BitmapCompressUtil() {
    }

    /**
     * 通过压缩bitmap的尺寸来压缩图片大小 
     *
     * @param bitmap
     * @param targetWidth  目标宽
     * @param targetHeight 目标高
     * @return
     */
    public static Bitmap compressBySize(Bitmap bitmap, int targetWidth, int targetHeight) {
        bitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true);
        return bitmap;
    }

    /**
     * 以k为像素压缩图片
     *
     * @param bitmap
     * @param maxSize
     * @return
     */
    public static Bitmap compressByQuality(Bitmap bitmap, int maxSize) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int quality = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        Log.d(TAG, "compressByQuality: 图片压缩前大小" + byteArrayOutputStream.toByteArray().length + "byte");
        while ((float) byteArrayOutputStream.toByteArray().length / 1024 > maxSize) {
            quality -= 10;
            byteArrayOutputStream.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
            Log.d(TAG, "compressByQuality: " + byteArrayOutputStream.toByteArray().length + "byte");
        }
        Log.d(TAG, "compressByQuality: 图片压缩后大小" + byteArrayOutputStream.toByteArray().length + "byte");
        bitmap = BitmapFactory.decodeByteArray(byteArrayOutputStream.
                toByteArray(), 0, byteArrayOutputStream.toByteArray().length);
        return bitmap;
    }


}
