package com.example.sale_integer_module.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.common_lib.java_bean.SaleShareRecordBean;
import com.example.common_lib.java_bean.UserBean;
import com.example.sale_integer_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class SaleTeamShareAdapter extends RecyclerView.Adapter<SaleTeamShareAdapter.ViewHolder> {

    private List<SaleShareRecordBean> mSaleShareRecordBeans;

    public SaleTeamShareAdapter(List<SaleShareRecordBean> saleShareRecordBeans) {
        mSaleShareRecordBeans = saleShareRecordBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_team_item_record, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(5), 1f);
        SaleShareRecordBean bean = mSaleShareRecordBeans.get(position);//销毁积分bean

        spaceNum(holder.mIdText, "编号:", bean.getSale_share_id() + "");//编号
        spaceNum(holder.mMoneyText, "金额:", bean.getTransaction_amount() + "");//交易金额

        UserBean userBean = bean.getUser_bean();
        if (userBean != null) {
            spaceNum(holder.mNameText, "姓名:", userBean.getName());
            spaceNum(holder.mPhoneText, "手机:", userBean.getPhone());
            spaceNum(holder.mRoleText, "等级:", userBean.getRole());
        } else {
            spaceNum(holder.mNameText, "姓名:", "");
            spaceNum(holder.mPhoneText, "手机:", "");
            spaceNum(holder.mRoleText, "等级:", "");
        }

        spaceNum(holder.mTimeText, "交易时间:", bean.getInsert_time());//插入时间
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
        for (int i = key.length(); i <= 5; i++)
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
        TextView mNameText;
        TextView mPhoneText;
        TextView mRoleText;
        TextView mMoneyText;
        TextView mTimeText;


        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mIdText = itemView.findViewById(R.id.idText);
            mNameText = itemView.findViewById(R.id.nameText);
            mPhoneText = itemView.findViewById(R.id.phoneText);
            mRoleText = itemView.findViewById(R.id.roleText);
            mMoneyText = itemView.findViewById(R.id.moneyText);
            mTimeText = itemView.findViewById(R.id.timeText);
        }
    }
}