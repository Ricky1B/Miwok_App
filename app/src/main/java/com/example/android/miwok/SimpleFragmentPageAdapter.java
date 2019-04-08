package com.example.android.miwok;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SimpleFragmentPageAdapter extends FragmentPagerAdapter {
    public SimpleFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int i) {
          if(i ==0){
              return new NumbersFragment();
          }else if(i==1){
              return new FamilyMembersFragment();
          }else if(i==2){
              return new ColorsFragment();
          }else{
              return new PhrasesFragment();
          }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position ==0){
            return "Numbers";
        } else if(position ==1){
            return "Family";
        }else if(position == 2){
            return "Colors";
        }else {
            return "Phrases";
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
