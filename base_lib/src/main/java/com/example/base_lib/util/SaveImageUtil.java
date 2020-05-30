package com.example.base_lib.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.PathUtils;
import com.example.base_lib.base.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SaveImageUtil {

    private static final String TAG = "SaveImageUtil";

    //保存图片
    public static boolean saveImage(String dirName, String fileName, Bitmap bitmap) {

        Log.d(TAG, "saveImage: ");
        String state = Environment.getExternalStorageState();

        if (!state.equals(Environment.MEDIA_MOUNTED))
            return false;


        String SAVE_PATH = PathUtils.getExternalStoragePath();
        File dir = new File(SAVE_PATH, dirName);
        if (!dir.exists()) {
            dir.mkdir();//创建该文件夹
        }

        try {
            File file = new File(dir, fileName);
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            //保存图片后发送广播通知更新数据库
            sendBroadCast(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }

    /**
     * 该图片是否存在
     *
     * @param dirName
     * @param fileName
     * @return
     */
    public static boolean isExist(String dirName, String fileName) {

        String filePath = getImageFilePath(dirName, fileName);

        if (TextUtils.isEmpty(filePath))
            return false;

        File file = new File(filePath);

        if (file.exists()) {

            Bitmap bitmap = BitmapFactory.decodeFile(filePath);

            Log.d(TAG, "isExist: " + bitmap.getWidth());

            return true;
        }

        return false;
    }

    public static String getImageFilePath(String dirName, String fileName) {
        String state = Environment.getExternalStorageState();

        if (!state.equals(Environment.MEDIA_MOUNTED))
            return "";
        // Environment.getExternalStorageDirectory().getAbsolutePath();
        String SAVE_PATH = PathUtils.getExternalStoragePath();
        File dir = new File(SAVE_PATH, dirName);
        if (!dir.exists()) {
            dir.mkdir();//创建该文件夹
            return "";
        }
        File file = new File(dir, fileName);
        return file.getPath();
    }

    public static boolean copyFile(String oldPath, String newPath) {

        File source = new File(oldPath);
        File dest = new File(newPath);

        InputStream input = null;
        OutputStream output = null;

        if (!source.exists())
            return false;

        if (TextUtils.isEmpty(newPath))
            return false;

        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            sendBroadCast(dest);
            return true;
        } catch (IOException e) {

        } finally {
            try {
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 保存图片后发送广播通知更新数据库
     *
     * @param file
     */
    public static void sendBroadCast(File file) {
        Uri uri = Uri.fromFile(file);
        MyApplication.getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
    }

}
