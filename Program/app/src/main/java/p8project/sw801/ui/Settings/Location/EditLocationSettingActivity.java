package p8project.sw801.ui.Settings.Location;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import p8project.sw801.R;
import p8project.sw801.ui.AddEvent.CreateEventMapActivity;
/**
 * Created by clubd on 22-03-2018.
 */

public class EditLocationSettingActivity extends AppCompatActivity {

    private String locationSettingName;
    private Bundle addressBundle;
    private Address address;
    private TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_location_setting);
        Intent i = getIntent();
        locationSettingName = i.getStringExtra(locationSettingName);

        final TextView textView = findViewById(R.id.textView_editlocationsettingname);
        textView.setText(locationSettingName);

        final EditText editTextName = findViewById(R.id.textInputLocationName);
        editTextName.setText(locationSettingName);

        addressTextView = findViewById(R.id.addLocation);
    }

    public void showMapActivity(View v){
        Intent mapIntent = new Intent(EditLocationSettingActivity.this, CreateEventMapActivity.class);
        startActivityForResult(mapIntent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {
                    addressBundle = data.getBundleExtra("address");
                    address = addressBundle.getParcelable("address");
                    addressTextView.setText(address.getAddressLine(0)+ ", " + address.getAddressLine(1) + ", " + address.getAddressLine(2));
                }
                break;
            }
        }
    }

}