package com.example.calendary.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.calendary.Fragment.MyActivityFragment;
import com.example.calendary.Fragment.MyDiaryFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm, tabCount);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                MyDiaryFragment myDiaryFragment = new MyDiaryFragment();
                return myDiaryFragment;

            case 1 :
                MyActivityFragment myActivityFragment = new MyActivityFragment();
                return myActivityFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
