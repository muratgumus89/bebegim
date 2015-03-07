package com.example.murat.benimbebegim.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.murat.benimbebegim.R;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	String[] favName;
	String[] country;
	String[] population;
	int[] upLogo;
	LayoutInflater inflater;

	public ListViewAdapter(Context context, String[] favName, int[] upLogo) {
		this.context = context;
		this.favName = favName;
		this.upLogo = upLogo;
	}

	public int getCount() {
		return favName.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		// Declare Variables
		TextView txtfavName;
		ImageView imgLogo;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_item, parent, false);

		// Locate the TextViews in listview_item.xml
		txtfavName = (TextView) itemView.findViewById(R.id.fav_name);
		// Locate the ImageView in listview_item.xml
		imgLogo = (ImageView) itemView.findViewById(R.id.logo);

		// Capture position and set to the TextViews
		txtfavName.setText(favName[position]);

		// Capture position and set to the ImageView
		imgLogo.setImageResource(upLogo[position]);

		return itemView;
	}
}
