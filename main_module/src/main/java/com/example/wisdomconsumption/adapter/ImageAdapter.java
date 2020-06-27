package com.example.wisdomconsumption.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
public class ImageAdapter extends BannerAdapter<Integer, ImageAdapter.BannerViewHolder> {

    private Context mContext;

    public ImageAdapter(List<Integer> mData, Context context) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mData);
        mContext = context;
    }

    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundColor(Color.WHITE);//白色
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, Integer data, int position, int size) {
        Glide.with(mContext).load(data).into(holder.mImageView);
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.mImageView = view;
        }
    }
}