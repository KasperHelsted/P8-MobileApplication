package p8project.sw801.ui.event.editevent;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.ActivityEditEventBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.custom.FragmentCallback;
import p8project.sw801.ui.event.choosenotificationorsmartdevice.ChooseNotificationOrSmartDevice;
import p8project.sw801.ui.event.editevent.triggersList.TriggerListAdapter;
import p8project.sw801.ui.event.locationpicker.LocationPicker;

public class EditEvent extends BaseActivity<ActivityEditEventBinding, EditEventViewModel> implements EditEventNavigator, TriggerListAdapter.TriggerListListener, HasSupportFragmentInjector, FragmentCallback {
    @Inject
    TriggerListAdapter mTriggerListAdapter;
    ActivityEditEventBinding mActivityEditEventBinding;

    @Inject
    LinearLayoutManager mLayoutManager;

    @Inject
    EditEventViewModel mEditEventViewModel;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    /**
     * Dynamic method to create new intent
     *
     * @param context Context
     * @return intent status
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditEvent.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_event;
    }

    @Override
    public EditEventViewModel getViewModel() {
        return mEditEventViewModel;
    }

    /**
     * '
     * Sets up the activity with data to edit
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEditEventViewModel.setNavigator(this);
        mTriggerListAdapter.setListener(this);

        mActivityEditEventBinding = getViewDataBinding();

        mEditEventViewModel.loadInitialEvent(
                getIntent().getIntExtra("event_id", -1)
        );

        setUp();
        subscribeToLiveData();
    }

    @Override
    public void addEventTrigger() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(ChooseNotificationOrSmartDevice.TAG)
                .add(R.id.placementfragment, ChooseNotificationOrSmartDevice.newInstance(), ChooseNotificationOrSmartDevice.TAG)
                .commit();
    }

    @Override
    public void addTrigger() {

    }

    /**
     * Deletes a trigger
     *
     * @param trigger trigger to delete
     */
    @Override
    public void deleteTrigger(Trigger trigger) {
        mEditEventViewModel.deleteTrigger(trigger);
    }

    public void deleteEvent(Event event) {
        new AlertDialog.Builder(this)
                .setTitle("Delete event")
                .setMessage("Do you really want to delete the event: " + event.getName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> getViewModel().deleteEventFromDatabase(event))
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onItemClick(Trigger trigger) {

    }

    /**
     * Initial setup
     */
    private void setUp() {
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mActivityEditEventBinding.recyclerViewMyTriggers.setLayoutManager(mLayoutManager);
        mActivityEditEventBinding.recyclerViewMyTriggers.setItemAnimator(new DefaultItemAnimator());
        mActivityEditEventBinding.recyclerViewMyTriggers.setAdapter(mTriggerListAdapter);
    }

    /**
     * Subscription to live data
     */
    private void subscribeToLiveData() {
        mEditEventViewModel.getEventTriggersListLiveData().observe(this, triggers -> mEditEventViewModel.addTriggersToList(triggers));
    }


    @Override
    public void chooseLocation() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(LocationPicker.TAG)
                .add(R.id.placementfragment, LocationPicker.newInstance(), LocationPicker.TAG)
                .commit();
    }

    @Override
    public void cancelEditEvent() {
        finish();
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    private void removeFragment(String TAG) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;
        String predefinedLocationJSON = "";
        Bundle result = data.getExtras();
        if (result != null) {
            predefinedLocationJSON = result.getString("key");
        }

        switch (requestCode) {
            case 0: {
                removeFragment(LocationPicker.TAG);

                Bundle addressBundle = data.getBundleExtra("address");

                Address address = addressBundle.getParcelable("address");

                mEditEventViewModel.setAddress(address);

                break;
            }
            case 2: {
                removeFragment(LocationPicker.TAG);


                PredefinedLocation predefinedLocation = new Gson().fromJson(predefinedLocationJSON, PredefinedLocation.class);
                mEditEventViewModel.setPredefinedLocation(predefinedLocation);

                break;
            }
            case 3: {
                removeFragment(ChooseNotificationOrSmartDevice.TAG);

                Trigger trigger = new Gson().fromJson(predefinedLocationJSON, Trigger.class);

                mEditEventViewModel.addTrigger(trigger);

                break;
            }
        }
    }


    @Override
    public void fragmentCallback(int resultCode, String data) {
        switch (resultCode) {
            case 0: {
                removeFragment(ChooseNotificationOrSmartDevice.TAG);

                Trigger trigger = new Trigger();

                // Notification only
                trigger.setNotification(false);
                trigger.setAction(0);
                trigger.setNotificationText(data);

                mEditEventViewModel.addTrigger(trigger);

                break;
            }
            case 1: {
                removeFragment(LocationPicker.TAG);

                PredefinedLocation predefinedLocation = new Gson().fromJson(data, PredefinedLocation.class);

                mEditEventViewModel.setPredefinedLocation(predefinedLocation);

                break;
            }
        }
    }

    @Override
    public void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public String reverseGeocode(double lat, double lon) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList != null) {
                return addressList.get(0).getAddressLine(0);
            }
        } catch (Exception e) {
        }

        return "Address could not be resolved";
    }
}
