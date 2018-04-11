package p8project.sw801.ui.main.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import p8project.sw801.R;
import p8project.sw801.ui.event.addevent.AddEvent;
import p8project.sw801.ui.event.MyEventAdapter;

public class MyEventsFragment extends Fragment {
    //Implement viewmodel here

    //Setup of burger menu
    private ListView listview;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<String> myEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_my_events, container, false);


        listview = (ListView) rootView.findViewById(R.id.listViewMyEvents);

        //------Creation of list of smart devices
        myEvents = new ArrayList<String>();
        myEvents.add("Hue");
        myEvents.add("Nest");

        MyEventAdapter myAdapter = new MyEventAdapter(rootView.getContext(), myEvents);
        listview.setAdapter(myAdapter);
        //------Creation of list of smart devices



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                String selectedDevice=myEvents.get(position);
                Toast.makeText(rootView.getContext(), "Smart device Selected : "+selectedDevice,   Toast.LENGTH_LONG).show();
            }
        });


        ImageView add = rootView.findViewById(R.id.imageView_myeventadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(rootView.getContext(), AddEvent.class);
                startActivity(intent);
            }
        });






        return rootView;


    }




}
