package com.example.integer_module.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.common_lib.info.NowUserInfo;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.integer_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class SaleShareAdapter extends RecyclerView.Adapter<SaleShareAdapter.ViewHolder> {

    private List<SaleShareRecordBean> mSaleShareRecordBeans;

    public SaleShareAdapter(List<SaleShareRecordBean> saleShareRecordBeans) {
        mSaleShareRecordBeans = saleShareRecordBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_item_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(5), 1f);
        SaleShareRecordBean bean = mSaleShareRecordBeans.get(position);//销毁积分bean
        spaceNum(holder.mIdText, "编号:", bean.getSale_share_id() + "");//编号
        spaceNum(holder.mMoneyText, "交易金额:", bean.getTransaction_amount() + "");//交易金额
        spaceNum(holder.mTimeText, "交易时间:", bean.getInsert_time());//插入时间


        if (SaleShareRecordBean.INTEGRAL_TYPE_TRAN.equals(bean.getIntegral_type())) {
            holder.mUserIdText.setVisibility(View.VISIBLE);
            holder.mTargetUserIdText.setVisibility(View.VISIBLE);

            if (NowUserInfo.getNowUserId() == bean.getUser_id())
                spaceNum(holder.mUserIdText, "转出方:", "我");
            else
                spaceNum(holder.mUserIdText, "转出方编号:", bean.getUser_id() + "");

            if (NowUserInfo.getNowUserId() == bean.getTarget_user_id())
                spaceNum(holder.mTargetUserIdText, "转入方:", "我");
            else
                spaceNum(holder.mTargetUserIdText, "转入方编号:", bean.getTarget_user_id() + "");
        } else {
            holder.mUserIdText.setVisibility(View.GONE);
            holder.mTargetUserIdText.setVisibility(View.GONE);//不可见
        }

        spaceNum(holder.mRemainAmount, "剩余工资:", bean.getRemain_amount() + "");//剩余销售积分
        spaceNum(holder.mRemarkText, "备注:", bean.getTransaction_remark() + "");//备注

    }

    /**
     * 设置文字
     *
     * @param textView
     * @param key
     * @param value
     */
    private void spaceNum(TextView textView, String key, String value) {
        String str = key;
        for (int i = key.length(); i <= 6; i++)
            str += "\u3000";
        str += value;
        textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return mSaleShareRecordBeans == null ? 0 : mSaleShareRecordBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;
        TextView mIdText;
        TextView mMoneyText;
        TextView mTimeText;
        TextView mUserIdText;
        TextView mTargetUserIdText;
        TextView mRemainAmount;
        TextView mRemarkText;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mIdText = itemView.findViewById(R.id.idText);
            mMoneyText = itemView.findViewById(R.id.moneyText);
            mTimeText = itemView.findViewById(R.id.timeText);
            mUserIdText = itemView.findViewById(R.id.userIdText);
            mTargetUserIdText = itemView.findViewById(R.id.targetUserIdText);
            mRemainAmount = itemView.findViewById(R.id.remainAmount);
            mRemarkText = itemView.findViewById(R.id.remarkText);
        }
    }
}