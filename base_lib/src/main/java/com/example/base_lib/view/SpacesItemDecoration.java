package com.example.base_lib.view;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {


    private int mSpace;
    private int mOrientation = LinearLayoutManager.VERTICAL;

    public SpacesItemDecoration(int space, int orientation) {
        mSpace = space;
        mOrientation = orientation;
    }

    public SpacesItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL)
            outRect.bottom = mSpace;
        else
            outRect.right = mSpace;
    }
}