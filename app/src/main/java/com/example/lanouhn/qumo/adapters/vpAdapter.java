package com.example.lanouhn.qumo.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 */
public class vpAdapter extends FragmentPagerAdapter {

    private List<String> tabNames;
    private List<Fragment> fragments;
    private Context context;

    public vpAdapter(FragmentManager fm, List<String> tabNames, List<Fragment> fragments) {
        super(fm);
        this.tabNames = tabNames;
       this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabNames.get(position);
    }


}


