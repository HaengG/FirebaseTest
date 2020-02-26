package com.nslb.firebasetest.UI;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SNSView mSNSView = null;
    public TripTalkChatting mTripTalkChatting = null;
    public TripTalkDistribution mTripTalkDistribution = null;
    public TripTalkView mTripTalkView = null;

    public SectionsPagerAdapter(Context context, FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return mSNSView = new SNSView();
            case 1:
                return mTripTalkChatting = new TripTalkChatting();
            case 2:
                return mTripTalkDistribution = new TripTalkDistribution();
            case 3:
                return mTripTalkView = new TripTalkView();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
