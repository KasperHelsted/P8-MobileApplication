package p8project.sw801.ui.Settings.Location;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddLocationSettingBinding;
import p8project.sw801.ui.MapsActivity;
import p8project.sw801.ui.Settings.SettingsNavigator;
import p8project.sw801.ui.Settings.SettingsViewModel;
import p8project.sw801.ui.base.BaseActivity;

/**
 * Created by clubd on 22-03-2018.
 */

public class AddLocationSettingActivity extends BaseActivity<ActivityAddLocationSettingBinding,SettingsViewModel> implements SettingsNavigator, HasSupportFragmentInjector {
    private ActivityAddLocationSettingBinding mActivityAddLocationSettingBinding;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_setting);


        setTitle("Notify me - Add predefined location");

        final Button buttonSettings = findViewById(R.id.button_savePredefinedLocation);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddLocationSettingActivity.this, MapsActivity.class);
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

    }
}
