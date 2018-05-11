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
import p8project.sw801.databinding.FragmentMyEventsBinding;
import p8project.sw801.ui.base.BaseFragment;
import p8project.sw801.ui.event.MyEventAdapter;
import p8project.sw801.ui.event.addevent.AddEvent;

public class MyEventsFragment extends BaseFragment<FragmentMyEventsBinding, MyEventsFragmentViewModel> implements MyEventsFragmentNavigator {

    @Inject
    MyEventsFragmentViewModel mMyEventsFragmentViewModel;
    private FragmentMyEventsBinding mFragmentMyEventsBinding;

    private ListView listview;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<Event> myEvents;
    View view;

    /**
     * Method used on resume.
     * When the app returns to this page, this method is called.
     */
    @Override
    public void onResume() {
        getViewModel().fetchFromDatabase();

        super.onResume();
    }

    /**
     * On create view method for MyEventFragment. Instantiates and sets up all required fields for the page.
     *
     * @param inflater           The infater used from the activity to inflate this fragment.
     * @param container          The container containing this fragment.
     * @param savedInstanceState The saved instance state if there is one.
     * @return The inflated view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        mFragmentMyEventsBinding = getViewDataBinding();
        view = mFragmentMyEventsBinding.getRoot();
        mMyEventsFragmentViewModel.setNavigator(this);
        setUp();
        return view;
    }

    /**
     * Method used to create the page. This method sets the adapter for the listview containing the events created by the user.
     */
    public void setUp() {


        listview = (ListView) mFragmentMyEventsBinding.listViewMyEvents;

        //------Creation of list of Events
        myEvents = new ArrayList<Event>();
        myEvents.addAll(mMyEventsFragmentViewModel.getEventObservableList());
        if (myEvents == null) {

        } else {
            MyEventAdapter myAdapter = new MyEventAdapter(view.getContext(), myEvents, MyEventsFragment.this);
            listview.setAdapter(myAdapter);
            //------Creation of list of smart devices


            ImageView add = mFragmentMyEventsBinding.imageViewMyeventadd;
        }


    }

    /**
     * Method used to update the view containing the list of events
     */
    @Override
    public void updatelist() {
        setUp();
    }

    /**
     * Method used to delete an event and its associated objects.
     *
     * @param event The Event object to be deleted.
     */
    @Override
    public void deleteEvent(Event event) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete event")
                .setMessage("Do you really want to delete the event: " + event.getName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> getViewModel().deleteEventFromDatabase(event))
                .setNegativeButton(android.R.string.no, null).show();
    }

    /**
     * Method used to update an event object in the database.
     *
     * @param event     The event object to be updated.
     * @param condition The boolean condition describing if the event is active.
     */
    public void updateEvent(Event event, Boolean condition) {
        getViewModel().updateEvent(event, condition);
    }

    /**
     * Method used when the user clicks on add event.
     * This starts a new AddEvent activity.
     */
    public void addNewEvent() {
        Intent intent = new Intent(this.getContext(), AddEvent.class);
        startActivity(intent);
    }

    /**
     * Constructor for the fragment.
     *
     * @return
     */
    public static MyEventsFragment newInstance() {
        Bundle args = new Bundle();
        MyEventsFragment fragment = new MyEventsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Gets the binding variable.
     *
     * @return The binding variable.
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Get id for the layout for this page.
     *
     * @return Layout id.
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_events;
    }

    /**
     * Get the instance of the view model.
     *
     * @return Instance of the view model.
     */
    @Override
    public MyEventsFragmentViewModel getViewModel() {
        return mMyEventsFragmentViewModel;
    }
}
