package com.example.shop_module.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.example.common_lib.contract.ARouterContract;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.GoodBean;
import com.example.common_lib.java_bean.ImageBean;
import com.example.common_lib.java_bean.OrderRecordBean;
import com.example.shop_module.R;
import com.example.shop_module.activity.GoodDetailActivity;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderRecordBean> mOrderRecordBeans;
    private Context mContext;

    public OrderAdapter(List<OrderRecordBean> orderRecordBeans) {
        mOrderRecordBeans = orderRecordBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.shop_item_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    private static final String TAG = "OrderAdapter";

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OrderRecordBean bean = mOrderRecordBeans.get(position);
        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(10), SizeUtils.dp2px(5), 0f);
        GoodBean goodBean = bean.getGood_bean();
        holder.mShopName.setText(goodBean.getGood_type());

        holder.mShopName.setOnClickListener(v -> ARouter.getInstance().build(ARouterContract.SHOP_SHOP).
                withString("shopType", goodBean.getGood_type())
                .navigation());//跳到指定商城

        holder.mRoot.setOnClickListener(v -> {

            if (GoodBean.SOLD_OUT.equals(goodBean.getStatus())) {
                Toast.makeText(mContext, "商品已下架", Toast.LENGTH_SHORT).show();
            } else {
                GoodDetailActivity.actionStart(mContext, goodBean);//启动商品详情
            }
        });

        Log.d(TAG, "onBindViewHolder: " + goodBean.getGood_type());

        if (GoodBean.ON_LINE.equals(goodBean.getGood_type()))
            holder.mLittleImg.setImageResource(R.drawable.shop_online);
        else
            holder.mLittleImg.setImageResource(R.drawable.shop_bonus);

        holder.mStatusText.setText(bean.getStatus());//状态

        List<ImageBean> imageBeans = goodBean.getImage_list();
        if (CollectionUtils.isEmpty(imageBeans)) {
            Glide.with(mContext).
                    load(R.drawable.image_loading).into(holder.mImageView);//添加图片
        } else {
            Glide.with(mContext).
                    load(ServerInfo.getImageAddress(imageBeans.get(0).getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error).into(holder.mImageView);//添加图片
        }


        holder.mTitleText.setText(goodBean.getTitle());
        holder.mIntroduceText.setText(goodBean.getIntroduce());//描述

        holder.mMoney.setText("￥" + goodBean.getPrice());//单价
        holder.mCount.setText("×" + bean.getGood_count());//数量
        holder.mAllMoney.setText("总价:￥" + (goodBean.getPrice() * bean.getGood_count()));

    }

    @Override
    public int getItemCount() {
        return mOrderRecordBeans == null ? 0 : mOrderRecordBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;

        ImageView mLittleImg;
        TextView mShopName;
        TextView mStatusText;
        ImageView mImageView;
        TextView mTitleText;
        TextView mIntroduceText;
        TextView mMoney;
        TextView mCount;
        TextView mAllMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);//根布局
            mLittleImg = itemView.findViewById(R.id.littleImg);
            mShopName = itemView.findViewById(R.id.shopName);
            mStatusText = itemView.findViewById(R.id.statusText);
            mImageView = itemView.findViewById(R.id.imageView);
            mTitleText = itemView.findViewById(R.id.titleText);
            mIntroduceText = itemView.findViewById(R.id.introduceText);
            mMoney = itemView.findViewById(R.id.money);
            mCount = itemView.findViewById(R.id.count);
            mAllMoney = itemView.findViewById(R.id.allMoney);
        }
    }
}