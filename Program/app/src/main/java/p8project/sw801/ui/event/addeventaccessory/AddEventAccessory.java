package p8project.sw801.ui.event.addeventaccessory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.databinding.ActivityAddEventAccessoryBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.addeventhue.AddEventHue;
import p8project.sw801.ui.event.addeventnest.AddEventNest;

public class AddEventAccessory extends BaseActivity<ActivityAddEventAccessoryBinding, AddEventAccessoryViewModel> implements AddEventAccessoryNavigator {
    @Inject
    AddEventAccessoryViewModel mAddEventAccessoryViewModel;
    ActivityAddEventAccessoryBinding mActivityAddEventAccessoryBinding;

    private ArrayList<HueLightbulbWhite> hueArrayList = new ArrayList<>();
    private ArrayList<NestThermostat> nestThermostatArrayList = new ArrayList<>();
    private ListView listView;
    private SmartDevice mSmartDevice;

    /**
     * On create method for AddEvent. Instantiates and sets up all required fields for the page.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddEventAccessoryViewModel.setNavigator(this);
        mActivityAddEventAccessoryBinding = getViewDataBinding();
        mSmartDevice = decode();
        mAddEventAccessoryViewModel.getListFromDb(mSmartDevice);
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
        return R.layout.activity_add_event_accessory;
    }

    /**
     * Get the instance of the view model.
     * @return Instance of the view model.
     */
    @Override
    public AddEventAccessoryViewModel getViewModel() {
        return mAddEventAccessoryViewModel;
    }

    /**
     * Creates a new AddEventAccessory intent.
     * @param context The current context of the application.
     * @return The created intent.
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventAccessory.class);
        return intent;
    }

    /**
     * Method used to create the page. This method sets the adapter for the listview depending on which smart device have been chosen from the previous activity.
     */
    private void setUp(){
        TextView textView = mActivityAddEventAccessoryBinding.textViewAccessory;
        textView.setText(mSmartDevice.getDeviceName());
        listView = mActivityAddEventAccessoryBinding.listViewAccessory;

        if (mSmartDevice.getInternalIdentifier() == 1){
            hueArrayList.addAll(mAddEventAccessoryViewModel.getHueObservableList());

            //Adapter code
            customHueAdapter c = new customHueAdapter(this, hueArrayList);

            listView.setAdapter(c);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(AddEventAccessory.this, AddEventHue.class);
                    intent.putExtra("device", new Gson().toJson(mSmartDevice));
                    intent.putExtra("accessory", new Gson().toJson(hueArrayList.get(position)));
                    //Send smartdevice and accessory objects
                    startActivityForResult(intent, 1);

                }
            });


        }else if (mSmartDevice.getInternalIdentifier() == 2){
            nestThermostatArrayList.addAll(mAddEventAccessoryViewModel.getNestObservableList());

            customNestAdapter c = new customNestAdapter(this, nestThermostatArrayList);
            listView.setAdapter(c);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(AddEventAccessory.this, AddEventNest.class);
                    intent.putExtra("device", new Gson().toJson(mSmartDevice));
                    intent.putExtra("accessory", new Gson().toJson(nestThermostatArrayList.get(position)));
                    //Send smartdevice and accessory objects
                    startActivityForResult(intent, 1);
                }
            });
        }





    }

    /**
     * Method used to get the smart device object passed from the previous activity.
     * @return Returns the smart device object.
     */
    public SmartDevice decode(){
        String jsonMyObject ="";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("device");
        }
        SmartDevice myObject = new Gson().fromJson(jsonMyObject, SmartDevice.class);
        return myObject;
    }

    /**
     * Method used to update the list showed on the page
     */
    @Override
    public void updatelist(){
        setUp();
    }

    /**
     * The adapter used for showing use lights
     */
    private class customHueAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<HueLightbulbWhite> Title;

        public customHueAdapter(Context context, ArrayList<HueLightbulbWhite> text1) {
            mContext = context;
            Title = text1;
        }

        @Override
        public int getCount() {
            return Title.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View row;
            row = inflater.inflate(R.layout.addeventsmartdevicelist, parent, false);
            TextView t = row.findViewById(R.id.textview_addEventSmartDeviceList);
            t.setText(Title.get(position).getDeviceName());
            return (row);
        }
    }

    /**
     * The adapter used for showing the nest thermostats
     */
    private class customNestAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<NestThermostat> Title;

        public customNestAdapter(Context context, ArrayList<NestThermostat> text1) {
            mContext = context;
            Title = text1;
        }

        @Override
        public int getCount() {
            return Title.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View row;
            row = inflater.inflate(R.layout.addeventsmartdevicelist, parent, false);
            TextView t = row.findViewById(R.id.textview_addEventSmartDeviceList);
            t.setText(Title.get(position).getName());
            return (row);
        }
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
        if (data != null && requestCode == 1) {
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
