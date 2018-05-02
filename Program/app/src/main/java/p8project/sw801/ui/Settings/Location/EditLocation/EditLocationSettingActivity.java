package p8project.sw801.ui.Settings.Location.EditLocation;

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
import p8project.sw801.databinding.ActivityEditLocationSettingBinding;
import p8project.sw801.ui.Settings.Location.LocationSettingActivity;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.utils.CommonUtils;

/**
 * Created by clubd on 22-03-2018.
 */

public class EditLocationSettingActivity extends BaseActivity<ActivityEditLocationSettingBinding,EditLocationViewModel> implements EditLocationNavigator, HasSupportFragmentInjector {
    private Bundle addressBundle;
    private Bundle locationBundle;
    private Address address;
    private TextView addressTextView;
    private TextView nameTextView;
    private Button confirmButton;
    private Coordinate coords;
    private Location loc;
    private PredefinedLocation predLoc;

    private ActivityEditLocationSettingBinding mActivityEditLocationSettingBinding;
    @Inject
    EditLocationViewModel mEditLocationViewModel;
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
        return R.layout.activity_edit_location_setting;
    }

    @Override
    public EditLocationViewModel getViewModel() {
        return mEditLocationViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setup MVVM
        mEditLocationViewModel.setNavigator(this);
        mActivityEditLocationSettingBinding = getViewDataBinding();
        setUpBindings();
        //Setup Input fields based on clicked intent
        getLocationToEdit();
    }

    private void getLocationToEdit(){
        //Receives data from DB based on ID
        int id = getIntent().getIntExtra("id",0);
        mEditLocationViewModel.getLocationFromId(id);
    }

    public void renderFields(PredefinedLocation predefinedLocation, Coordinate coordinate){
        if (predefinedLocation != null){
            nameTextView.setText(predefinedLocation.getName());
            coords = coordinate;
            predLoc = predefinedLocation;
            Address add = CommonUtils.convertCoordinateToAddress(coordinate.getLatitude(),coordinate.getLongitude(),this);
            addressTextView.setText(add.getAddressLine(0) + ", " + add.getAddressLine(1) + ", " + add.getAddressLine(2));
        }
        else{
            nameTextView.setText("null");
            addressTextView.setText("null");
        }

    }

    @Override
    public void openLocationActivty() {
        finish();

    }

    private void setUpBindings() {
        addressTextView  = mActivityEditLocationSettingBinding.addLocation;
        confirmButton = mActivityEditLocationSettingBinding.buttonEditLocationSettingConfirm;
        nameTextView = mActivityEditLocationSettingBinding.textInputLocationName;
    }

    @Override
    public void openCreateMapActivity() {
        Intent intent = CreateEventMap.newIntent(EditLocationSettingActivity.this);
        startActivityForResult(intent,13);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void submitEditLocationClick() {
        try{
            String locName = nameTextView.getText().toString();
            if (!CommonUtils.isNullOrEmpty(locName) && coords.getLongitude() != 0){
                mEditLocationViewModel.updatePredefinedLoc(coords, locName,predLoc);
            }
            else {
                Toast.makeText(this, "You must specify a name", Toast.LENGTH_SHORT).show();
            }
        }catch (NullPointerException e){
            Toast.makeText(this, "You must set a location", Toast.LENGTH_SHORT).show();
        }catch (OnErrorNotImplementedException f){
            Toast.makeText(this, "You must set a location", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (13): {
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