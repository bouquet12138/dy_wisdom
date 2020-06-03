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
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.BannerBean;
import com.example.wisdomconsumption.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class MyBannerAdapter extends BannerAdapter<BannerBean, MyBannerAdapter.BannerViewHolder> {

    private Context mContext;

    public MyBannerAdapter(List<BannerBean> data, Context context) {
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
    public void onBindView(BannerViewHolder holder, BannerBean data, int position, int size) {
        Glide.with(mContext).load(ServerInfo.getImageAddress(data.getImage().getImage_url())).error(R.drawable.image_error).into(holder.mImageView);//错误图片
        holder.mTitle.setText(data.getImage().getImage_describe());//图片描述
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
