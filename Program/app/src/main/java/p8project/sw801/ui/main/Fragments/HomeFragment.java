package p8project.sw801.ui.main.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import p8project.sw801.R;
import p8project.sw801.ui.event.addevent.AddEvent;
import p8project.sw801.ui.Settings.SettingsActivity;
import p8project.sw801.ui.main.MainActivity;


//Implements Navigationviewlistener
public class HomeFragment extends Fragment {
    //private HomeActivityViewModel viewModel = new HomeActivityViewModel();
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_home, container, false);
        rootView.invalidate();

        //setTitle("Notify Me - Home");

        final Button buttonCreate = rootView.findViewById(R.id.button_Create);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddEvent.class);

                //((MainActivity)getActivity()).mViewPager.setCurrentItem(1);
                //((MainActivity)getActivity()).mSectionsPagerAdapter.notifyDataSetChanged();
                //((MainActivity)getActivity()).getSupportFragmentManager().findFragmentByTag("Events");
                // Intent intent = new Intent(HomeFragment.this, MyCreateActivity.class);
                startActivity(intent);
            }
        });

        //----------------Rest of the code

        final Button buttonMyEvents = rootView.findViewById(R.id.button_MyEvents);
        buttonMyEvents.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).ChangeToEvents();
                //((MainActivity)getActivity()).changetomyevents();
                //Intent intent = new Intent(HomeFragment.this, MyEventsFragment.class);
                //startActivity(intent);
            }
        }));

        final Button buttonMySmartDevices = rootView.findViewById(R.id.button_MySmartDevices);
        buttonMySmartDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).ChangeToSmartDevice();
                //Intent intent = new Intent(HomeFragment.this, MySmartDeviceFragment.class);
                //startActivity(intent);
            }
        });

        final Button buttonSettings = rootView.findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                //Intent intent = new Intent();
                //intent.setClass(HomeFragment.this.getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}