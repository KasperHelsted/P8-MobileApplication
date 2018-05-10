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
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.utils.CommonUtils;

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

    /**
     * MVVM fields and setup of MVVM
     */
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

    /**
     * 'Setup of MVVM bindings and data at the start of activity
     * @param savedInstanceState the state of the app
     */
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

    /**
     * Setup of UI elements -> fields
     */
    private void setUpBindings() {
        addressTextView  = mActivityEditLocationSettingBinding.addLocation;
        confirmButton = mActivityEditLocationSettingBinding.buttonEditLocationSettingConfirm;
        nameTextView = mActivityEditLocationSettingBinding.textInputLocationName;
    }

    /**
     * Receives the location id from the intent and receives the location object from the db
     */
    private void getLocationToEdit(){
        //Receives data from DB based on ID
        int id = getIntent().getIntExtra("id",0);
        mEditLocationViewModel.getLocationFromId(id);
    }

    /**
     * Renders the fields of the editpage with the location data
     * @param predefinedLocation location object
     * @param coordinate coordinate object corresponding to the predefined location
     */
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

    /**
     * Stops the activity
     */
    @Override
    public void openLocationActivty() {
        finish();

    }

    /**'
     * Opens the createEventMap activity for the user to choose a location
     */
    @Override
    public void openCreateMapActivity() {
        Intent intent = CreateEventMap.newIntent(EditLocationSettingActivity.this);
        startActivityForResult(intent,13);
    }

    /**
     * Dagger
     * @return androidinjector for dagger to use
     */
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    /**
     * Method that submits the edited event to the database
     */
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

    /**
     * Method that catches the result of an intent created in this activity
     * @param requestCode code specified for the result
     * @param resultCode result status code
     * @param data intent with data
     */
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
                    addressTextView.setText(address.getAddressLine(0));
                }
                break;
            }
        }
    }
}