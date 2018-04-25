package p8project.sw801.utils.TimeBasedNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        String jsonMyObject ="";
        Bundle result = intent.getExtras();
        if (result != null) {
            jsonMyObject = result.getString("eventWithDate");
        }
        EventWithData eventWithData = new Gson().fromJson(jsonMyObject, EventWithData.class);
        List<TriggerWithSmartDevice> triggerWithSmartDevices = eventWithData.triggers;
        WhenWithCoordinate whenWithCoordinate = eventWithData.whens.get(0);
        When when = whenWithCoordinate.when;


        // Position 0 = No Time condition choosen
        // Position 1 = Before this time
        // Position 2 = At this time
        // Position 3 = After this time
        // Position 4 = Between these times

        //Get time of day to compare
        GregorianCalendar gc = new GregorianCalendar();
        int ho = gc.get(GregorianCalendar.HOUR_OF_DAY);
        int minute = gc.get(GregorianCalendar.MINUTE);
        gc.clear();
        gc.add(Calendar.HOUR_OF_DAY, ho);
        gc.add(Calendar.MINUTE, minute);
        long time = gc.getTime().getTime();

        //IF NO LOCATION
        if (when.getLocationCondition() == 0){
            ProximityReceiver proximityReceiver = new ProximityReceiver();
                proximityReceiver.triggerFunction(triggerWithSmartDevices, eventWithData.event.getName(), context);
        }else if(when.getTimeCondition()== 1 && when.getStartTime() >= time){
            Log.i("log", "Before this time received");
            ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
            proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
        } else if(when.getTimeCondition() == 2){
            Log.i("log", "At this time received");
            ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
            proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
        } else if(when.getTimeCondition() == 3 && when.getStartTime() <= time){
            Log.i("log", "After this time received");
            ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
            proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
        } else if(when.getTimeCondition() == 4 && when.getStartTime() <= time && when.getEndTime() >= time){
            Log.i("log", "Between times received");
            ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
            proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
        }
    }
}
