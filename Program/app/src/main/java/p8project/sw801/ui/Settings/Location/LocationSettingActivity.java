package p8project.sw801.ui.Settings.Location;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.databinding.ActivityLocationSettingBinding;
import p8project.sw801.ui.Settings.Location.AddLocation.AddLocationSettingActivity;
import p8project.sw801.ui.Settings.Location.EditLocation.EditLocationSettingActivity;
import p8project.sw801.ui.base.BaseActivity;

public class LocationSettingActivity extends BaseActivity<ActivityLocationSettingBinding,LocationViewModel> implements LocationNavigator, HasSupportFragmentInjector {
    private ActivityLocationSettingBinding mActivityLocationSettingBinding;
    @Inject
    LocationViewModel mLocationViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;



    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_location_setting;
    }

    @Override
    public LocationViewModel getViewModel() {
        return mLocationViewModel;
    }

    private ListView listView;
    private ImageView imageView;
    private TextView textView;
    View view;
    private LocationSettingAdapter locationSettingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setup MVVM bindings
        mActivityLocationSettingBinding = getViewDataBinding();
        mLocationViewModel.setNavigator(this);
        setupBindings();
        //Get latest Locations and update ListView
        updateListView();
    }

    private void updateListView(){
        mLocationViewModel.getLatestPredefinedLocationData();
    }

    private void setupBindings() {
        listView = mActivityLocationSettingBinding.listViewMylocationsettings;
        imageView = mActivityLocationSettingBinding.imageViewMysmartdeviceadd;
        textView = mActivityLocationSettingBinding.textViewMysmartdevices;
        view = mActivityLocationSettingBinding.getRoot();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {
        //todo fejl?
    }

    @Override
    public void onLocationClicked(PredefinedLocation predefinedLocation) {
        Intent intent = new Intent(LocationSettingActivity.this, EditLocationSettingActivity.class);
        intent.putExtra("id",predefinedLocation.getId());
        startActivityForResult(intent,1);
    }

    @Override
    public void createLocation() {
        Intent intent = new Intent(LocationSettingActivity.this, AddLocationSettingActivity.class);
        startActivityForResult(intent,2);
    }

    @Override
    public void createList(List<PredefinedLocation> predefinedLocationList) {
        locationSettingAdapter = new LocationSettingAdapter(view.getContext(), (ArrayList<PredefinedLocation>) predefinedLocationList,LocationSettingActivity.this);
        locationSettingAdapter.notifyDataSetChanged();
        listView.deferNotifyDataSetChanged();
        listView.setAdapter(locationSettingAdapter);
        System.out.println("Datasæt changed");

    }

    @Override
    protected void onResume() {
        updateListView();
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1): {
                if (resultCode == Activity.RESULT_OK)
                {
                    updateListView();
                }
                break;
            }
            case(2):{
                if (resultCode == Activity.RESULT_OK)
                {
                    updateListView();
                }
            }
        }
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        updateListView();
    }

}
