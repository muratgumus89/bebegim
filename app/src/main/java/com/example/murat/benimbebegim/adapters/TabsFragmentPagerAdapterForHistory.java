package com.example.murat.benimbebegim.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.murat.benimbebegim.ActivityCategory;
import com.example.murat.benimbebegim.ActivityEarlier;
import com.example.murat.benimbebegim.ActivityFavorites;
import com.example.murat.benimbebegim.ActivityFeatures;
import com.example.murat.benimbebegim.ActivityMoreEvents;
import com.example.murat.benimbebegim.ActivityToday;

public class TabsFragmentPagerAdapterForHistory extends FragmentPagerAdapter {

 public TabsFragmentPagerAdapterForHistory(FragmentManager fm) {
 super(fm);
 // TODO Auto-generated constructorq stub
 }
 
 @Override
 public Fragment getItem(int index) {
 // TODO Auto-generated method stub
 if(index == 0)
 return new ActivityEarlier();
 if(index == 1)
 return new ActivityToday();
 if(index == 2)
 return new ActivityCategory();
 
 return null;
 }
 
 @Override
 public int getCount() {
        // TODO Auto-generated method stub
        return 3;
    }
}