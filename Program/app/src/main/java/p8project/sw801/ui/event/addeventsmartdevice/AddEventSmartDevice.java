package p8project.sw801.ui.event.addeventsmartdevice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.ActivityAddEventSmartDeviceListBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.addeventaccessory.AddEventAccessory;

public class AddEventSmartDevice extends BaseActivity<ActivityAddEventSmartDeviceListBinding, AddEventSmartDeviceViewModel> implements AddEventSmartDeviceNavigator {
    @Inject
    AddEventSmartDeviceViewModel mAddEventSmartDeviceViewModel;
    ActivityAddEventSmartDeviceListBinding mActivityAddEventSmartDeviceListBinding;

    private ArrayList<SmartDevice> arrayList = new ArrayList<>();
    private ListView listView;

    /**
     * On create method for AddEventSmartDevice. Instantiates and sets up all required fields for the page.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddEventSmartDeviceListBinding = getViewDataBinding();
        mAddEventSmartDeviceViewModel.setNavigator(this);
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
        return R.layout.activity_add_event_smart_device_list;
    }

    /**
     * Get the instance of the view model.
     * @return Instance of the view model.
     */
    @Override
    public AddEventSmartDeviceViewModel getViewModel() {
        return mAddEventSmartDeviceViewModel;
    }

    /**
     * Creates a new AddEventAccessory intent.
     * @param context The current context of the application.
     * @return The created intent.
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventSmartDevice.class);
        return intent;
    }

    /**
     * Method used to create the page. This method sets the adapter for the listview.
     */
    private void setUp(){
        arrayList = new ArrayList<>();

        listView = mActivityAddEventSmartDeviceListBinding.listViewSmartDevice;
        arrayList.addAll(mAddEventSmartDeviceViewModel.getEventObservableList());
        customSDAdapter a = new customSDAdapter(this, arrayList);
        listView.setAdapter(a);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AddEventSmartDevice.this, AddEventAccessory.class);
                intent.putExtra("device", new Gson().toJson(arrayList.get(position)));
                startActivityForResult(intent, 1);
            }
        });

    }

    /**
     * Method used to update the list showing the smart devices
     */
    @Override
    public void updatelist(){
        setUp();
    }

    /**
     * Method used to catch the results from other activites that have been created from this one.
     * The returned result is either a trigger for a hue light or a trigger for a nest thermostat. This result is passed on to the activity that started this one.
     * @param requestCode The code used when creating the returned activity.
     * @param resultCode The result code from the returned activity.
     * @param data The intent attached to the returning activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == 1){
            String jsonMyObject ="";
            Bundle result = data.getExtras();
            if (result != null) {
                jsonMyObject = result.getString("key");
            }
            Trigger t = new Gson().fromJson(jsonMyObject, Trigger.class);
            Intent resultintent = new Intent();
            resultintent.putExtra("key", new Gson().toJson(t));
            setResult(Activity.RESULT_OK, resultintent);
            finish();
        }
    }



}
