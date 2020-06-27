package com.example.common_lib.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.QMUIFragmentPagerAdapter;

import java.util.List;

public class QMUIViewPageAdapter extends QMUIFragmentPagerAdapter {

    private List<QMUIFragment> mQMUIFragments;//碎片
    private List<String> mTitle;//标题

    public QMUIViewPageAdapter(@NonNull FragmentManager fm, List<QMUIFragment> qMUIFragments, List<String> title) {
        super(fm);
        mQMUIFragments = qMUIFragments;//碎片
        mTitle = title;//标题
    }

    /**
     * 得到碎片
     *
     * @param position
     * @return
     */
    @Override
    public QMUIFragment createFragment(int position) {
        return mQMUIFragments.get(position);
    }

    /**
     * 得到碎片数目
     *
     * @return
     */
    @Override
    public int getCount() {
        return mQMUIFragments == null ? 0 : mQMUIFragments.size();
    }

    /**
     * 得到标题
     *
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }


}
