package p8project.sw801.ui.event.addeventnest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
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
    private EditText seekbarEditText;
    private SeekBar.OnSeekBarChangeListener mSeekBarChangeListener;

    /**
     * Creates a new AddEventAccessory intent.
     *
     * @param context The current context of the application.
     * @return The created intent.
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventNest.class);
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
        mAddEventNestViewModel.setNavigator(this);
        mActivityAddEventNestBinding = getViewDataBinding();
        bindings();
        fetchData();
        setUp();


    }

    /**
     * Sets up the different components on the page
     */
    public void setUp() {

        deviceNameTextView.setText("Device: " + mySmartDevice.getDeviceName());
        assesoryNameTextView.setText("Thermostat: " + myAccessory.getName());
        seekBarNest.setProgress(20);
        seekbarEditText.setText(String.valueOf(seekBarNest.getProgress()));

        mSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekbarEditText.setText(String.valueOf(progress));
                if (seekbarEditText.getText() != null) {
                    seekbarEditText.setSelection(seekbarEditText.getText().length());
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        seekBarNest.setOnSeekBarChangeListener(mSeekBarChangeListener);

        seekbarEditText.addTextChangedListener(new MyTextWatcher(seekbarEditText));
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
        myAccessory = new Gson().fromJson(jsonMyAccessory, NestThermostat.class);
    }

    /**
     * Setup bindings between the elements in the xml and the variables used to access these.
     */
    private void bindings() {
        deviceNameTextView = mActivityAddEventNestBinding.textViewSmartDeviceNameNest;
        assesoryNameTextView = mActivityAddEventNestBinding.textViewAccessoryNameNest;
        turnOnButton = mActivityAddEventNestBinding.buttonNestOn;
        turnOffButton = mActivityAddEventNestBinding.buttonNestOff;
        confirmButtom = mActivityAddEventNestBinding.buttonConfirmTemp;
        seekBarNest = mActivityAddEventNestBinding.seekBarNest;
        seekbarEditText = mActivityAddEventNestBinding.editTextSeekBarNest;
    }

    /**
     * Method used when the user chooses the turn thermostat on option. This method creates a trigger object and returns this to the previous activity.
     */
    @Override
    public void turnOn() {
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
    public void turnOff() {
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
    public void setTemp() {
        Trigger t = new Trigger();
        t.setNotification(true);
        t.setNotificationText(mySmartDevice.getDeviceName() + " " + myAccessory.getName() + " Adjust temperature to: " + seekBarNest.getProgress());
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
        return R.layout.activity_add_event_nest;
    }

    /**
     * Get the instance of the view model.
     *
     * @return Instance of the view model.
     */
    @Override
    public AddEventNestViewModel getViewModel() {
        return mAddEventNestViewModel;
    }

    /**
     * Custom TextWatcher used to enable a user to enter a value in the text field and also change the seekbar
     */
    public class MyTextWatcher implements TextWatcher {
        private EditText et;

        // Pass the EditText instance to TextWatcher by constructor
        public MyTextWatcher(EditText et) {
            this.et = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (et.getText() != null) {


                String strEnteredVal = et.getText().toString();
                if (!strEnteredVal.equals("")) {
                    int num = Integer.parseInt(strEnteredVal);
                    if (num > 50) {
                        et.removeTextChangedListener(this);
                        et.setText("50");
                        seekBarNest.setProgress(50);
                        et.addTextChangedListener(this);
                    } else if (num < 0) {
                        et.removeTextChangedListener(this);
                        et.setText(String.valueOf(0));
                        seekBarNest.setProgress(0);
                        et.addTextChangedListener(this);
                    } else {
                        et.removeTextChangedListener(this);
                        et.setText(String.valueOf(num));
                        seekBarNest.setProgress(num);
                        et.addTextChangedListener(this);
                    }


                }
            }
        }
    }
}


