package com.placediscovery.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import com.placediscovery.ui.fragment.CFragment;
import com.placediscovery.ui.fragment.BFragment;
import com.placediscovery.ui.fragment.AFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<TabPagerItem> mTabs;
    public ViewPagerAdapter(FragmentManager fragmentManager, List<TabPagerItem> tabs) {
        super(fragmentManager);
        this.mTabs = tabs;
    }

    public void setDatasource(List<TabPagerItem> datasource){
        mTabs = datasource;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new AFragment();
            case 1:
                return new BFragment();
            case 2:
                return new CFragment();
        }
        return mTabs.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position).getTitle();
    }
}