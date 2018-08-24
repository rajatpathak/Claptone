package com.appentus.claptone.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appentus.claptone.activities.YoutubeActivity;
import com.appentus.claptone.fragment.HomeFragment;
import com.appentus.claptone.fragment.ShowsFragment;
import com.appentus.claptone.fragment.MusicFragment;
import com.appentus.claptone.fragment.ShopFragment;
import com.appentus.claptone.fragment.TabFragment4;
import com.appentus.claptone.fragment.TestFrag;

import junit.framework.Test;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Context context;

    public PagerAdapter(Context context,FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context= context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                ShowsFragment showsFragment= new ShowsFragment();
                return showsFragment;
            case 2:
                MusicFragment musicFragment= new MusicFragment();
                return musicFragment;
            case 3:
                TestFrag video=new TestFrag();
                return video;
            case 4:
                ShopFragment shopFragment= new ShopFragment();
                return shopFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}