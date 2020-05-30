package com.example.wisdomconsumption.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.common_lib.java_bean.ImageBean;
import com.example.wisdomconsumption.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * 自定义布局，下面是常见的图片样式，更多实现可以看demo，可以自己随意发挥
 */
public class ImageAdapter extends BannerAdapter<ImageBean, ImageAdapter.BannerViewHolder> {

    private Context mContext;

    public ImageAdapter(List<ImageBean> data, Context context) {
        super(data);
        mContext = context;
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_image, parent, false);
        BannerViewHolder viewHolder = new BannerViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindView(BannerViewHolder holder, ImageBean data, int position, int size) {
        Glide.with(mContext).load(data.getImage_id()).error(R.drawable.image_error).into(holder.mImageView);//错误图片
        holder.mTitle.setText(data.getImage_describe());
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;//图片
        TextView mTitle;//标题

        public BannerViewHolder(@NonNull View view) {
            super(view);
            mImageView = itemView.findViewById(R.id.imageView);
            mTitle = itemView.findViewById(R.id.title);
        }
    }
}