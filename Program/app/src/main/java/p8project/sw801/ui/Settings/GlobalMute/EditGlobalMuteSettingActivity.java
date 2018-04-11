package p8project.sw801.ui.Settings.GlobalMute;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import p8project.sw801.R;
/**
 * Created by clubd on 21-03-2018.
 */

public class EditGlobalMuteSettingActivity extends AppCompatActivity {

    private String globalSettingName;
    private TextView globalMuteName;
    static private EditText betweenTime = null;
    static private EditText betweenTimeTwo = null;
    static private EditText comment = null;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_global_mute);
        Intent i = getIntent();
        globalSettingName = i.getStringExtra(globalSettingName);

        final TextView textView = findViewById(R.id.textView_editglobalmutename);
        textView.setText(globalSettingName);

        final EditText editTextName = findViewById(R.id.textInputGlobalMuteName);
        editTextName.setText(globalSettingName);

        final Spinner spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);

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

        globalMuteName = findViewById(R.id.textInputGlobalMuteName);
        betweenTime = findViewById(R.id.editTextTimeBetween);
        betweenTimeTwo = findViewById(R.id.editTextTimeBetween2);
        comment = findViewById(R.id.editTextComment);
        confirm = findViewById(R.id.buttonEditGlobalMuteConfirm);

        // Click events
        betweenTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, 1);
            }
        });
        betweenTimeTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(v, 2);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gName = globalMuteName.getText().toString();
                String commentText = comment.getText().toString();
                Integer location = spinnerLocation.getSelectedItemPosition();
                Long startTime = Long.parseLong(betweenTime.getText().toString());
                Long endTime = Long.parseLong(betweenTimeTwo.getText().toString());
                //TODO Connect with ViewModel
                //List<TIGGER_TYPE>
            }
        });

    }
    public void showTimePickerDialog(View v, int i) {
        if (i == 1){
            DialogFragment newFragment = new EditGlobalMuteSettingActivity.TimePickerFragment1();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        }
        else{
            DialogFragment newFragment = new EditGlobalMuteSettingActivity.TimePickerFragment2();
            newFragment.show(getSupportFragmentManager(), "timePicker");
        }

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
}