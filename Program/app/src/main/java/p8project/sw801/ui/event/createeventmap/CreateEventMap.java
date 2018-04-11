package p8project.sw801.ui.event.createeventmap;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.databinding.ActivityCreateEventMapBinding;
import p8project.sw801.ui.base.BaseActivity;

public class CreateEventMap extends BaseActivity<ActivityCreateEventMapBinding, CreateEventMapViewModel> implements CreateEventMapNavigator, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, HasSupportFragmentInjector {
    @Inject
    CreateEventMapViewModel mCreateEventMapViewModel;
    private ActivityCreateEventMapBinding mActivityCreateEventMapBinding;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private GoogleMap gmap;
    private static LocationManager locManager;
    private Marker marker;
    private static Geocoder geocoder;
    private EditText editText;
    private Button cancelButton;
    private Button confirmButton;
    private Address a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCreateEventMapBinding = getViewDataBinding();
        mCreateEventMapViewModel.setNavigator(this);
    }


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_event_map;
    }

    @Override
    public CreateEventMapViewModel getViewModel() {
        return mCreateEventMapViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        gmap.setMinZoomPreference(8);

        // Get current location
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        //Set current location on map
        LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());

        marker = gmap.addMarker(new MarkerOptions().position(currentLoc).title("Current position"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 12.0f));

        //Write address in textfield
        a = convertCoordinateToAddress(currentLoc);
        editText.setText(a.getAddressLine(0) + ", " + a.getAddressLine(1) + ", " + a.getAddressLine(2));

        //Current location button on map
        gmap.setMyLocationEnabled(true);
        gmap.setOnMyLocationButtonClickListener(this);
        gmap.setOnMyLocationClickListener(this);


        gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.remove();
                marker = gmap.addMarker(new MarkerOptions().position(latLng).title("Chosen position"));
                a = convertCoordinateToAddress(latLng);
                editText.setText(a.getAddressLine(0) + ", " + a.getAddressLine(1) + ", " + a.getAddressLine(2));
            }
        });
    }

    private Address convertCoordinateToAddress(LatLng latLng) {
        //TODO MAKE DEFAULT ADDRESS TO RETURN TO AVOID NULL
        Address address = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        double lat = latLng.latitude;
        double lon = latLng.longitude;
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
            address = addressList.get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CreateEventMap.class);
        return intent;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
