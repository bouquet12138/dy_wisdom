package com.example.common_view.custom_view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.common_view.R;


public class SelectImg extends androidx.appcompat.widget.AppCompatImageView {

    private boolean mSelect = false;//是否选中

    public SelectImg(@NonNull Context context) {
        super(context);
        init();
    }

    public SelectImg(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        setImageResource(R.drawable.circle_un_select);
        setOnClickListener(view -> invertSelection());
    }

    public boolean isSelect() {
        return mSelect;
    }

    public void setSelect(boolean select) {
        mSelect = select;
        if (mSelect) {
            setImageResource(R.drawable.circle_select);
        } else {
            setImageResource(R.drawable.circle_un_select);
        }
    }

    /**
     * 反选
     */
    private void invertSelection() {
        setSelect(!mSelect);
    }

}