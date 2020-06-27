package com.example.common_view.custom_view;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.common_view.R;

public class ShowPasswordView extends LinearLayout {

    private ImageView mImageView;

    private EditText mEditText;

    public ShowPasswordView(Context context) {
        super(context);
        init();
    }

    public ShowPasswordView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 加载布局
        initView();
        initListener();
    }

    /**
     * 初始化view
     */
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_show_pass, this);
        mImageView = findViewById(R.id.imageView);
    }

    public void setEditText(EditText editText) {
        mEditText = editText;
    }

    private static final String TAG = "ShowPasswordView";

    private void initListener() {
        mImageView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mImageView.setImageResource(R.drawable.open);
                if (mEditText != null) {
                    Log.d(TAG, "initListener: 打开 ");
                    mEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEditText.setSelection(mEditText.getText().length());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mImageView.setImageResource(R.drawable.close);
                if (mEditText != null) {
                    mEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEditText.setSelection(mEditText.getText().length());
                }
            }
            return true;
        });
    }


}
