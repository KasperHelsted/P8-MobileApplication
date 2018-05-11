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
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.databinding.ActivityAddLocationSettingBinding;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.utils.CommonUtils;


public class AddLocationSettingActivity extends BaseActivity<ActivityAddLocationSettingBinding, AddLocationViewModel> implements AddLocationNavigator, HasSupportFragmentInjector {
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

    /**
     * MVVM setup
     */
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

    /**
     * Setup the view and MVVM
     *
     * @param savedInstanceState state of the app
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddLocationSettingBinding = getViewDataBinding();
        mAddLocationViewModel.setNavigator(this);
        setupBindings();
    }

    /**
     * Setup from ui components to fields
     */
    private void setupBindings() {
        addressTextView = mActivityAddLocationSettingBinding.addLocation;
        confirmButton = mActivityAddLocationSettingBinding.buttonSavePredefinedLocation;
        nameTextView = mActivityAddLocationSettingBinding.textInputGlobalMuteName;
    }

    /**
     * daggger
     *
     * @return dagger injector
     */
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    /**
     * Opens the createmap activity
     */
    @Override
    public void openCreateMapActivity() {
        Intent intent = CreateEventMap.newIntent(AddLocationSettingActivity.this);
        startActivityForResult(intent, 42);
    }

    /**
     * Submits the location to the database
     */
    @Override
    public void submitLocationClick() {
        String locName = nameTextView.getText().toString();
        try {
            if (!CommonUtils.isNullOrEmpty(locName) && coords.getLongitude() != 0 && coords != null) {
                mAddLocationViewModel.submitLocationToDatabase(locName, coords);
            } else {
                Toast.makeText(this, "You must specify a name", Toast.LENGTH_SHORT).show();
            }
        } catch (NullPointerException e) {
            Toast.makeText(this, "You must set a location", Toast.LENGTH_SHORT).show();
        } catch (OnErrorNotImplementedException f) {
            Toast.makeText(this, "You must set a location", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Finishes current activity and sends activity information to the Location page
     *
     * @param pred
     */
    @Override
    public void openLocationActivty(PredefinedLocation pred) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("predId", pred.getId());
        resultIntent.putExtra("predName", pred.getName());
        resultIntent.putExtra("predCoordId", pred.getCoordinateId());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    /**
     * method to catch intent results
     *
     * @param requestCode the request identifier
     * @param resultCode  status of the result
     * @param data        intent with data
     */
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
                    addressTextView.setText(address.getAddressLine(0));
                }
                break;
            }
        }
    }
}
