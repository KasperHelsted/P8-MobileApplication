package p8project.sw801.ui.event.createeventmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import p8project.sw801.R;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class CreateEventMapViewModel extends BaseViewModel<CreateEventMapNavigator> {
    public CreateEventMapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public Location setDefaultLocation(){
        Location l = new Location("");
        l.setLatitude(57.016959);
        l.setLongitude(9.991390);
        return l;

    }

    public void cancleButton(){
        getNavigator().cancelButton();
    }
    public void confirmButton(){
        getNavigator().confirmButton();
    }

}
