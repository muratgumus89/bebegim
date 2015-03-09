package com.example.murat.benimbebegim;

import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.murat.benimbebegim.adapters.TabsFragmentPagerAdapter;
import com.example.murat.benimbebegim.adapters.TabsFragmentPagerAdapterForHistory;

public class FragmentHistory extends Fragment implements ActionBar.TabListener {

    public static final String TAG = FragmentHistory.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    Integer count;
    ViewPager mViewPager;
    private ActionBar actionBar;
    private TabsFragmentPagerAdapterForHistory tabsAdapter;
    private String[] days = new String[]{"Features", "Favorites", "More Events","Earlier", "Today", "Category"};



    public static FragmentHistory newInstance() {
        return new FragmentHistory();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_historyfragment, container, false);
        tabsAdapter = new TabsFragmentPagerAdapterForHistory(
                getChildFragmentManager());

        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
        mViewPager.setAdapter(tabsAdapter);
        actionBar = getActivity().getActionBar();
        actionBar.removeAllTabs();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        for (int i = 3; i < 6; i++) {
            actionBar.addTab(actionBar.newTab().setText(days[i])
                    .setTabListener(this));
        }
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg) {
                // TODO Auto-generated method stub
                actionBar.setSelectedNavigationItem(arg);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        return v;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
