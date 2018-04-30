package p8project.sw801.ui.SmartDevice.AddNestSmartDevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.nestlabs.sdk.Camera;
import com.nestlabs.sdk.GlobalUpdate;
import com.nestlabs.sdk.Metadata;
import com.nestlabs.sdk.NestAPI;
import com.nestlabs.sdk.NestException;
import com.nestlabs.sdk.NestListener;
import com.nestlabs.sdk.NestToken;
import com.nestlabs.sdk.SmokeCOAlarm;
import com.nestlabs.sdk.Structure;
import com.nestlabs.sdk.Thermostat;

import java.util.ArrayList;

import p8project.sw801.R;

public class AddNestSmartDevice extends AppCompatActivity {

    public TextInputEditText clientId;
    public TextInputEditText secret;
    public Button confirmButton;
    public Button cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nest_smart_device);

        clientId = findViewById(R.id.clientid);
        secret = findViewById(R.id.clientsecret);
        confirmButton = findViewById(R.id.button_nestconfirm);
        cancelButton = findViewById(R.id.button_nestcancel);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = clientId.getText().toString();
                String clientSecret = secret.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("id",id);
                returnIntent.putExtra("secret",clientSecret);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
