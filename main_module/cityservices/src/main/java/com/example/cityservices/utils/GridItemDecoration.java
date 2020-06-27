package com.example.cityservices.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {


    private int space;

    public GridItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        //注释这两行是为了上下间距相同
//        if(parent.getChildAdapterPosition(view)==0){
        outRect.top = space;
//        }
    }


}
