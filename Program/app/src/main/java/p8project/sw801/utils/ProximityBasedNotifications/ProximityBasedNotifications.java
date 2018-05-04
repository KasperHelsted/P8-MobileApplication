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

    /**
     * Constructor for the proximity notification class.
     * Sets up the location manager used for proximity events.
     * @param base The context of the application.
     */
    public ProximityBasedNotifications(Context base) {
        mContext = base;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Method used to construct a proximity based alert.
     * @param coordinate The coordinate set used for the proximity alert.
     * @param requestCode The request code used for the intent that are created.
     * @param eventWithData The EventWithData object associated with the proximity alert that need to be created.
     */
    public void createProximityNotification(Coordinate coordinate, int requestCode, EventWithData eventWithData) {

        //Setup pending intent for proximity
        Integer radius = 50;
        Intent intent = new Intent(mContext, ProximityReceiver.class);
        intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, 0);


        //Gps enabled check
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        else{

            //Add the proximity alarm to the system
            locationManager.addProximityAlert(coordinate.getLatitude(), coordinate.getLongitude(), radius, -1, pendingIntent);

            Log.i("Alarm", "Proximity added ");
        }

    }

    /**
     * Method used to cancel a pending proximity alert.
     * @param requestCode The request code that was used when creating the proximity alert. Need to be the EXACT same.
     * @param eventWithData The EventWithData object used when creating the proximity alert. Need to be the EXACT same.
     */
    public void cancelProximity(int requestCode, EventWithData eventWithData){
        Intent intent = new Intent(mContext, ProximityReceiver.class);
        intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, 0);
        locationManager.removeProximityAlert(pendingIntent);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        Log.i("Alarm", "Proximity deleted at: " + sdf.format(new Date()));
    }
}
