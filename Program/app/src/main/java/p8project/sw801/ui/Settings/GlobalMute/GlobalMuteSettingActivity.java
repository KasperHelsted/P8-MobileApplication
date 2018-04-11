package p8project.sw801.ui.Settings.GlobalMute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import p8project.sw801.R;

public class GlobalMuteSettingActivity extends AppCompatActivity {

    private ListView listview;
    ArrayList<String> globalMuteSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_mute);


        listview = (ListView) this.findViewById(R.id.listView_myglobalmutesettings);

        //------Creation of list of smart devices
        globalMuteSettings = new ArrayList<String>();
        globalMuteSettings.add("Off at home");

        GlobalMuteSettingAdapter myAdapter = new GlobalMuteSettingAdapter(this, globalMuteSettings);


        listview.setAdapter(myAdapter);
        //------Creation of list of smart devices

        ImageView add = findViewById(R.id.imageView_globalmuteadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GlobalMuteSettingActivity.this, AddGlobalMuteSettingActivity.class);
                startActivity(intent);
            }
        });

    }
}