package p8project.sw801.ui.Settings.Location.AddLocation;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityAddLocationSettingBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;

/**
 * Created by clubd on 22-03-2018.
 */

public class AddLocationSettingActivity extends BaseActivity<ActivityAddLocationSettingBinding,AddLocationViewModel> implements AddLocationNavigator, HasSupportFragmentInjector {
    private ActivityAddLocationSettingBinding mActivityAddLocationSettingBinding;
    @Inject
    AddLocationViewModel mAddLocationViewModel;

    private Bundle addressBundle;
    private Address address;
    //private TextView addLocation;
    private TextView addressTextView;
    private TextView nameTextView;
    private Button confirmButton;

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
        setupBindings();
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

    private void setupBindings() {
        addressTextView  = mActivityAddLocationSettingBinding.addLocation;
        confirmButton = mActivityAddLocationSettingBinding.buttonSavePredefinedLocation;
        nameTextView = mActivityAddLocationSettingBinding.textInputGlobalMuteName;
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
        Intent intent = CreateEventMap.newIntent(AddLocationSettingActivity.this);
        startActivityForResult(intent,42);
    }

    @Override
    public void submitLocationClick() {
        String locName = nameTextView.getText().toString();
        Address addressToSend = address;
        mAddLocationViewModel.submitLocationToDatabase(locName,addressToSend);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (42): {
                if (resultCode == Activity.RESULT_OK) {
                    addressBundle = data.getBundleExtra("address");
                    address = addressBundle.getParcelable("address");
                    addressTextView.setText(address.getAddressLine(0) + ", " + address.getAddressLine(1) + ", " + address.getAddressLine(2));
                }
                break;
            }
        }
    }
}
