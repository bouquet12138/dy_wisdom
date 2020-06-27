package com.example.cityservices.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.example.base_lib.util.FileNameUtil;
import com.example.cityservices.R;
import com.example.common_lib.activity.ImageWatchActivity;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.ImageBean;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.ArrayList;
import java.util.List;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {

    private Context mContext;

    private List<ImageBean> mImageBeans;//图片列表
    private List<String> mImageUrls = new ArrayList<>();//图片链接

    public GoodAdapter(List<ImageBean> imageBeans) {
        mImageBeans = imageBeans;
        if (!CollectionUtils.isEmpty(mImageBeans)) {
            for (ImageBean imageBean : mImageBeans)
                if (imageBean != null)
                    mImageUrls.add(imageBean.getImage_url());//添加图片链接
                else
                    mImageUrls.add("");//添加图片链接
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_good, parent, false);


        int width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(60)) / 2;

        ImageView imageView = view.findViewById(R.id.good_image);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();//布局管理
        layoutParams.height = width;
        imageView.setLayoutParams(layoutParams);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(5), 1f);
        ImageBean bean = mImageBeans.get(position);//图片bean
        if (bean != null) {
            if (FileNameUtil.isGif(bean.getImage_url())) {
                holder.mHintText.setText("gif");
                holder.mHintText.setVisibility(View.VISIBLE);//可见
            } else {
                if (bean.getWidth() > 0 && bean.getHeight() / bean.getWidth() > 2) {
                    holder.mHintText.setText("长图");
                    holder.mHintText.setVisibility(View.VISIBLE);//可见
                } else
                    holder.mHintText.setVisibility(View.GONE);//不可见
            }
            Glide.with(mContext).
                    load(ServerInfo.getImageAddress(bean.getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(holder.mGood_image);//添加图片
            holder.mImage_describe.setText(bean.getImage_describe());//设置图片的描述
        } else {
            Glide.with(mContext).
                    load(R.drawable.image_loading).into(holder.mGood_image);//添加图片
            holder.mImage_describe.setText("");//设置图片的描述
            holder.mHintText.setVisibility(View.GONE);//不可见
        }
        holder.mGood_image.setOnClickListener(view -> ImageWatchActivity.actionStart(mContext, mImageUrls, position));
    }

    @Override
    public int getItemCount() {
        return mImageBeans == null ? 0 : mImageBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;

        ImageView mGood_image;
        TextView mImage_describe;
        TextView mHintText;//提醒文字

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mGood_image = itemView.findViewById(R.id.good_image);
            mImage_describe = itemView.findViewById(R.id.image_describe);
            mHintText = itemView.findViewById(R.id.hintText);//提醒文字
        }
    }
}