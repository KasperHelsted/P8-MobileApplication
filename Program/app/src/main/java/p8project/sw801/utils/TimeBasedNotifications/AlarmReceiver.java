package p8project.sw801.utils.TimeBasedNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.local.RelationEntity.WhenWithCoordinate;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.data.model.db.When;
import p8project.sw801.ui.base.BaseService;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityBasedNotifications;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityReceiver;

public class AlarmReceiver extends BroadcastReceiver {

    BaseService baseService = new BaseService();

    /**
     * Method called when a time based alarm is triggered. Create a new proximity based alarm if the Event specifies it else calls a function to trigger to relevant smart devices.
     * @param context The context of the application.
     * @param intent The pending intent used when creating the alarm.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        //Get data from the transfered intent
        String jsonMyObject ="";
        Bundle result = intent.getExtras();
        if (result != null) {
            jsonMyObject = result.getString("eventWithDate");
        }

        //Creates a new connection to the database to fetch the newest EventWithData object in case the user have updated it since they created the event the first time.
        AppDatabase db = baseService.getDatabase(context);
        EventWithData eventWithData = db.eventWithDataDao().getEventWithData(
                new Gson().fromJson(jsonMyObject, EventWithData.class).event.getId()
        );

        //Check to see if the user have deleted the event
        if (eventWithData != null) {
            List<GlobalMute> globalMuteList = db.globalMuteDao().getAll();

            List<TriggerWithSmartDevice> triggerWithSmartDevices = eventWithData.triggers;
            WhenWithCoordinate whenWithCoordinate = eventWithData.whens.get(0);
            When when = whenWithCoordinate.when;


            //Get time of day to compare. Are only encoded with hour and minute
            GregorianCalendar gc = new GregorianCalendar();
            int ho = gc.get(GregorianCalendar.HOUR_OF_DAY);
            int minute = gc.get(GregorianCalendar.MINUTE);
            gc.clear();
            gc.add(Calendar.HOUR_OF_DAY, ho);
            gc.add(Calendar.MINUTE, minute);
            long time = gc.getTime().getTime();


            // Position 0 = No Time condition choosen
            // Position 1 = Before this time
            // Position 2 = At this time
            // Position 3 = After this time
            // Position 4 = Between these times
            ProximityReceiver proximityReceiver = new ProximityReceiver();

            //Evaluates the condition specified by the user and check if it is allowed to continue with either constructing a proximity alert or trigger smart devices.
            //Further the first check is to see if the current time is in the span of a global mute setting meaning that it is not allowed to trigger.
            if (eventWithData.event.getActive() && !proximityReceiver.globalMuted(globalMuteList, time)) {
                if (when.getLocationCondition() == 0) {
                    proximityReceiver.triggerFunction(eventWithData, eventWithData.event.getName(), context);
                    Log.i("log", "Time only received");
                } else if (when.getTimeCondition() == 0) {
                    Log.i("log", "Proximity only received");
                    ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
                    proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
                } else if (when.getTimeCondition() == 1 && when.getStartHour() >= ho && when.getStartMinute() >= minute) {
                    Log.i("log", "Before this time received");
                    ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
                    proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
                } else if (when.getTimeCondition() == 2) {
                    Log.i("log", "At this time received");
                    ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
                    proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
                } else if (when.getTimeCondition() == 3 && when.getStartHour() <= ho && when.getStartMinute() <= minute) {
                    Log.i("log", "After this time received");
                    ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
                    proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
                } else if (when.getTimeCondition() == 4 && when.getStartHour() <= ho && when.getStartMinute() <= minute && when.getEndHour() >= ho && when.getEndMinute() >= minute) {
                    Log.i("log", "Between times received");
                    ProximityBasedNotifications proximityBasedNotifications = new ProximityBasedNotifications(context);
                    proximityBasedNotifications.createProximityNotification(whenWithCoordinate.coordinate.get(0), eventWithData.event.getId(), eventWithData);
                }
            }
        }
    }

}
