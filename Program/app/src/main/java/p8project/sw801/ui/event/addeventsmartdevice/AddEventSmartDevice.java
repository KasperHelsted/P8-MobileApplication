package p8project.sw801.ui.event.addeventsmartdevice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddEventSmartDeviceListBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.addeventaccessory.AddEventAccessory;

public class AddEventSmartDevice extends BaseActivity<ActivityAddEventSmartDeviceListBinding, AddEventSmartDeviceViewModel> implements AddEventSmartDeviceNavigator {
    @Inject
    AddEventSmartDeviceViewModel mAddEventSmartDeviceViewModel;
    ActivityAddEventSmartDeviceListBinding mActivityAddEventSmartDeviceListBinding;

    private final ArrayList<String> arrayList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddEventSmartDeviceListBinding = getViewDataBinding();
        mAddEventSmartDeviceViewModel.setNavigator(this);
        setUp();

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


        listView = mActivityAddEventSmartDeviceListBinding.listViewSmartDevice;
        populateList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AddEventSmartDevice.this, AddEventAccessory.class);
                intent.putExtra("Name", arrayList.get(position));
                startActivityForResult(intent, 1);
            }
        });
    }

    private void populateList(){
        //TODO Change to call to viewmodel
        arrayList.add("Hue - Smart Lights");
        arrayList.add("Nest - Termostat");
        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.activity_add_event_list_layout, arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == 1){
            Bundle result = data.getBundleExtra("key");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("key", result);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

}
