package com.example.common_lib.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.example.base_lib.adapter.MyFragmentAdapter;
import com.example.base_lib.base.MVPBaseActivity;
import com.example.common_lib.R;
import com.example.common_lib.fragment.PicFragment;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.zhihu.matisse.internal.utils.Platform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImageWatchActivity extends MVPBaseActivity {

    private final int SAVE_IMG = 0;

    private TextView mNumText;//数量textView
    private ImageView mListImg;//列表图片

    private ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<>();

    private int mImgIndex;

    protected MyFragmentAdapter mAdapter;
    private List<String> mImageList;
    private QMUIBottomSheet mBottomSheet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_image_watch);
        initView();
        initData();
        initAdapter();
        initListener();
    }

    /**
     * 初始化view
     */
    private void initView() {

        if (Platform.hasKitKat()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mNumText = findViewById(R.id.numText);
        mListImg = findViewById(R.id.listImg);

        mViewPager = findViewById(R.id.viewPager);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();

        mImageList = (List<String>) bundle.getSerializable("imageList");//图片列表
        mImgIndex = bundle.getInt("imgIndex");

        if (CollectionUtils.isEmpty(mImageList))//为空就销毁
        {
            finish();
            return;
        }
        if (mImageList.size() == 1)
            mNumText.setText("");
        else
            mNumText.setText((mImgIndex + 1) + "/" + mImageList.size());
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        for (int i = 0; i < mImageList.size(); i++) {
            PicFragment picFragment = new PicFragment();
            picFragment.setImagePath(mImageList.get(i));
            mFragments.add(picFragment);//添加碎片
            picFragment.setOnPicClickListener(new PicFragment.OnPicClickListener() {
                @Override
                public void onClick() {
                    finish();//销毁activity
                }

                @Override
                public void onLongClick() {
                    showBottomSheet();//展示底部sheet
                }
            });
        }
        mAdapter = new MyFragmentAdapter(getSupportFragmentManager(), mFragments);//初始化适配器
        mViewPager.setAdapter(mAdapter);//设置适配器

        mViewPager.setCurrentItem(mImgIndex);//设置当前选中
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
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        mListImg.setOnClickListener((v) -> {
            showBottomSheet();//展示底部
        });

    }


    /**
     * 启动activity
     */
    public static void actionStart(Context context, List<String> imageList, int imgIndex) {
        Intent intent = new Intent(context, ImageWatchActivity.class);
        intent.putExtra("imageList", (Serializable) imageList);
        intent.putExtra("imgIndex", imgIndex);
        context.startActivity(intent);
    }

    private void showBottomSheet() {

        if (mBottomSheet == null) {
            QMUIBottomSheet.BottomListSheetBuilder builder =
                    new QMUIBottomSheet.BottomListSheetBuilder(ImageWatchActivity.this);
            builder.setGravityCenter(true)
                    .setAddCancelBtn(true)
                    .setAllowDrag(true)
                    .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {

                        if (position == 0) {
                            saveImagePermission();
                        } else if (position == 1) {
                            if (!NetworkUtils.isAvailable())
                                Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    });
            builder.addItem("保存图片");
            builder.addItem("收藏");
            //展示一下
            mBottomSheet = builder.build();
        }

        mBottomSheet.show();
    }

    private void saveImagePermission() {

        List<String> permissionList = new ArrayList<>();//权限组
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);//读取权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);//写权限

        if (permissionList.size() != 0) {
            // 如果用户已经拒绝了当前权限,shouldShowRequestPermissionRationale返回true，此时我们需要进行必要的解释和处理
            for (String str : permissionList) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, str)) {
                    Toast.makeText(this, "用户拒绝了权限，不能读取本地图片，" +
                            "请到设置界面打开", Toast.LENGTH_SHORT).show();
                    return;//返回出去
                }
            }
            ActivityCompat.requestPermissions(this,
                    permissionList.toArray(new String[permissionList.size()]), SAVE_IMG);//存储图片

        } else {
            saveImage();
        }

    }

    /**
     * 保存图片
     */
    private void saveImage() {
        PicFragment picFragment = (PicFragment) mFragments.get(mViewPager.getCurrentItem());
        picFragment.saveImage();//保存图片
    }

    /**
     * 请求权限的响应
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SAVE_IMG:
                // 从数组中取出返回结果
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "用户拒绝权限，存储图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    saveImage();//从本地选择图片
                }
                break;
        }
    }

}
