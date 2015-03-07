package com.example.murat.benimbebegim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.view.ViewGroup;

import com.example.murat.benimbebegim.adapters.ListViewAdapter;

public class ActivityFavorites extends Fragment {
    String[] favName;
    int[] upLogo;
    ListView list;
    ListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_favorites, container,
                false);
        // Generate sample data
        favName = getResources().getStringArray(R.array.Favorites);

       upLogo = new int[] {R.drawable.ic_mood_bullet,R.drawable.ic_solid_bullet,R.drawable.ic_bottle_bullet,
       R.drawable.ic_breastfeed_bullet,R.drawable.ic_sleep_bullet,R.drawable.ic_diaper_bullet,R.drawable.ic_pumping_milk_bullet};

        // Locate the ListView in fragmenttab1.xml
        list = (ListView) rootView.findViewById(R.id.listFavorites);

        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(getActivity(), favName, upLogo);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        // Capture clicks on ListView items
        return rootView;
    }
}