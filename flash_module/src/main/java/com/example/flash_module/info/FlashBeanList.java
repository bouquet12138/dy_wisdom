package com.example.flash_module.info;

import com.example.common_lib.java_bean.FlashBean;
import com.example.common_lib.java_bean.FlashContentBean;
import com.example.flash_module.R;

import java.util.ArrayList;
import java.util.List;

public class FlashBeanList {

    private static FlashBeanList sInstance;

    /**
     * 构造器私有化
     */
    private FlashBeanList() {
    }

    private List<FlashBean> mFlashBeans = new ArrayList<>();


    public static FlashBeanList getInstance() {
        if (sInstance == null) {
            sInstance = new FlashBeanList();
        }
        return sInstance;
    }

    public List<FlashBean> getFlashBeans() {

        if (mFlashBeans.size() == 0) {
            mFlashBeans.add(getFlashBean1());
            mFlashBeans.add(getFlashBean2());
        }

        return mFlashBeans;
    }

    private FlashBean getFlashBean1() {

        FlashBean flashBean = new FlashBean("新到果蔬", 3, "2020-05-15 19:13:57");

        flashBean.getContent_list().add(new FlashContentBean("新鲜大柿子", R.drawable.flash_fruit_a));
        flashBean.getContent_list().add(new FlashContentBean("新鲜小柿子", R.drawable.flash_fruit_b));
        flashBean.getContent_list().add(new FlashContentBean("新鲜土豆", R.drawable.flash_fruit_c));
        flashBean.getContent_list().add(new FlashContentBean("新鲜扁豆", R.drawable.flash_fruit_d));
        return flashBean;

    }

    private FlashBean getFlashBean2() {

        FlashBean flashBean = new FlashBean("公司环境一览", 4, "2020-05-15 19:21:12");
        flashBean.getContent_list().add(new FlashContentBean("二楼，招待客户的地方。", R.drawable.flash_a));
        flashBean.getContent_list().add(new FlashContentBean("五楼视频剪辑，后勤，软件开发初", R.drawable.flash_b));
        flashBean.getContent_list().add(new FlashContentBean("五楼商品展示及员工娱乐区", R.drawable.flash_c));
        flashBean.getContent_list().add(new FlashContentBean("办公室", R.drawable.flash_e));
        return flashBean;

    }


}
