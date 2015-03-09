package com.example.murat.benimbebegim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Murat on 9.3.2015.
 */
public class ActivityCategory extends Fragment{
    public static final String TAG = ActivityCategory.class.getSimpleName();
    public static ActivityCategory newInstance() {
        return new ActivityCategory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.activity_category, container,false);
        return view;
    }
}
