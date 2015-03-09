package com.example.murat.benimbebegim;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentDiary extends Fragment {
    private ActionBar actionBar;
    public static final String TAG = FragmentDiary.class.getSimpleName();
    public static FragmentDiary newInstance() {
        return new FragmentDiary();
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.layout_diaryfragment, container,false);
        actionBar=getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		return view;
	}
}
