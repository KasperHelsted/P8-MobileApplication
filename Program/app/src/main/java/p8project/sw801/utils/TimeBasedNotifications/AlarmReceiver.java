package p8project.sw801.utils.TimeBasedNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.local.RelationEntity.WhenWithCoordinate;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.utils.NotificationUtil;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityBasedNotifications;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityReceiver;

public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Method called when an alarm is triggered
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        //TODO CHECK WHEN AND CREATE PROXI IF NEEDED ELSE TRIGGER SMART DEVICES






        NotificationUtil n = new NotificationUtil(context);
        n.CreateNotification("TIME", "ALARM SOMETHING HEHRHERHERHERHEHR");
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();

        String jsonMyObject ="";
        Bundle result = intent.getExtras();
        if (result != null) {
            jsonMyObject = result.getString("eventWithDate");
        }
        EventWithData eventWithData = new Gson().fromJson(jsonMyObject, EventWithData.class);
        List<TriggerWithSmartDevice> triggerWithSmartDevices = eventWithData.triggers;
        WhenWithCoordinate whenWithCoordinate = eventWithData.whens.get(0);
        When when = whenWithCoordinate.when;

        //IF NO LOCATION
        if (when.getLocationCondition() == 0){
            ProximityReceiver proximityReceiver = new ProximityReceiver();
            for (TriggerWithSmartDevice t : triggerWithSmartDevices) {
                proximityReceiver.triggerFunction(t.trigger, eventWithData.event.getName());
            }
        }else {
            ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
            proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);

        }
    }
}
