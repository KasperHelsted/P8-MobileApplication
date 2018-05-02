package p8project.sw801.ui.event.createeventmap;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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


    private EditText editText;
    private Address a;
    private Geocoder geocoder;
    private Marker marker;
    private GoogleMap gmap;
    private Location location;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCreateEventMapBinding = getViewDataBinding();
        mCreateEventMapViewModel.setNavigator(this);
        editText = mActivityCreateEventMapBinding.editTextCreateEventMapAddressbar;
        setUp();
    }

    public void setUp() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void cancelButton() {
        finish();
    }

    @Override
    public void confirmButton() {
        Intent resultIntent = new Intent();

        Bundle b = new Bundle();
        b.putParcelable("address", a);
        b.putParcelable("location", location);
        resultIntent.putExtra("address", b);
        resultIntent.putExtra("location", b);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
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
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(8);
        getLocationPermission();

        gmap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.remove();
                marker = gmap.addMarker(new MarkerOptions().position(latLng).title("Chosen position"));
                a = convertCoordinateToAddress(latLng);
                if (a != null){
                    editText.setText(a.getAddressLine(0) + ", " + a.getAddressLine(1) + ", " + a.getAddressLine(2));
                }
            }
        });

    }

    public Address convertCoordinateToAddress(LatLng latLng) {
        Address address = null;
        geocoder = new Geocoder(this, Locale.getDefault());
        double lat = latLng.latitude;
        double lon = latLng.longitude;
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList != null){
            address = addressList.get(0);
            }else{
                Toast.makeText(this, "Could not get address try again", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not get address try again", Toast.LENGTH_SHORT).show();
        }
        return address;
    }

    private void prepMap() {
        // Get current location
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));


        if (location == null){
            location = mCreateEventMapViewModel.setDefaultLocation();
        }

        //Set current location on map
        LatLng currentLoc = new LatLng(location.getLatitude(), location.getLongitude());

        marker = gmap.addMarker(new MarkerOptions().position(currentLoc).title("Current position"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 12.0f));

        a = convertCoordinateToAddress(currentLoc);
        //Write address in textfield
        if (a != null){
            editText.setText(a.getAddressLine(0)+ ", " + a.getAddressLine(1) + ", " + a.getAddressLine(2));
        }


        //Current location button on map
        gmap.setMyLocationEnabled(true);
        gmap.setOnMyLocationButtonClickListener(this);
        gmap.setOnMyLocationClickListener(this);
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            prepMap();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    prepMap();
                }
                else{
                    Intent resultIntent = new Intent();
                    setResult(Activity.RESULT_CANCELED, resultIntent);
                    finish();
                }
            }
        }
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
