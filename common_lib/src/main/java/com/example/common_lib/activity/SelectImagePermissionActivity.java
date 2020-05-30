package com.example.common_lib.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.base_lib.util.MatisseUtil;
import com.example.common_lib.base.AppMvpBaseActivity;
import com.zhihu.matisse.Matisse;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public abstract class SelectImagePermissionActivity extends AppMvpBaseActivity implements EasyPermissions.PermissionCallbacks {

    protected final int PERMISSIONS_READ_CONTACTS = 0;//读取本地图片
    protected final int SELECT_PICTURE = 1;//选择图片
    protected int mMaxImgNum = 1;

    /**
     * 从相册选择图片 权限
     */
    protected void selectImageAuthor() {

        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            selectImage();// 已经申请过权限 选择图片
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    "打开相册",
                    SELECT_PICTURE,
                    perms);
        }
    }

    /**
     * 选择相片
     */
    protected void selectImage() {
        MatisseUtil.selectImage(this, mMaxImgNum, getResources(), SELECT_PICTURE);
    }

    /**
     * 请求权限的响应
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            List<Uri> uriList = Matisse.obtainResult(data);
            List<String> stringList = Matisse.obtainPathResult(data);
            addImage(uriList, stringList);
        }
    }

    /**
     * 添加图片
     *
     * @param uriList
     */
    protected abstract void addImage(List<Uri> uriList, List<String> stringList);

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case PERMISSIONS_READ_CONTACTS:
                selectImage();//从本地选择图片
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        switch (requestCode) {
            case PERMISSIONS_READ_CONTACTS:
                // 从数组中取出返回结果
                showErrorHint("拒绝了权限无法打开相册");
                break;
        }
    }
}
