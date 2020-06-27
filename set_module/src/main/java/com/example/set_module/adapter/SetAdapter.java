package com.example.set_module.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.common_lib.java_bean.SetBean;
import com.example.set_module.R;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {

    private List<SetBean> mSetBeans;

    public SetAdapter(List<SetBean> setBeans) {
        mSetBeans = setBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_set, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SetBean setBean = mSetBeans.get(position);
        if (SetBean.SET_TYPE_COLL.equals(setBean.getSet_name())) {
            holder.mImageView.setImageResource(R.drawable.set_b);
        }
        if (SetBean.SET_TYPE_AGENCY.equals(setBean.getSet_name())) {
            holder.mImageView.setImageResource(R.drawable.set_c);
        }

        holder.mMoneyText.setText("￥" + setBean.getSet_price());//设置一下价格

        if (setBean.isSelected()) {//如果选中
            holder.mRoot.setBackgroundResource(R.drawable.set_shape_selected);
        } else {
            holder.mRoot.setBackgroundResource(0);
        }

        int imageWidth = ScreenUtils.getScreenWidth() - SizeUtils.dp2px(20);
        int imageHeight = (int) (imageWidth * 0.52f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(imageWidth, imageHeight);
        layoutParams.topMargin = SizeUtils.dp2px(10);
        holder.mRoot.setLayoutParams(layoutParams);//设置布局

        holder.mRoot.setOnClickListener(v -> {//点击监听

            for (SetBean _setBean : mSetBeans)
                _setBean.setSelected(false);//取消选中
            setBean.setSelected(true);//选中当前

            notifyDataSetChanged();//唤醒数据更新
        });

    }

    /**
     * 得到item数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mSetBeans == null ? 0 : mSetBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewGroup mRoot;
        ImageView mImageView;
        TextView mMoneyText;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mImageView = itemView.findViewById(R.id.imageView);
            mMoneyText = itemView.findViewById(R.id.moneyText);
        }
    }

    /**
     * 得到选中的套餐
     *
     * @return
     */
    public int getSelectSet() {
        for (SetBean setBean : mSetBeans) {
            if (setBean.isSelected())
                return setBean.getSet_id();//得到当前选中的套餐id
        }
        return 0;
    }

}