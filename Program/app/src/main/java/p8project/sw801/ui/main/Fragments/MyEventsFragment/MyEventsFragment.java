package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.databinding.ActivityMyEventsBinding;
import p8project.sw801.ui.base.BaseFragment;
import p8project.sw801.ui.event.MyEventAdapter;
import p8project.sw801.ui.event.addevent.AddEvent;

public class MyEventsFragment extends BaseFragment<ActivityMyEventsBinding, MyEventsFragmentViewModel> implements MyEventsFragmentNavigator {

    @Inject
    MyEventsFragmentViewModel mMyEventsFragmentViewModel;
    private ActivityMyEventsBinding mActivityMyEventsBinding;

    //Setup of burger menu
    private ListView listview;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<Event> myEvents;
    View view;

    @Override
    public void onResume() {
        getViewModel().fetchFromDatabase();

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        mActivityMyEventsBinding = getViewDataBinding();
        view = mActivityMyEventsBinding.getRoot();
        mMyEventsFragmentViewModel.setNavigator(this);
        setUp();
        return view;
    }
	
    public void setUp(){


        listview = (ListView) mActivityMyEventsBinding.listViewMyEvents;

        //------Creation of list of Events
        myEvents = new ArrayList<Event>();
        myEvents.addAll(mMyEventsFragmentViewModel.getEventObservableList());
        if (myEvents == null) {

        } else {
            MyEventAdapter myAdapter = new MyEventAdapter(view.getContext(), myEvents, MyEventsFragment.this);
            listview.setAdapter(myAdapter);
            //------Creation of list of smart devices


            ImageView add = mActivityMyEventsBinding.imageViewMyeventadd;
        }


    }

    @Override
    public void updatelist() {
        setUp();
    }

    @Override
    public void deleteEvent(Event event) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete event")
                .setMessage("Do you really want to delete the event: " + event.getName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> getViewModel().deleteEventFromDatabase(event))
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void updateEvent(Event event, Boolean condition) {
        getViewModel().updateEvent(event, condition);
    }

    public void addNewEvent() {
        Intent intent = new Intent(this.getContext(), AddEvent.class);
        startActivity(intent);
    }

    public static MyEventsFragment newInstance() {
        Bundle args = new Bundle();
        MyEventsFragment fragment = new MyEventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_events;
    }

    @Override
    public MyEventsFragmentViewModel getViewModel() {
        return mMyEventsFragmentViewModel;
    }



}
