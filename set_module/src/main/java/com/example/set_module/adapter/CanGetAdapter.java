package com.example.set_module.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.base_lib.util.DateUtil;
import com.example.common_lib.java_bean.SetRecordBean;
import com.example.set_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class CanGetAdapter extends RecyclerView.Adapter<CanGetAdapter.ViewHolder> {

    private List<SetRecordBean> mSetRecordBeans;

    public CanGetAdapter(List<SetRecordBean> setRecordBeans) {
        mSetRecordBeans = setRecordBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_can_get, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SetRecordBean bean = mSetRecordBeans.get(position);

        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(5), 1f);
        spaceNum(holder.mIdText, "编号:", bean.getSet_record_id() + "");//编号

        int saleRebate = (int) (bean.getSet_bean().getRebate_price() * bean.getSet_bean().getSale_radio());//销售返利
        int redeemRebate = bean.getSet_bean().getRebate_price() - saleRebate;//兑换返利

        spaceNum(holder.mSaleText, "可领销售积分:", saleRebate + "");//销售返利
        spaceNum(holder.mRedeemText, "可领兑换积分:", redeemRebate + "");//兑换返利


        spaceNum(holder.mInsertTime, "创建时间:", DateUtil.formatDate(bean.getInsert_time()));//插入时间
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
        for (int i = key.length(); i <= 7; i++)
            str += "\u3000";
        str += value;
        textView.setText(str);
    }

    @Override
    public int getItemCount() {
        return mSetRecordBeans == null ? 0 : mSetRecordBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;
        TextView mIdText;
        TextView mSaleText;
        TextView mRedeemText;
        TextView mInsertTime;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mIdText = itemView.findViewById(R.id.idText);
            mSaleText = itemView.findViewById(R.id.saleText);
            mRedeemText = itemView.findViewById(R.id.redeemText);
            mInsertTime = itemView.findViewById(R.id.insertTime);
        }
    }
}