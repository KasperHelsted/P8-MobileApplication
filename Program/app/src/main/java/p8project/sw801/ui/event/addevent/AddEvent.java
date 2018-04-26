package p8project.sw801.ui.event.addevent;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.databinding.ActivityAddEventBinding;
import p8project.sw801.ui.AddEvent.AddEventAdapter;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.ui.event.notificationorsmartdevice.NotificationOrSmartdevice;
import p8project.sw801.utils.NotificationUtil;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityBasedNotifications;
import p8project.sw801.utils.TimeBasedNotifications.TimeBasedNotification;


public class AddEvent extends BaseActivity<ActivityAddEventBinding, AddEventViewModel> implements AddEventNavigator, HasSupportFragmentInjector {
    @Inject
    AddEventViewModel mAddEventViewModel;
    private ActivityAddEventBinding mActivityAddEventBinding;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    public ArrayList<Trigger> addMyEvents;
    public AddEventAdapter myAdapter;
    private Bundle addressBundle;
    private Address address;
    private Coordinate coordinate;
    private TextView addressTextView;
    private TextView textViewTime;
    private TextView textViewBetweenTime;
    private TextView addEvent;
    static private EditText AtTime = null;
    static private EditText betweenTime = null;
    private Button confirm;
    private TextView eventName;
    private ArrayList<Integer> markedButtons;
    private LinearLayout doThis;
    private Spinner spinner;
    private Spinner spinnerLocation;
    private Event newEvent;
    private When newWhen;
    static private int startHour;
    static private int startMin;
    static private int endHour;
    static private int endMin;
    static private Date startDate;
    static private Date endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddEventBinding = getViewDataBinding();
        mAddEventViewModel.setNavigator(this);
        //Setup bindings
        setupBindings();
        //---Calls the mark functions to mark a day in the day picker.
        markButton();
        //------Creation of list of "do this"
        addMyEvents = new ArrayList<Trigger>();
        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("No time condition chosen");
        categories.add("Before this time");
        categories.add("At this time");
        categories.add("After this time");
        categories.add("Between these times");

        List<String> categoriesLocation = new ArrayList<String>();
        categoriesLocation.add("No Location condition chosen");
        categoriesLocation.add("At location");
        categoriesLocation.add("Near Location");
        categoriesLocation.add("Leaving Location");
        categoriesLocation.add("Predefined Location");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesLocation);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinnerLocation.setAdapter(dataAdapterLocation);

        //Show/hide location options
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 4) {
                    textViewTime.setVisibility(View.VISIBLE);
                    AtTime.setEnabled(true);
                    AtTime.setVisibility(View.VISIBLE);
                    textViewBetweenTime.setVisibility(View.VISIBLE);
                    betweenTime.setEnabled(true);
                    betweenTime.setVisibility(View.VISIBLE);
                } else if (position == 0) {
                    textViewTime.setVisibility(View.GONE);
                    AtTime.setEnabled(false);
                    AtTime.setVisibility(View.GONE);
                    textViewBetweenTime.setVisibility(View.GONE);
                    betweenTime.setEnabled(false);
                    betweenTime.setVisibility(View.GONE);
                } else {
                    textViewTime.setVisibility(View.VISIBLE);
                    AtTime.setEnabled(true);
                    AtTime.setVisibility(View.VISIBLE);
                    textViewBetweenTime.setVisibility(View.GONE);
                    betweenTime.setEnabled(false);
                    betweenTime.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        //Show/hide time options
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position == 0) {
                    addressTextView.setVisibility(View.GONE);
                } else if (position == 4) {
                    //TODO OPEN NEW ACTIVITY/FRAGMENT/SPINNER AND CALL VIEWMODEL TO GET DATA
                } else {
                    addressTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event;
    }

    @Override
    public AddEventViewModel getViewModel() {
        return mAddEventViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void openCreateMapActivity() {
        Intent intent = CreateEventMap.newIntent(AddEvent.this);
        startActivityForResult(intent, 0);
    }

    @Override
    public void showNotificationOrSmartdevice() {
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .add(R.id.event_activity_layout, NotificationOrSmartdevice.newInstance(), NotificationOrSmartdevice.TAG)
                .commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void showTimePickerDialog(int i) {
        if (i == 1) {
            DialogFragment newFragment = new TimePickerFragment1();
            newFragment.show(getFragmentManager(), "timepicker");
        } else {
            DialogFragment newFragment = new TimePickerFragment2();
            newFragment.show(getFragmentManager(), "timePicker");
        }

    }

    public static class TimePickerFragment1 extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            startHour = hourOfDay;
            startMin = minute;
            //startDate = timeFormat.parse(hourOfDay + ":" + minute);
            String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            AtTime.setText(time);
        }
    }

    public static class TimePickerFragment2 extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            endHour = hourOfDay;
            endMin = minute;
            //endDate = timeFormat.parse(hourOfDay + ":" + minute);

            String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            betweenTime.setText(time);
        }
    }


    public void closeAddEvent(View v) {
        finish();
    }

    private void markButton() {
        markedButtons = new ArrayList<Integer>();

        //Day buttons
        ToggleButton tMon;
        ToggleButton tThu;
        ToggleButton tWen;
        ToggleButton tTue;
        ToggleButton tFri;
        ToggleButton tSat;
        ToggleButton tSun;


        tMon = (ToggleButton) findViewById(R.id.tD);
        tThu = (ToggleButton) findViewById(R.id.tL);
        tWen = (ToggleButton) findViewById(R.id.tM);
        tTue = (ToggleButton) findViewById(R.id.tMi);
        tFri = (ToggleButton) findViewById(R.id.tJ);
        tSat = (ToggleButton) findViewById(R.id.tV);
        tSun = (ToggleButton) findViewById(R.id.tS);

        //Check individual items.
        if (tMon.isChecked()) {
            markedButtons.add(1);
        }
        if (tThu.isChecked()) {
            markedButtons.add(2);
        }
        if (tWen.isChecked()) {
            markedButtons.add(3);
        }
        if (tTue.isChecked()) {
            markedButtons.add(4);
        }
        if (tFri.isChecked()) {
            markedButtons.add(5);
        }
        if (tSat.isChecked()) {
            markedButtons.add(6);
        }
        if (tSun.isChecked()) {
            markedButtons.add(7);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case (0): {
                    if (resultCode == Activity.RESULT_OK) {
                        addressBundle = data.getBundleExtra("address");
                        address = addressBundle.getParcelable("address");
                        addressTextView.setText(address.getAddressLine(0) + ", " + address.getAddressLine(1) + ", " + address.getAddressLine(2));
                    }
                    break;
                }
            }
        }
    }

    public void refreshData() {
        ArrayList<Trigger> a = new ArrayList<>();
        a.clear();
        a.addAll(addMyEvents);

        doThis.removeAllViews();

        myAdapter = new AddEventAdapter(this, a);

        final int adapterCount = myAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = myAdapter.getView(i, null, null);
            doThis.addView(item);
        }
    }

    public void deleteItem(int pos) {
        addMyEvents.remove(pos);
        refreshData();
    }

    @Override
    public void submitEventClick() {
        newEvent = new Event();
        newWhen = new When();
        //TODO
        //Crashes if any of the attributes we try to set is NULL
        //Refreshing the MyEvent page after creating the new event

        //--Creating event--
        newEvent.setName(eventName.getText().toString());
        newEvent.setActive(true);
        mAddEventViewModel.saveEvent(newEvent);

        //--Creating When--
        newWhen.setTimeCondition(spinner.getSelectedItemPosition());
        newWhen.setLocationCondition(spinnerLocation.getSelectedItemPosition());
        newWhen.setStartHour(startHour);
        newWhen.setStartMinute(startMin);
        newWhen.setEndHour(endHour);
        newWhen.setEndMinute(endMin);
        //newWhen.setStartTime(startDate.getTime());
        //newWhen.setEndTime(endDate.getTime());

        //Checking which days have been marked, and set the list of weekdays
        markButton();
        try {
            newWhen.setListWeekDays(markedButtons);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Make coordinate and save to DB
        coordinate = new Coordinate(address.getLatitude(), address.getLongitude());
        mAddEventViewModel.saveCoordinate(newWhen, addMyEvents, coordinate);


        finish();
    }

    private void setupBindings() {
        doThis = (LinearLayout) mActivityAddEventBinding.linearLayoutAddEvent;
        spinner = (Spinner) mActivityAddEventBinding.spinnerWhen;
        spinnerLocation = (Spinner) mActivityAddEventBinding.spinnerLocation;
        AtTime = mActivityAddEventBinding.editTextTime;
        betweenTime = mActivityAddEventBinding.editTextTimeBetween;
        addressTextView = mActivityAddEventBinding.addLocation;
        textViewTime = mActivityAddEventBinding.textViewTime;
        textViewBetweenTime = mActivityAddEventBinding.textViewBetweenTime;
        confirm = mActivityAddEventBinding.buttonCreateEvent;
        eventName = mActivityAddEventBinding.textInputEventName;
        addEvent = mActivityAddEventBinding.addEventTriggerStatic;
    }
}
