package p8project.sw801.ui.event.addeventaccessory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddEventAccessoryBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.addeventhue.AddEventHue;

public class AddEventAccessory extends BaseActivity<ActivityAddEventAccessoryBinding, AddEventAccessoryViewModel> implements AddEventAccessoryNavigator {
    @Inject
    AddEventAccessoryViewModel mAddEventAccessoryViewModel;
    ActivityAddEventAccessoryBinding mActivityAddEventAccessoryBinding;

    private final ArrayList<String> arrayList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddEventAccessoryViewModel.setNavigator(this);
        mActivityAddEventAccessoryBinding = getViewDataBinding();
        setUp();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_event_accessory;
    }

    @Override
    public AddEventAccessoryViewModel getViewModel() {
        return mAddEventAccessoryViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddEventAccessory.class);
        return intent;
    }

    private void setUp(){
        TextView textView = mActivityAddEventAccessoryBinding.textViewAccessory;
        textView.setText(getIntent().getExtras().getString("Name"));

        listView = mActivityAddEventAccessoryBinding.listViewAccessory;
        populateList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO REPLACE STRING WITH OBJECT AND REPLACE IF CONDITIONS WITH VALID CONDITIONS!!

                String accessory = arrayList.get(position);
                if (accessory.equals("Hue - Smart Lights")) {
                    Intent intent = new Intent(AddEventAccessory.this, AddEventHue.class);
                    startActivityForResult(intent, 1);
                    //OPEN HUE PAGE
                } else if (accessory.equals("Nest - Termostat")) {
                    //Open nest page
                }
                //MORE DEVICES POSSIBLE
            }
        });
    }

    private void populateList() {
        //TODO Change to call to viewmodel
        arrayList.add("Hue - Smart Lights");
        arrayList.add("Nest - Termostat");
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_add_event_list_layout, arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null && requestCode == 1) {
            Bundle result = data.getBundleExtra("key");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("key", result);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

}
