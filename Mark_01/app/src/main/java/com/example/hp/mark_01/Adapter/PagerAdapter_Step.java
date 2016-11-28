package com.example.hp.mark_01.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by hp on 2016/8/23.
 */
public class PagerAdapter_Step extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    List<String> tabtitlelist;

    public PagerAdapter_Step(FragmentManager fm,List<Fragment> fragmentList,List<String> tabtitlelist) {
        super(fm);
        this.fragmentList=fragmentList;
        this.tabtitlelist=tabtitlelist;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title="第 "+(position+1)+" 步";
        return title;
    }
}
