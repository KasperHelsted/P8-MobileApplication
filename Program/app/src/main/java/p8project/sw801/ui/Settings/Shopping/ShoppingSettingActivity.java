package p8project.sw801.ui.Settings.Shopping;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import p8project.sw801.R;

public class ShoppingSettingActivity extends AppCompatActivity {

    ListView listview ;
    SearchView searchView;
    ArrayList<String> list;

    SparseBooleanArray sparseBooleanArray ;

    ArrayAdapter<String > adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_setting);

        searchView = findViewById(R.id.searchView);
        listview = findViewById(R.id.listView);

        list = new ArrayList<>();
        list.add("Netto");
        list.add("Føtex");
        list.add("Bilka");
        list.add("Salling");
        list.add("MENY");
        list.add("SPAR");
        list.add("Min Købmand");
        list.add("Let-Køb");
        list.add("Kvickly");
        list.add("SuperBrugsen");
        list.add("Dagli'Brugsen");
        list.add("LokalBrugsen");
        list.add("Irma");
        list.add("fakta");
        list.add("fakta Q");
        list.add("ALDI");
        list.add("Lidl");


        adapter = new ArrayAdapter<>
                (ShoppingSettingActivity.this,
                        android.R.layout.simple_list_item_multiple_choice,
                        android.R.id.text1, list );

        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                sparseBooleanArray = listview.getCheckedItemPositions();

                String ValueHolder = "" ;

                int i = 0 ;

                while (i < sparseBooleanArray.size()) {

                    if (sparseBooleanArray.valueAt(i)) {

                        ValueHolder += list.get(sparseBooleanArray.keyAt(i)) + ",";
                    }

                    i++ ;
                }

                ValueHolder = ValueHolder.replaceAll("(,)*$", "");

                if (ValueHolder != null && !ValueHolder.isEmpty()) {

                    Toast.makeText(ShoppingSettingActivity.this, "Selected shops = " + ValueHolder, Toast.LENGTH_LONG).show();
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(ShoppingSettingActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}