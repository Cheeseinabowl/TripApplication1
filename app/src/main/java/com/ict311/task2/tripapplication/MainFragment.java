package com.ict311.task2.tripapplication;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainFragment extends Fragment {
    DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.activity_main, container, false);
        db = new DatabaseHelper(getActivity());
        ArrayList<Trip> tripList = db.getAllTrips();

        ListView listView = (ListView) v.findViewById(R.id.tripList);
        if(tripList.size()>0)
        listView.setAdapter(new CustomListAdapter( getActivity(),tripList ));

        Button logBtn = (Button) v.findViewById(R.id.logBtn);
        Button settingBtn = (Button) v.findViewById(R.id.settingBtn);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LogActivity.class);
                startActivity(intent);
            }
        });
  settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,int position, long id)
            {
                String tripID = ((TextView) view.findViewById(R.id.tripID))
                        .getText().toString();

                Intent intent = new Intent(getActivity(), TripInformationActivity.class);
                intent.putExtra("tripID", tripID);
                startActivity(intent);
            }
        });
        return v;
    }

}


