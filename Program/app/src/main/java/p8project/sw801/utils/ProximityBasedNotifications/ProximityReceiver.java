package p8project.sw801.utils.ProximityBasedNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.local.RelationEntity.WhenWithCoordinate;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.utils.NotificationUtil;

public class ProximityReceiver extends BroadcastReceiver {

    private static NotificationUtil n;


    @Override
    public void onReceive(Context context, Intent intent) {
        n = new NotificationUtil(context);

        //Get entering attribute
        final String key = LocationManager.KEY_PROXIMITY_ENTERING;
        final Boolean entering = intent.getBooleanExtra(key, false);

        //Get EventWithData
        String jsonMyObject = "";
        Bundle result = intent.getExtras();
        if (result != null) {
            jsonMyObject = result.getString("eventWithDate");
        }
        EventWithData eventWithData = new Gson().fromJson(jsonMyObject, EventWithData.class);
        List<TriggerWithSmartDevice> triggerWithSmartDevices = eventWithData.triggers;
        WhenWithCoordinate whenWithCoordinate = eventWithData.whens.get(0);
        When when = whenWithCoordinate.when;

        Log.i("Log", "Recieved prox alarm");

        if (when.getLocationCondition() != 0 && when.getLocationCondition() != 3 && entering) {
            Log.i("PROXIMITY", "Entering");
            triggerFunction(triggerWithSmartDevices, eventWithData.event.getName(), context);

        } else if(when.getLocationCondition() == 3 && !entering) {
            Log.i("PROXIMITY", "Leaving");
            triggerFunction(triggerWithSmartDevices, eventWithData.event.getName(), context);

        }
    }

    public void triggerFunction(List<TriggerWithSmartDevice> triggerList, String eventName, Context context){

        Boolean notification = false;

        Log.i("Log", "Triggering");
        for (TriggerWithSmartDevice t:triggerList) {
            switch (t.trigger.getAction()){
            case 0:{ n.CreateNotification(eventName, t.trigger.getNotificationText());
            notification = true; }

            case 1:  break;//TODO TRIGGER HUE LIGHT ON;
            case 2: break;//TODO TRIGGER HUE LIGHT OFF;
            case 3: break;//TODO TRIGGER HUE LIGHT BRIGHTNESS
            case 4: break;//TODO TRIGGER NEST THERMO ON
            case 5: break;//TODO TRIGGER NEST THERMO OFF
            case 6: break;//TODO CHANGE NEST THERMO TEMP
            default: break;
            }
        }

        if(!notification){
            n.CreateNotification(eventName, "");
            notification = false;
        }
    }




}
