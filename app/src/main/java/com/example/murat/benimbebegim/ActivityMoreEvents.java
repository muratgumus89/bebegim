package com.example.murat.benimbebegim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.murat.benimbebegim.adapters.ListViewAdapter;

public class ActivityMoreEvents extends Fragment {
    String[] favName;
    int[] upLogo;
    ListView list;
    ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_moreevents, container,
                false);
        // Generate sample data
        favName = getResources().getStringArray(R.array.MoreEvents);


        upLogo = new int[] {R.drawable.ic_health_bullet,R.drawable.ic_medicine_bullet,R.drawable.ic_vaccination_bullet,
                R.drawable.ic_hygiene_bullet,R.drawable.ic_teeth_bullet};

        // Locate the ListView in fragmenttab1.xml
        list = (ListView) rootView.findViewById(R.id.listMoreEvents);

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getActivity(), favName, upLogo);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        // Capture clicks on ListView items
        return rootView;
    }
 
}