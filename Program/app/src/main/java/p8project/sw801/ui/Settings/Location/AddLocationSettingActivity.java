package p8project.sw801.ui.Settings.Location;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import p8project.sw801.R;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;


/**
 * Created by clubd on 22-03-2018.
 */

public class AddLocationSettingActivity extends AppCompatActivity {

    private Bundle addressBundle;
    private Address address;
    private TextView addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_setting);
        setTitle("Notify me - Add predefined location");

        addressTextView = findViewById(R.id.addLocation);

    }

    public void showMapActivity(View v){
        Intent mapIntent = new Intent(AddLocationSettingActivity.this, CreateEventMap.class);
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
