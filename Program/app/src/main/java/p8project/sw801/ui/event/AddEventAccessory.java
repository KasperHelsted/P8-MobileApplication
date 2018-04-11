package p8project.sw801.ui.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import p8project.sw801.R;

public class AddEventAccessory extends AppCompatActivity {
    private final ArrayList<String> arrayList = new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_accessory);
        TextView textView = findViewById(R.id.textViewAccessory);
        textView.setText(getIntent().getExtras().getString("Name"));

        listView = findViewById(R.id.listViewAccessory);
        populateList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //TODO REPLACE STRING WITH OBJECT AND REPLACE IF CONDITIONS WITH VALID CONDITIONS!!

                String accessory = arrayList.get(position);
                if (accessory.equals("Hue - Smart Lights")){
                    Intent intent = new Intent(AddEventAccessory.this, AddEventHue.class);
                    startActivityForResult(intent, 1);
                    //OPEN HUE PAGE
                }else if(accessory.equals("Nest - Termostat")){
                    //Open nest page
                }
                //MORE DEVICES POSSIBLE
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
        if (requestCode == 1){
            Bundle result = data.getBundleExtra("key");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("key", result);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}
