package com.example.cityservices.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.example.cityservices.R;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.StoreBean;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private List<StoreBean> mStoreBeans;
    private Context mContext;

    public StoreAdapter(List<StoreBean> storeBeans) {
        mStoreBeans = storeBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_store, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(0), 1f);

        StoreBean bean = mStoreBeans.get(position);
        if (bean.getHead_img() == null) {
            holder.mStoreImg.setImageResource(R.drawable.image_loading);
        } else
            Glide.with(mContext).
                    load(ServerInfo.getImageAddress(bean.getHead_img().getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(holder.mStoreImg);
        holder.mStoreText.setText(bean.getStore_name());//商店名称
        holder.mRoot.setOnClickListener(view -> {

            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(bean);//传递出去
        });
    }

    @Override
    public int getItemCount() {
        return mStoreBeans == null ? 0 : mStoreBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;
        ImageView mStoreImg;
        TextView mStoreText;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mStoreImg = itemView.findViewById(R.id.storeImg);
            mStoreText = itemView.findViewById(R.id.storeText);
        }
    }

    /**
     * item点击
     */
    public interface OnItemClickListener {
        void onItemClick(StoreBean storeBean);
    }

    private OnItemClickListener mOnItemClickListener;

    /**
     * 设置item点击监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}