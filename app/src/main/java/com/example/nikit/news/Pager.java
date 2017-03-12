package com.example.nikit.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by nikit on 11.03.2017.
 */

public class Pager extends FragmentStatePagerAdapter {
    int tabCount;

    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                TabNewsFromSource tabNewsFromSource = new TabNewsFromSource();
                return tabNewsFromSource;
            case 1:
                TabNewsFromSource tabNewsFromSource1 = new TabNewsFromSource();
                return tabNewsFromSource1;
            case 2:
                TabNewsFromSource tabNewsFromSource2 = new TabNewsFromSource();
                return tabNewsFromSource2;


        }


        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
