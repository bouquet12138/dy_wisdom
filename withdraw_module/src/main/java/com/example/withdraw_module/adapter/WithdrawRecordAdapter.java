package com.example.withdraw_module.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.common_lib.java_bean.WithdrawBean;
import com.example.withdraw_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class WithdrawRecordAdapter extends RecyclerView.Adapter<WithdrawRecordAdapter.ViewHolder> {

    private List<WithdrawBean> mWithdrawBeans;

    public WithdrawRecordAdapter(List<WithdrawBean> withdrawBeans) {
        mWithdrawBeans = withdrawBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_item_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(5), 1f);

        WithdrawBean bean = mWithdrawBeans.get(position);//得到这条记录
        holder.mIdText.setText("编号:\u3000\u3000\u3000" + bean.getWithdraw_id());
        holder.mMoneyText.setText("提现金额:\u3000" + bean.getWithdraw_amount());
        holder.mTimeText.setText("提现时间:\u3000" + bean.getInsert_time());
        if (TextUtils.isEmpty(bean.getBank_card())) {
            holder.mBandCardText.setVisibility(View.GONE);
        } else {
            holder.mBandCardText.setVisibility(View.VISIBLE);//可见
            holder.mBandCardText.setText("银行卡号:\u3000" + bean.getBank_card());
        }

        if (TextUtils.isEmpty(bean.getWe_chat())) {
            holder.mWeChartText.setVisibility(View.GONE);
        } else {
            holder.mWeChartText.setVisibility(View.VISIBLE);//可见
            holder.mWeChartText.setText("微信:\u3000\u3000\u3000" + bean.getBank_card());
        }

        if (TextUtils.isEmpty(bean.getAli_pay())) {
            holder.mAlipayText.setVisibility(View.GONE);
        } else {
            holder.mAlipayText.setVisibility(View.VISIBLE);//可见
            holder.mAlipayText.setText("支付宝:\u3000\u3000" + bean.getAli_pay());
        }

        holder.mRemainAmount.setText("剩余积分:\u3000" + bean.getRemain_amount());//剩余工资

        holder.mRemarkText.setText("备注:\u3000\u3000\u3000" + bean.getWithdraw_remark());

        if (bean.getIs_process() == 1) {
            holder.mIsProcessText.setText("是否处理:\u3000是");
            holder.mHandleTime.setVisibility(View.VISIBLE);
            holder.mHandleTime.setText("处理时间:\u3000" + bean.getUpdate_time());
        } else {
            holder.mIsProcessText.setText("是否处理:\u3000否");
            holder.mHandleTime.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mWithdrawBeans == null ? 0 : mWithdrawBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;
        TextView mIdText;
        TextView mMoneyText;
        TextView mRemarkText;
        TextView mTimeText;
        TextView mBandCardText;
        TextView mWeChartText;
        TextView mAlipayText;
        TextView mRemainAmount;//剩余积分
        TextView mIsProcessText;
        TextView mHandleTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mIdText = itemView.findViewById(R.id.idText);
            mMoneyText = itemView.findViewById(R.id.moneyText);
            mRemarkText = itemView.findViewById(R.id.remarkText);
            mTimeText = itemView.findViewById(R.id.timeText);
            mBandCardText = itemView.findViewById(R.id.bandCardText);
            mWeChartText = itemView.findViewById(R.id.weChartText);
            mAlipayText = itemView.findViewById(R.id.alipayText);
            mRemainAmount = itemView.findViewById(R.id.remainAmount);//剩余金额

            mIsProcessText = itemView.findViewById(R.id.isProcessText);
            mHandleTime = itemView.findViewById(R.id.handleTime);

        }
    }
}