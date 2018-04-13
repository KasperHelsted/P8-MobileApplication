package p8project.sw801.ui.Settings.Location.AddLocation;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.ui.event.createeventmap.CreateEventMapViewModel;

/**
 * Created by clubd on 22-03-2018.
 */

public class AddLocationSettingActivity extends BaseActivity<ActivityAddLocationSettingBinding,AddLocationViewModel> implements AddLocationNavigator, HasSupportFragmentInjector {
    private ActivityAddLocationSettingBinding mActivityAddLocationSettingBinding;
    private AddLocationViewModel mAddLocationViewModel;
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
    public AddLocationViewModel getViewModel() {
        mAddLocationViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddLocationViewModel.class);

        return mAddLocationViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_location_setting);
        mActivityAddLocationSettingBinding = getViewDataBinding();
        mAddLocationViewModel.setNavigator(this);

        setTitle("Notify me - Add predefined location");

       /* final Button buttonSettings = findViewById(R.id.button_savePredefinedLocation);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddLocationSettingActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });*/


    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openCreateMapActivity() {
        System.out.println("LULKAGEMAND");
        Intent intent = CreateEventMap.newIntent(AddLocationSettingActivity.this);
        startActivity(intent);
        //finish();
    }
}
