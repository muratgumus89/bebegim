package com.example.murat.benimbebegim;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentStats extends Fragment {
    public static final String TAG = FragmentStats.class.getSimpleName();
    private ActionBar actionBar;
    public static FragmentStats newInstance() {
        return new FragmentStats();
    }

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.layout_statsfragment, container,false);
        actionBar=getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		return view;
	}
}
