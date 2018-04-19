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
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbRGB;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddEventHueViewModel.setNavigator(this);
        mActivityAddEventHueBinding = getViewDataBinding();
        bindings();
        fetchData();
        setUp();
    }

    private void fetchData(){
        String jsonMyObject ="";
        String jsonMyAccessory="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("device");
            jsonMyAccessory = extras.getString("accessory");
        }
        mySmartDevice = new Gson().fromJson(jsonMyObject, SmartDevice.class);
        myAccessory = new Gson().fromJson(jsonMyAccessory, HueLightbulbWhite.class);
    }

    public void setUp(){
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

    private void bindings(){
        deviceNameTextView = mActivityAddEventHueBinding.textViewSmartDeviceName;
        accessoryNameTextView = mActivityAddEventHueBinding.textViewAccessoryName;
        seekBar = mActivityAddEventHueBinding.seekBar2;
        seekBarTextView = mActivityAddEventHueBinding.textViewSeekBarPrecentage;
    }

    @Override
    public void turnOn(){
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
    @Override
    public void turnOff(){
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
    @Override
    public void setBrightness(){
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


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event_hue;
    }

    @Override
    public AddEventHueViewModel getViewModel() {
        return mAddEventHueViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventHue.class);
        return intent;
    }
}
