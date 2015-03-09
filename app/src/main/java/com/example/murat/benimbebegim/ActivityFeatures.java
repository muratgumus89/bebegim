package com.example.murat.benimbebegim;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ActivityFeatures extends Fragment implements View.OnClickListener {
    TextView txtTheme;
    final Context context=this.context;
    @Override
         public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.activity_features, container, false);
        txtTheme=(TextView)view.findViewById(R.id.txtTheme_Home_Screen);
        txtTheme.setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtTheme_Home_Screen:
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.select_theme);
                dialog.setTitle("Select Theme");

                // set the custom dialog components - text, image and button
                Button blue = (Button) dialog.findViewById(R.id.blue);
                Button green = (Button) dialog.findViewById(R.id.green);
                Button yellow = (Button) dialog.findViewById(R.id.yellow);
                Button black = (Button) dialog.findViewById(R.id.black);
                Button white = (Button) dialog.findViewById(R.id.white);
                Button gray = (Button) dialog.findViewById(R.id.gray);
                Button orange = (Button) dialog.findViewById(R.id.orange);
                Button pink = (Button) dialog.findViewById(R.id.pink);
                Button purple = (Button) dialog.findViewById(R.id.purple);
                Button cancel = (Button) dialog.findViewById(R.id.cancel);
                dialog.show();
                break;

        }
    }
}
