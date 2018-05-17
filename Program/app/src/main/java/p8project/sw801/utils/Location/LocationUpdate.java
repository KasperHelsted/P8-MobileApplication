package p8project.sw801.utils.Location;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;


public class LocationUpdate extends Service {

    private static LocationManager locationManager = null;

    @Override
    public void onCreate() {

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (locationManager == null){
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 10, locationListener);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Define a listener that responds to location updates
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {}

        public void onProviderEnabled(String provider) {}

        public void onProviderDisabled(String provider) {}
    };

}
