package p8project.sw801.ui.Settings.Location;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityLocationSettingBinding;
import p8project.sw801.ui.Settings.SettingsNavigator;
import p8project.sw801.ui.Settings.SettingsViewModel;
import p8project.sw801.ui.base.BaseActivity;

public class LocationSettingActivity extends BaseActivity<ActivityLocationSettingBinding,SettingsViewModel> implements SettingsNavigator, HasSupportFragmentInjector {
    private ActivityLocationSettingBinding mActivityLocationSettingBinding;
    private SettingsViewModel mSettingsViewModel;
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
        return R.layout.activity_add_location_setting;
    }

    @Override
    public SettingsViewModel getViewModel() {
        mSettingsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SettingsViewModel.class);
        return mSettingsViewModel;
    }

    private ListView listview;
    ArrayList<String> locationSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_setting);
        mActivityLocationSettingBinding = getViewDataBinding();
        mSettingsViewModel.setNavigator(this);

        listview = (ListView) this.findViewById(R.id.listView_mylocationsettings);

        //------Creation of list of smart devices
        locationSettings = new ArrayList<String>();
        locationSettings.add("Home sweet home");

        LocationSettingAdapter myAdapter = new LocationSettingAdapter(this, locationSettings);


        listview.setAdapter(myAdapter);
        //------Creation of list of smart devices

        ImageView add = findViewById(R.id.imageView_mysmartdeviceadd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LocationSettingActivity.this, AddLocationSettingActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {
        //todo fejl?
    }
}
