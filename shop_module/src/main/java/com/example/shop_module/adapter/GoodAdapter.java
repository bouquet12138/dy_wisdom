package com.example.shop_module.adapter;

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
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.GoodBean;
import com.example.common_lib.java_bean.ImageBean;
import com.example.shop_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;


public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {

    private List<GoodBean> mGoodBeans;
    private Context mContext;

    public GoodAdapter(List<GoodBean> goodBeans) {
        mGoodBeans = goodBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item_good, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodBean goodBean = mGoodBeans.get(position);
        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(5), 0f);
        holder.mRoot.setOnClickListener(v -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(goodBean);
        });
        if (!CollectionUtils.isEmpty(goodBean.getImage_list())) {
            holder.mGood_image.setVisibility(View.VISIBLE);
            ImageBean imageBean = goodBean.getImage_list().get(0);//得到第一个
            Glide.with(mContext).
                    load(ServerInfo.getImageAddress(imageBean.getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(holder.mGood_image);//添加图片

            if (FileNameUtil.isGif(imageBean.getImage_name())) {
                holder.mHintText.setText("gif");
                holder.mHintText.setVisibility(View.VISIBLE);//可见
            } else {
                if (imageBean.getWidth() != 0 && imageBean.getHeight() / (float) imageBean.getWidth() >= 1.5f) {
                    holder.mHintText.setText("长图");
                    holder.mHintText.setVisibility(View.VISIBLE);//可见
                } else {
                    holder.mHintText.setVisibility(View.GONE);//不可见
                }
            }
            setImageLayout(holder, imageBean);//设置一下图片布局
        } else {
            holder.mHintText.setVisibility(View.GONE);
            holder.mGood_image.setVisibility(View.GONE);//不可见
        }


        holder.mTitleText.setText(goodBean.getTitle());
        holder.mIntroduceText.setText(goodBean.getIntroduce());//描述
        holder.mMoney.setText("￥" + goodBean.getPrice());//价格
    }

    /**
     * 设置图片布局
     */
    private void setImageLayout(ViewHolder viewHolder, ImageBean imageBean) {


        int width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(40)) / 2;

        int height = (int) Math.max(width * (imageBean.getHeight() / (float) imageBean.getWidth()), 2);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.mGood_image.getLayoutParams();//布局管理
        layoutParams.height = height;
        viewHolder.mGood_image.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return mGoodBeans == null ? 0 : mGoodBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;
        ImageView mGood_image;
        TextView mHintText;
        TextView mTitleText;
        TextView mIntroduceText;
        TextView mMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mGood_image = itemView.findViewById(R.id.good_image);
            mHintText = itemView.findViewById(R.id.hintText);
            mTitleText = itemView.findViewById(R.id.titleText);
            mIntroduceText = itemView.findViewById(R.id.introduceText);
            mMoney = itemView.findViewById(R.id.money);//价格
        }
    }

    public interface OnItemClickListener {
        void onItemClick(GoodBean goodBean);
    }

    private OnItemClickListener mOnItemClickListener;

    /**
     * 设置接口
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}