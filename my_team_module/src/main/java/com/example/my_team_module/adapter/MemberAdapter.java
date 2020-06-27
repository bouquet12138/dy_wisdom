package com.example.my_team_module.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.example.common_lib.java_bean.UserBean;
import com.example.my_team_module.R;
import com.qmuiteam.qmui.layout.QMUILinearLayout;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private Context mContext;
    private List<UserBean> mUserBeans;

    public MemberAdapter(Context context, List<UserBean> userBeans) {
        mContext = context;
        mUserBeans = userBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_team_item_member, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserBean bean = mUserBeans.get(position);//得到用户

        holder.mRoot.setRadiusAndShadow(SizeUtils.dp2px(5), SizeUtils.dp2px(5), 1f);

        holder.mRoot.setOnClickListener(view -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onClick(bean);//将用户传递过去
        });

        if (TextUtils.isEmpty(bean.getName()))
            holder.mNameText.setText("姓名：\u3000" + "");
        else
            holder.mNameText.setText("姓名：\u3000" + bean.getName());

        String roleStr = "等级：\u3000" + bean.getRole();

        SpannableString spannableString = new SpannableString(roleStr);
        int color_id = R.color.business;

        switch (bean.getRole()) {
            case UserBean.ROLE_COOPERATIVE_PARTNER:
                color_id = R.color.coop;
                break;
            case UserBean.ROLE_AGENT:
                color_id = R.color.agent;
                break;
            case UserBean.ROLE_SERVICE:
                color_id = R.color.service;
                break;
            case UserBean.ROLE_SERVICE_CENTER:
                color_id = R.color.service_center;
                break;
        }


        spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(color_id)),
                3, roleStr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        holder.mRoleText.setText(spannableString);//设置等级

        holder.mNumText.setText("团队人数:" + bean.getChild_num());//孩子数

        if (TextUtils.isEmpty(bean.getPhone()))
            holder.mPhoneText.setText("手机号：" + "");
        else
            holder.mPhoneText.setText("手机号：" + bean.getPhone());

        // HeadImageUtil.setUserHead(bean, mContext, holder.mHeadImg);//设置头像
    }

    @Override
    public int getItemCount() {
        return mUserBeans == null ? 0 : mUserBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        QMUILinearLayout mRoot;
        TextView mNameText;
        TextView mRoleText;
        TextView mPhoneText;
        TextView mNumText;

        public ViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView.findViewById(R.id.root);
            mNameText = itemView.findViewById(R.id.nameText);
            mRoleText = itemView.findViewById(R.id.roleText);
            mPhoneText = itemView.findViewById(R.id.phoneText);
            mNumText = itemView.findViewById(R.id.numText);
        }
    }

    public interface OnItemClickListener {
        void onClick(UserBean userBean);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}