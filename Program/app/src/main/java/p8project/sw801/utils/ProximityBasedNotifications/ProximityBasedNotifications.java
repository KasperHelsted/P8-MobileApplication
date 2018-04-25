package p8project.sw801.utils.ProximityBasedNotifications;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.Coordinate;


public class ProximityBasedNotifications {
    private Context mContext;
    private static LocationManager locationManager;


    public ProximityBasedNotifications(Context base) {
        mContext = base;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void createProximityNotification(Coordinate coordinate, int requestCode, EventWithData eventWithData) {

        Integer radius = 500;

        Intent intent = new Intent(mContext, ProximityReceiver.class);

        intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));


        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, 0);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else{
            locationManager.addProximityAlert(coordinate.getLatitude(), coordinate.getLongitude(), radius, -1, pendingIntent);

            Log.i("Alarm", "Proximity added ");
        }

    }

    public void cancelProximity(int requestCode, EventWithData eventWithData){
        Intent intent = new Intent(mContext, ProximityReceiver.class);
        intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, 0);
        locationManager.removeProximityAlert(pendingIntent);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        Log.i("Alarm", "Proximity deleted at: " + sdf.format(new Date()));
    }
}
