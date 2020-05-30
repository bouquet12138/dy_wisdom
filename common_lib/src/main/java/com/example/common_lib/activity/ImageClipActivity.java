package com.example.common_lib.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.common_lib.R;
import com.example.common_view.custom_view.ClipView;
import com.example.common_view.custom_view.ZoomImageView;

public class ImageClipActivity extends AppCompatActivity {

    private ClipView mClipView;
    private ZoomImageView mZoomImage;
    private ImageView mBackButton;
    private TextView mConfirmText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_image_clip);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        mClipView = findViewById(R.id.clipView);
        mZoomImage = findViewById(R.id.zoomImage);
        mBackButton = findViewById(R.id.backButton);
        mConfirmText = findViewById(R.id.confirmText);
    }

    private void initData() {
        Intent intent = getIntent();
        String filePath = intent.getStringExtra("filePath");
        mZoomImage.setImageBitmap(BitmapFactory.decodeFile(filePath));//设置一下图片
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mBackButton.setOnClickListener((v) -> {
            finish();//销毁
        });
        mConfirmText.setOnClickListener((v) -> {
            String filePath = mZoomImage.save(mClipView.getCropPath());
            Intent intent = new Intent();
            intent.putExtra("filePath", filePath);
            setResult(RESULT_OK, intent);
            finish();//销毁
        });
        mClipView.setOnDrawListener(() -> mZoomImage.setLimit(mClipView.getRect()));

    }


}
