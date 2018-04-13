package p8project.sw801.ui.Settings.Shopping;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityShoppingSettingBinding;
import p8project.sw801.ui.base.BaseActivity;

public class ShoppingSettingActivity extends BaseActivity<ActivityShoppingSettingBinding,ShoppingSettingViewModel> implements ShoppingSettingNavigator {

    @Inject
    ShoppingSettingViewModel mShoppingSettingViewModel;
    private ActivityShoppingSettingBinding mActivityShoppingSettingBinding;

    private ListView listview ;
    private SearchView searchView;
    private ArrayList<String> list;
    private SparseBooleanArray sparseBooleanArray ;
    private ArrayAdapter<String > adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityShoppingSettingBinding = getViewDataBinding();
        mShoppingSettingViewModel.setNavigator(this);
        setUp();

    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shopping_setting;
    }

    @Override
    public ShoppingSettingViewModel getViewModel() {
        return mShoppingSettingViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }
    private void setUp(){
        searchView = mActivityShoppingSettingBinding.searchView;
        listview = mActivityShoppingSettingBinding.listView;

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
                adapter.getFilter().filter(query);
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

