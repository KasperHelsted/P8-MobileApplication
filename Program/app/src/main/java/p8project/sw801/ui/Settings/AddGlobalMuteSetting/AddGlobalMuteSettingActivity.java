package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddGlobalMuteBinding;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog.CustomTimePickerCallback;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog.TimePickerDialog;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.base.BaseViewModel;


/**
 * Created by clubd on 21-03-2018.
 */

public class AddGlobalMuteSettingActivity extends BaseActivity<ActivityAddGlobalMuteBinding, AddGlobalMuteSettingViewModel> implements AddGlobalMuteSettingNavigator, HasSupportFragmentInjector, CustomTimePickerCallback {

    @Inject
    AddGlobalMuteSettingViewModel mAddGlobalMuteSettingViewModel;
    private ActivityAddGlobalMuteBinding mActivityAddGlobalMuteBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private BaseViewModel callback;

    private TextView globalMuteName;
    static private EditText betweenTime = null;
    static private EditText betweenTimeTwo = null;
    static private EditText comment = null;
    private Button confirm;
    private Spinner spinnerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddGlobalMuteSettingViewModel.setNavigator(this);
        mActivityAddGlobalMuteBinding = getViewDataBinding();
        setupBindings();
        setUp();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_global_mute;
    }

    @Override
    public AddGlobalMuteSettingViewModel getViewModel() {
        return mAddGlobalMuteSettingViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void showTimePickerDialog(BaseViewModel viewModel) {
        callback = viewModel;

        TimePickerDialog.newInstance().show(getSupportFragmentManager());
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    public void closeAddGlobalMute(View v) {
        finish();
    }

    @Override
    public void submitGlobalMuteClick() {
        String gName = globalMuteName.getText().toString();
        String commentText = comment.getText().toString();
        Integer locationCondition = spinnerLocation.getSelectedItemPosition();
        Long startTime = Long.parseLong(betweenTime.getText().toString());
        Long endTime = Long.parseLong(betweenTimeTwo.getText().toString());
        //List<TIGGER_TYPE>

        //Calling Viewmodel Still missing correct parameters
        mAddGlobalMuteSettingViewModel.submitEventToDatabase();
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, AddGlobalMuteSettingActivity.class);
        return intent;
    }

    private void setupBindings() {
        betweenTime = mActivityAddGlobalMuteBinding.editTextTimeBetween;
        betweenTimeTwo = mActivityAddGlobalMuteBinding.editTextTimeBetween2;
        spinnerLocation = mActivityAddGlobalMuteBinding.spinnerLocation;
    }

    private void setUp() {
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        List<String> categoriesLocation = new ArrayList<String>();
        categoriesLocation.add("No location chosen");
        categoriesLocation.add("Home");
        categoriesLocation.add("Work");

        ArrayAdapter<String> dataAdapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesLocation);
        dataAdapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(dataAdapterLocation);

    }

    @Override
    public void onTimeSet(long datTime) {
        callback.callbackTimePicker(datTime);
    }
}

