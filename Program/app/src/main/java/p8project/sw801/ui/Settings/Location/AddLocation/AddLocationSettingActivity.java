package p8project.sw801.ui.Settings.Location.AddLocation;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.exceptions.OnErrorNotImplementedException;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.databinding.ActivityAddLocationSettingBinding;
import p8project.sw801.ui.Settings.Location.LocationSettingActivity;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.utils.CommonUtils;

/**
 * Created by clubd on 22-03-2018.
 */

public class AddLocationSettingActivity extends BaseActivity<ActivityAddLocationSettingBinding,AddLocationViewModel> implements AddLocationNavigator, HasSupportFragmentInjector {
    private ActivityAddLocationSettingBinding mActivityAddLocationSettingBinding;
    @Inject
    AddLocationViewModel mAddLocationViewModel;

    private Bundle addressBundle;
    private Bundle locationBundle;
    private Address address;
    private TextView addressTextView;
    private TextView nameTextView;
    private Button confirmButton;
    private Coordinate coords;
    private Location loc;

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
        return mAddLocationViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddLocationSettingBinding = getViewDataBinding();
        mAddLocationViewModel.setNavigator(this);
        setupBindings();
        setTitle("Notify me - Add predefined location");
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
        try{
            if (!CommonUtils.isNullOrEmpty(locName)&& coords.getLongitude() != 0 && coords !=null){
                mAddLocationViewModel.submitLocationToDatabase(locName,coords);
            }
            else {
                Toast.makeText(this, "You must specify a name", Toast.LENGTH_SHORT).show();
            }
        }catch (NullPointerException e){
            Toast.makeText(this, "You must set a location", Toast.LENGTH_SHORT).show();
        } catch (OnErrorNotImplementedException f){
        Toast.makeText(this, "You must set a location", Toast.LENGTH_SHORT).show();
    }
    }

    @Override
    public void openLocationActivty()
    {
       finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (42): {
                if (resultCode == Activity.RESULT_OK) {
                    addressBundle = data.getBundleExtra("address");
                    locationBundle = data.getBundleExtra("location");
                    address = addressBundle.getParcelable("address");
                    loc = locationBundle.getParcelable("location");
                    coords = new Coordinate(loc);
                    addressTextView.setText(address.getAddressLine(0) + ", " + address.getAddressLine(1) + ", " + address.getAddressLine(2));
                }
                break;
            }
        }
    }
}
