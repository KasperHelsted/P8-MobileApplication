package p8project.sw801.ui.event.addeventsmartdevice;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddEventSmartDeviceListBinding = getViewDataBinding();
        mAddEventSmartDeviceViewModel.setNavigator(this);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event_smart_device_list;
    }

    @Override
    public AddEventSmartDeviceViewModel getViewModel() {
        return mAddEventSmartDeviceViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventSmartDevice.class);
        return intent;
    }

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

    @Override
    public void updatelist(){
        setUp();
    }

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
