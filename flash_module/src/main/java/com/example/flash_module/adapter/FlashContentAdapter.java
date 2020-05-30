package com.example.flash_module.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.example.common_lib.activity.ImageWatchActivity;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.FlashContentBean;
import com.example.common_lib.java_bean.ImageBean;
import com.example.flash_module.R;

import java.util.ArrayList;
import java.util.List;


public class FlashContentAdapter extends RecyclerView.Adapter<FlashContentAdapter.ViewHolder> {

    private List<FlashContentBean> mFlashContentBeans;
    private Context mContext;
    private List<String> mImageList = new ArrayList<>();
    private List<Integer> mPosition = new ArrayList<>();//位置
    int num = 0;

    public FlashContentAdapter(List<FlashContentBean> flashContentBeans) {
        mFlashContentBeans = flashContentBeans;

        if (!CollectionUtils.isEmpty(mFlashContentBeans)) {
            for (FlashContentBean flashContentBean : mFlashContentBeans) {
                if (flashContentBean.getImage() != null) {
                    mPosition.add(num);
                    num++;
                    mImageList.add(flashContentBean.getImage().getImage_url());
                } else
                    mPosition.add(0);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flash_item_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FlashContentBean bean = mFlashContentBeans.get(position);

        if (TextUtils.isEmpty(bean.getContent()))
            holder.mContent.setVisibility(View.GONE);
        else
            holder.mContent.setVisibility(View.VISIBLE);

        holder.mDescribeText.setVisibility(View.GONE);//先不可见

        holder.mContent.setText(bean.getContent());
        //   holder.mDescribeText.setText(bean.getDescriptions());
        //Glide.with(mContext).load(bean.getImages()).into(holder.mImageView);//加载图片

        setImageLayout(holder, bean.getImage());//设置图片

        holder.mImageView.setOnClickListener(view -> ImageWatchActivity.actionStart(mContext, mImageList, mPosition.get(position)));

    }

    private void setImageLayout(ViewHolder holder, ImageBean imageBean) {

        if (imageBean != null) {
            int imageWidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(30);
            int imageHeight = (int) (imageWidth * imageBean.getHeight() / (float) imageBean.getWidth());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
            layoutParams.topMargin = SizeUtils.dp2px(10);
            holder.mImageView.setLayoutParams(layoutParams);//设置布局

            Glide.with(mContext).load(ServerInfo.getImageAddress(imageBean.getImage_url())).placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(holder.mImageView);
        } else {
            holder.mImageView.setVisibility(View.GONE);//没有图片就不可见呗
        }
    }

    private static final String TAG = "FlashContentAdapter";

    @Override
    public int getItemCount() {
        return mFlashContentBeans == null ? 0 : mFlashContentBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mContent;
        ImageView mImageView;
        TextView mDescribeText;

        public ViewHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.content);
            mImageView = itemView.findViewById(R.id.imageView);
            mDescribeText = itemView.findViewById(R.id.describeText);
        }
    }
}