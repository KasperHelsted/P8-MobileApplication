package p8project.sw801.ui.event.addeventnest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.ActivityAddEventNestBinding;
import p8project.sw801.ui.base.BaseActivity;

public class AddEventNest extends BaseActivity<ActivityAddEventNestBinding, AddEventNestViewModel> implements AddEventNestNavigator {

    @Inject
    AddEventNestViewModel mAddEventNestViewModel;
    ActivityAddEventNestBinding mActivityAddEventNestBinding;

    private SmartDevice mySmartDevice;
    private NestThermostat myAccessory;
    private TextView deviceNameTextView;
    private TextView assesoryNameTextView;
    private Button turnOnButton;
    private Button turnOffButton;
    private Button confirmButtom;
    private SeekBar seekBarNest;
    private TextView seekbarTextView;

    /**
     * On create method for AddEvent. Instantiates and sets up all required fields for the page.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddEventNestViewModel.setNavigator(this);
        mActivityAddEventNestBinding  = getViewDataBinding();
        bindings();
        fetchData();
        setUp();

    }

    /**
     * Sets up the different components on the page
     */
    public void setUp(){
        deviceNameTextView.setText("Device: " + mySmartDevice.getDeviceName());
        assesoryNameTextView.setText("Thermostat: " + myAccessory.getName());
        seekBarNest.setProgress(20);
        seekbarTextView.setText(String.valueOf(seekBarNest.getProgress()));

        seekBarNest.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarTextView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Fetches the data passed from the previous activity.
     */
    private void fetchData(){
        String jsonMyObject ="";
        String jsonMyAccessory="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("device");
            jsonMyAccessory = extras.getString("accessory");
        }
        mySmartDevice = new Gson().fromJson(jsonMyObject, SmartDevice.class);
        myAccessory = new Gson().fromJson(jsonMyAccessory, NestThermostat.class);
    }

    /**
     * Setup bindings between the elements in the xml and the variables used to access these.
     */
    private void bindings(){
        deviceNameTextView = mActivityAddEventNestBinding.textViewSmartDeviceNameNest;
        assesoryNameTextView = mActivityAddEventNestBinding.textViewAccessoryNameNest;
        turnOnButton = mActivityAddEventNestBinding.buttonNestOn;
        turnOffButton = mActivityAddEventNestBinding.buttonNestOff;
        confirmButtom = mActivityAddEventNestBinding.buttonConfirmTemp;
        seekBarNest = mActivityAddEventNestBinding.seekBarNest;
        seekbarTextView = mActivityAddEventNestBinding.textViewSeekBarNest;
    }

    /**
     * Method used when the user chooses the turn thermostat on option. This method creates a trigger object and returns this to the previous activity.
     */
    @Override
    public void turnOn(){
        Trigger t = new Trigger();
        t.setNotification(true);
        t.setNotificationText(mySmartDevice.getDeviceName() + " " + myAccessory.getName() + " Turn on");
        t.setSmartDeviceId(mySmartDevice.getId());
        t.setAccessorieId(myAccessory.getId());
        t.setAction(4);

        Intent resultintent = new Intent();
        resultintent.putExtra("key", new Gson().toJson(t));
        setResult(Activity.RESULT_OK, resultintent);
        finish();
    }

    /**
     * Method used when the user chooses the turn thermostat off option. This method creates a trigger object and returns this to the previous activity.
     */
    @Override
    public void turnOff(){
        Trigger t = new Trigger();
        t.setNotification(true);
        t.setNotificationText(mySmartDevice.getDeviceName() + " " + myAccessory.getName() + " Turn off");
        t.setSmartDeviceId(mySmartDevice.getId());
        t.setAccessorieId(myAccessory.getId());
        t.setAction(5);

        Intent resultintent = new Intent();
        resultintent.putExtra("key", new Gson().toJson(t));
        setResult(Activity.RESULT_OK, resultintent);
        finish();
    }

    /**
     * Method used when the user chooses the turn thermostat on option. This method creates a trigger object and returns this to the previous activity.
     */
    @Override
    public void setTemp(){
        Trigger t = new Trigger();
        t.setNotification(true);
        t.setNotificationText(mySmartDevice.getDeviceName() + " " + myAccessory.getName() + " Adjust brightness to: " + seekBarNest.getProgress());
        t.setSmartDeviceId(mySmartDevice.getId());
        t.setAccessorieId(myAccessory.getId());
        t.setAction(6);
        t.setValue(seekBarNest.getProgress());

        Intent resultintent = new Intent();
        resultintent.putExtra("key", new Gson().toJson(t));
        setResult(Activity.RESULT_OK, resultintent);
        finish();
    }

    /**
     * Gets the binding variable.
     * @return The binding variable.
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Get id for the layout for this page.
     * @return Layout id.
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event_nest;
    }

    /**
     * Get the instance of the view model.
     * @return Instance of the view model.
     */
    @Override
    public AddEventNestViewModel getViewModel() {
        return mAddEventNestViewModel;
    }

    /**
     * Creates a new AddEventAccessory intent.
     * @param context The current context of the application.
     * @return The created intent.
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventNest.class);
        return intent;
    }
}
