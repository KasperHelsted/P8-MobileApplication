package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddGlobalMuteBinding;
import p8project.sw801.ui.base.BaseActivity;


/**
 * Created by clubd on 21-03-2018.
 */

public class AddGlobalMuteSettingActivity extends BaseActivity<ActivityAddGlobalMuteBinding, AddGlobalMuteSettingViewModel> implements AddGlobalMuteSettingNavigator, HasSupportFragmentInjector {
    //    @Inject
//    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    AddGlobalMuteSettingViewModel mAddGlobalMuteSettingViewModel;
    private ActivityAddGlobalMuteBinding mActivityAddGlobalMuteBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    /*
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    */
    private TextView globalMuteName;
    static private EditText betweenTime = null;
    static private EditText betweenTimeTwo = null;
    static private EditText comment = null;
    private Button confirm;
    private Spinner spinnerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_global_mute);
        mAddGlobalMuteSettingViewModel.setNavigator(this);
        mActivityAddGlobalMuteBinding = getViewDataBinding();

        setupBindings();

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
    public void showTimePickerDialog(int i) {
        if (i == 1){
            DialogFragment newFragment = new AddGlobalMuteSettingActivity.TimePickerFragment1();
            newFragment.show(getFragmentManager(), "timePicker");
        }
        else{
            DialogFragment newFragment = new AddGlobalMuteSettingActivity.TimePickerFragment2();
            newFragment.show(getFragmentManager(), "timePicker");
        }

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public static class TimePickerFragment1 extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            betweenTime.setText(time);
        }
    }

    public static class TimePickerFragment2 extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            betweenTimeTwo.setText(time);
        }
    }

    public void closeAddGlobalMute(View v){finish();}

    @Override
    public void submitGlobalMuteClick(){
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

    private void setupBindings(){
        betweenTime = mActivityAddGlobalMuteBinding.editTextTimeBetween;
        betweenTimeTwo = mActivityAddGlobalMuteBinding.editTextTimeBetween2;
        spinnerLocation = mActivityAddGlobalMuteBinding.spinnerLocation;
    }

//    @Override
//    public AndroidInjector<Fragment> supportFragmentInjector() {
//        return fragmentDispatchingAndroidInjector;
//    }
}

