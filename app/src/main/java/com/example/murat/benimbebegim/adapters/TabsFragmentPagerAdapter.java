package com.example.murat.benimbebegim.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.murat.benimbebegim.ActivityFavorites;
import com.example.murat.benimbebegim.ActivityFeatures;
import com.example.murat.benimbebegim.ActivityMoreEvents;

public class TabsFragmentPagerAdapter extends FragmentPagerAdapter {
 
 public TabsFragmentPagerAdapter(FragmentManager fm) {
 super(fm);
 // TODO Auto-generated constructorq stub
 }
 
 @Override
 public Fragment getItem(int index) {
 // TODO Auto-generated method stub
 if(index == 0)
 return new ActivityFeatures();
 if(index == 1)
 return new ActivityFavorites();
 if(index == 2)
 return new ActivityMoreEvents();
 
 
 return null;
 }
 
 @Override
 public int getCount() {
 // TODO Auto-generated method stub
 return 3;
 }
 
}