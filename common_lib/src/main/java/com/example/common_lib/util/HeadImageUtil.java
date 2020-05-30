package com.example.common_lib.util;


import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.common_lib.R;
import com.example.common_lib.info.ServerInfo;
import com.example.common_lib.java_bean.UserBean;

public class HeadImageUtil {

    public static void setUserHead(UserBean bean, Context context, ImageView imageView) {

        if (bean == null)
            return;

        if (bean.getHead_img() == null || TextUtils.isEmpty(bean.getHead_img().getImage_url())) {

            if ("女".equals(bean.getSex())) {
                Glide.with(context).load(R.drawable.woman_head).into(imageView);
            } else {
                Glide.with(context).load(R.drawable.man_head).into(imageView);
            }
            return;
        }

        if ("女".equals(bean.getSex())) {
            Glide.with(context).
                    load(ServerInfo.getImageAddress(bean.getHead_img().getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.woman_head).into(imageView);
        } else {
            Glide.with(context).
                    load(ServerInfo.getImageAddress(bean.getHead_img().getImage_url())).
                    placeholder(R.drawable.image_loading).error(R.drawable.man_head).into(imageView);
        }

    }


}
