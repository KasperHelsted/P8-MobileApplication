package p8project.sw801.utils.ProximityBasedNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.When;
import p8project.sw801.utils.NotificationUtil;

public class ProximityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtil n = new NotificationUtil(context);

        //Get entering attribute
        final String key = LocationManager.KEY_PROXIMITY_ENTERING;
        final Boolean entering = intent.getBooleanExtra(key, false);

        //Get EventWithData
        String jsonMyObject ="";
        Bundle result = intent.getExtras();
        if (result != null) {
            jsonMyObject = result.getString("eventWithDate");
        }
        EventWithData eventWithData = new Gson().fromJson(jsonMyObject, EventWithData.class);



        //TODO CHECK WHENS
        if (entering) {
            Toast.makeText(context, "entering", Toast.LENGTH_SHORT).show();
            Log.i("PROXIMITY", "Entering");
            n.CreateNotification("Entering", "Entering");
        } else {
            Toast.makeText(context, "exiting", Toast.LENGTH_SHORT).show();
            Log.i("PROXIMITY", "Leaving");
            n.CreateNotification("Leaving", "Leaving");
        }
    }

}
