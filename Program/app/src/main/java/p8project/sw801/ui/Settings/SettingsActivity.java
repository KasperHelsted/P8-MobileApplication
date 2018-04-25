package p8project.sw801.ui.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import p8project.sw801.ui.Settings.GlobalMuteSetting.GlobalMuteSettingActivity;
import p8project.sw801.ui.Settings.Location.LocationSettingActivity;
import p8project.sw801.ui.Settings.Shopping.ShoppingSettingActivity;
import p8project.sw801.R;
import p8project.sw801.utils.NotificationUtil;

public class SettingsActivity extends AppCompatActivity {
    //Implement viewmodel here

    //Setup of burger menu
    //private DrawerLayout drawerLayout;
    //private ActionBarDrawerToggle actionBarDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("Notify Me - Settings");
        setContentView(R.layout.activity_settings);


        //--------------------------Navigation bar----------------------------------
        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //--------------------------Navigation bar----------------------------------

        //--------------------------Burger menu-------------------------------------
        //drawerLayout = findViewById(R.id.settingsactivity);
        //actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        //drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //actionBarDrawerToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //NavigationView nv = findViewById(R.id.burgermenu);
        //nv.setNavigationItemSelectedListener(this);
        //--------------------------Burger menu-------------------------------------

        //----------------Rest of the code

        EditText globalmute = findViewById(R.id.editText_globalmute);
        globalmute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, GlobalMuteSettingActivity.class);
                startActivity(intent);
            }
        });

        EditText predefinedlocation = findViewById(R.id.editText_predefinedlocationsettings);
        predefinedlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, LocationSettingActivity.class);
                startActivity(intent);
            }
        });

        EditText preferredshopping = findViewById(R.id.editText_preferredshoppingsettings);
        preferredshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, ShoppingSettingActivity.class);
                startActivity(intent);
            }
        });

    }

}

