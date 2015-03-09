package com.example.murat.benimbebegim;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class FragmentGrowth extends Fragment {
    private ActionBar actionBar;
    public static final String TAG = FragmentGrowth.class.getSimpleName();
    public static FragmentGrowth newInstance() {
        return new FragmentGrowth();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.layout_growthfragment, container,false);
        actionBar=getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		return view;
	}
}
