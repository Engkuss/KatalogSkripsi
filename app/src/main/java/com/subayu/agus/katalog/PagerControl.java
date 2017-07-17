package com.subayu.agus.katalog;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by black4v on 15/05/2017.
 */

public class PagerControl extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerControl(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                Menuu menuu = new Menuu();
                return menuu;
            case 1:
                Pencarian pencarian = new Pencarian();
                return pencarian;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
