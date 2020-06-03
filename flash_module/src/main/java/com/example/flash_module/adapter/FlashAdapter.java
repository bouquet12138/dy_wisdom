package com.example.flash_module.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.example.base_lib.util.DateUtil;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.java_bean.FlashContentBean;
import com.example.common_lib.java_bean.ImageBean;
import com.example.flash_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FlashAdapter extends RecyclerView.Adapter<FlashAdapter.ViewHolder> {

    private List<FlashBean> mFlashBeans;//快讯bean
    private Context mContext;

    public FlashAdapter(List<FlashBean> flashBeans) {
        mFlashBeans = flashBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flash_item_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mViewGroup.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(0), 1f);
        FlashBean bean = mFlashBeans.get(position);
        if (TextUtils.isEmpty(bean.getTitle()))
            holder.mTitleText.setVisibility(View.GONE);//不可见
        else {
            holder.mTitleText.setVisibility(View.VISIBLE);//可见
            holder.mTitleText.setText(bean.getTitle());//设置标题
        }
        holder.mDateAndReadVolume.setText(DateUtil.formatDate(bean.getInsert_time())
                + " 阅览量：" + bean.getReading_volume());

        List<ImageBean> imageList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(bean.getContent_list()))
            for (FlashContentBean flashContentBean : bean.getContent_list()) {
                if (flashContentBean != null && flashContentBean.getImage() != null) {

                    imageList.add(flashContentBean.getImage());
                }
            }

        setImage(imageList, holder);
        initListener(holder, position, imageList);
    }

    /**
     * 初始化监听
     *
     * @param holder
     * @param position
     * @param imageList
     */
    private void initListener(ViewHolder holder, int position, List<ImageBean> imageList) {
        holder.mViewGroup.setOnClickListener(v -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onClick(mFlashBeans.get(position));
        });
    }

    private static final String TAG = "FlashAdapter";

    private void setImage(List<ImageBean> imageList, ViewHolder holder) {
        if (!CollectionUtils.isEmpty(imageList)) {
            if (imageList.size() == 1) {
                holder.mImage.setVisibility(View.VISIBLE);//可见
                holder.mMyGridLayout.setVisibility(View.GONE);//图片列表不可见

                loadImg(imageList.get(0), holder.mImage);
            } else {
                holder.mImage.setVisibility(View.GONE);//不可见
                holder.mMyGridLayout.setVisibility(View.VISIBLE);//网格图片可见

                loadImg(imageList.get(0), holder.mImage1);
                loadImg(imageList.get(1), holder.mImage2);

                if (imageList.size() == 2) {
                    holder.mImage3.setVisibility(View.GONE);//图片3不可见
                } else {
                    holder.mImage3.setVisibility(View.VISIBLE);//图片3可见
                    loadImg(imageList.get(2), holder.mImage3);
                }
            }
        } else {
            holder.mImage.setVisibility(View.GONE);//图片消失
            holder.mMyGridLayout.setVisibility(View.GONE);//图片列表消失
        }

        if (!CollectionUtils.isEmpty(imageList) && imageList.size() > 3) {
            holder.mImageGroup.setVisibility(View.VISIBLE);//可见
            holder.mImageSizeText.setText((imageList.size() - 3) + "");
        } else
            holder.mImageGroup.setVisibility(View.GONE);//不可见

    }

    /**
     * 加载图片
     */
    private void loadImg(ImageBean imageBean, ImageView imageView) {

        if (imageBean != null) {
            Glide.with(mContext).load(ServerInfo.getImageAddress(imageBean.getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).
                    into(imageView);//设置图片
        } else {
            Glide.with(mContext).load(R.drawable.image_loading).error(R.drawable.image_error).
                    into(imageView);//设置图片
        }
    }

    public interface OnItemClickListener {
        void onClick(FlashBean flashBean);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mFlashBeans == null ? 0 : mFlashBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mViewGroup;
        TextView mTitleText;
        ImageView mImage;
        ViewGroup mMyGridLayout;
        ImageView mImage1;
        ImageView mImage2;
        ImageView mImage3;
        TextView mDateAndReadVolume;

        ViewGroup mImageGroup;
        TextView mImageSizeText;

        public ViewHolder(View itemView) {
            super(itemView);
            mViewGroup = itemView.findViewById(R.id.viewGroup);
            mTitleText = itemView.findViewById(R.id.describeText);
            mImage = itemView.findViewById(R.id.image);
            mMyGridLayout = itemView.findViewById(R.id.myGridLayout);
            mImage1 = itemView.findViewById(R.id.image1);
            mImage2 = itemView.findViewById(R.id.image2);
            mImage3 = itemView.findViewById(R.id.image3);
            mDateAndReadVolume = itemView.findViewById(R.id.dateAndReadVolume);

            mImageGroup = itemView.findViewById(R.id.imageGroup);
            mImageSizeText = itemView.findViewById(R.id.imageSizeText);
        }
    }
}