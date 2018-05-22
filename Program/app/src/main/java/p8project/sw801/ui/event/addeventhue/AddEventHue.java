package p8project.sw801.ui.event.addeventhue;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.Gson;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.ActivityAddEventHueBinding;
import p8project.sw801.ui.base.BaseActivity;

public class AddEventHue extends BaseActivity<ActivityAddEventHueBinding, AddEventHueViewModel> implements AddEventHueNavigator {

    @Inject
    AddEventHueViewModel mAddEventHueViewModel;
    ActivityAddEventHueBinding mActivityAddEventHueBinding;

    private SmartDevice mySmartDevice;
    private HueLightbulbWhite myAccessory;
    private TextView deviceNameTextView;
    private TextView accessoryNameTextView;
    private SeekBar seekBar;
    private TextView seekBarTextView;

    /**
     * Creates a new AddEventAccessory intent.
     *
     * @param context The current context of the application.
     * @return The created intent.
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventHue.class);
        return intent;
    }

    /**
     * On create method for AddEvent. Instantiates and sets up all required fields for the page.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddEventHueViewModel.setNavigator(this);
        mActivityAddEventHueBinding = getViewDataBinding();
        bindings();
        fetchData();
        setUp();
    }

    /**
     * Fetches the data passed from the previous activity.
     */
    private void fetchData() {
        String jsonMyObject = "";
        String jsonMyAccessory = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("device");
            jsonMyAccessory = extras.getString("accessory");
        }
        mySmartDevice = new Gson().fromJson(jsonMyObject, SmartDevice.class);
        myAccessory = new Gson().fromJson(jsonMyAccessory, HueLightbulbWhite.class);
    }

    /**
     * Sets up the different components on the page
     */
    public void setUp() {
        deviceNameTextView.setText("Device: " + mySmartDevice.getDeviceName());
        accessoryNameTextView.setText("Light: " + myAccessory.getDeviceName());
        seekBar.setProgress(125);
        seekBarTextView.setText(String.valueOf(seekBar.getProgress()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarTextView.setText(String.valueOf(progress));
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
     * Setup bindings between the elements in the xml and the variables used to access these.
     */
    private void bindings() {
        deviceNameTextView = mActivityAddEventHueBinding.textViewSmartDeviceName;
        accessoryNameTextView = mActivityAddEventHueBinding.textViewAccessoryName;
        seekBar = mActivityAddEventHueBinding.seekBar2;
        seekBarTextView = mActivityAddEventHueBinding.textViewSeekBarPrecentage;
    }

    /**
     * Method used when the user chooses the turn light on option. This method creates a trigger object and returns this to the previous activity.
     */
    @Override
    public void turnOn() {
        Trigger t = new Trigger();
        t.setNotification(true);
        t.setNotificationText(mySmartDevice.getDeviceName() + " " + myAccessory.getDeviceName() + " Turn on");
        t.setSmartDeviceId(mySmartDevice.getId());
        t.setAccessorieId(myAccessory.getId());
        t.setAction(1);

        Intent resultintent = new Intent();
        resultintent.putExtra("key", new Gson().toJson(t));
        setResult(Activity.RESULT_OK, resultintent);
        finish();


    }

    /**
     * Method used when the user chooses the turn light off option. This method creates a trigger object and returns this to the previous activity.
     */
    @Override
    public void turnOff() {
        Trigger t = new Trigger();
        t.setNotification(true);
        t.setNotificationText(mySmartDevice.getDeviceName() + " " + myAccessory.getDeviceName() + " Turn off");
        t.setSmartDeviceId(mySmartDevice.getId());
        t.setAccessorieId(myAccessory.getId());
        t.setAction(2);

        Intent resultintent = new Intent();
        resultintent.putExtra("key", new Gson().toJson(t));
        setResult(Activity.RESULT_OK, resultintent);
        finish();
    }

    /**
     * Method used when the user chooses the brightness option. This method creates a trigger object and returns this to the previous activity.
     */
    @Override
    public void setBrightness() {
        Trigger t = new Trigger();
        t.setNotification(true);
        t.setNotificationText(mySmartDevice.getDeviceName() + " " + myAccessory.getDeviceName() + " Adjust brightness to: " + seekBar.getProgress());
        t.setSmartDeviceId(mySmartDevice.getId());
        t.setAccessorieId(myAccessory.getId());
        t.setAction(3);
        t.setValue(seekBar.getProgress());

        Intent resultintent = new Intent();
        resultintent.putExtra("key", new Gson().toJson(t));
        setResult(Activity.RESULT_OK, resultintent);
        finish();
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
        return R.layout.activity_add_event_hue;
    }

    /**
     * Get the instance of the view model.
     *
     * @return Instance of the view model.
     */
    @Override
    public AddEventHueViewModel getViewModel() {
        return mAddEventHueViewModel;
    }
}
