package p8project.sw801.ui.Settings.Shopping;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.Chain;
import p8project.sw801.databinding.ActivityShoppingSettingBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.utils.KeyBoardUtil;

public class ShoppingSettingActivity extends BaseActivity<ActivityShoppingSettingBinding, ShoppingSettingViewModel> implements ShoppingSettingNavigator {

    @Inject
    ShoppingSettingViewModel mShoppingSettingViewModel;
    private ActivityShoppingSettingBinding mActivityShoppingSettingBinding;

    private ListView listview;
    private SearchView searchView;
    private ArrayList<Chain> list;
    private customAdapter _customAdapter;

    /**
     * Sets up MVVM on creation of the page
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityShoppingSettingBinding = getViewDataBinding();
        mShoppingSettingViewModel.setNavigator(this);
        setupBindings();
        KeyBoardUtil.setHideKeyboardOnTouch(this, findViewById(R.id.shoppingsettingpage));
    }

    /**
     * @return the viewmodelbinding
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Specifices which layout belongs to this acitivity
     *
     * @return layout id
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_shopping_setting;
    }

    /**
     * @return the current viewmodel
     */
    @Override
    public ShoppingSettingViewModel getViewModel() {
        return mShoppingSettingViewModel;
    }

    /**
     * Sets up the MVVM bindings from UI elements -> fields
     */
    private void setupBindings() {
        searchView = mActivityShoppingSettingBinding.searchView;
        searchView.setFocusable(false);
        listview = mActivityShoppingSettingBinding.listView;
    }

    /**
     * Updates the UI elements on the page with data
     */
    private void setUp() {
        list = new ArrayList<>();
        list.addAll(mShoppingSettingViewModel.getChainsObservableList());
        _customAdapter = new customAdapter(ShoppingSettingActivity.this, list);
        listview.setAdapter(_customAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Chain item = list.get(position);
                if (item.isActive()) {
                    item.setActive(false);
                } else if (!item.isActive()) {
                    item.setActive(true);
                }

                mShoppingSettingViewModel.updateChain(item);


                Toast.makeText(ShoppingSettingActivity.this, item.getBrandName(), Toast.LENGTH_SHORT).show();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                _customAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                _customAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    /**
     * Method to update the shoppinglist data
     */
    @Override
    public void updateShoppingList() {
        setUp();
    }

    /**
     * In-line adapter used for displaying data
     */
    private class customAdapter extends BaseAdapter implements Filterable {

        private Context mContext;
        private ArrayList<Chain> mChainArrayList;

        public customAdapter(Context context, ArrayList<Chain> chainArrayList) {
            mContext = context;
            mChainArrayList = chainArrayList;
        }

        @Override
        public int getCount() {
            return mChainArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater chainLayout = LayoutInflater.from(mContext);
            View chainView;
            chainView = chainLayout.inflate(R.layout.chain_list, parent, false);
            CheckedTextView chainsView = chainView.findViewById(R.id.chainList);
            chainsView.setText(mChainArrayList.get(position).getBrandName());
            ((ListView) parent).setItemChecked(position, mChainArrayList.get(position).isActive());

            return chainView;
        }

        @Override
        public Filter getFilter() {
            filter_here filterChains = new filter_here();
            return filterChains;
        }


        public class filter_here extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults Result = new FilterResults();
                // if constraint is empty return the original names
                if (constraint.length() == 0) {
                    Result.values = list;
                    Result.count = list.size();
                    return Result;
                }

                ArrayList<Chain> Filtered_Names = new ArrayList<Chain>();
                String filterString = constraint.toString().toLowerCase();
                String filterableString;

                for (int i = 0; i < list.size(); i++) {
                    filterableString = list.get(i).getBrandName();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        Filtered_Names.add(list.get(i));
                    }
                }
                Result.values = Filtered_Names;
                Result.count = Filtered_Names.size();

                return Result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mChainArrayList = (ArrayList<Chain>) results.values;
                notifyDataSetChanged();
            }
        }

    }
}

