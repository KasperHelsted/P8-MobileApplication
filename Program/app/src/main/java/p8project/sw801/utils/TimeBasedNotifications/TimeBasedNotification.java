package p8project.sw801.utils.TimeBasedNotifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.local.RelationEntity.WhenWithCoordinate;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;

public class TimeBasedNotification {

    private Context mContext;
    private static AlarmManager alarmManager;


    public TimeBasedNotification(Context base){
        mContext = base;
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * Creates a new alarm and add it to the alarm manager
     * @param alarmTime
     * @param eventWithData
     */
    public void setAlarm(long alarmTime, EventWithData eventWithData){

        //Create intent with data
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));

        //Creates the pending intent for the alarm
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, eventWithData.event.getId(), intent, 0);
        //Setup alarm with time and the pending intent
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);

        //Log
        Log.i("Log", "Time based alarm made");

    }

    /**
     * Deletes an alarm from the alarm manager
     * @param eventWithData
     */
    public void cancelAlarm(EventWithData eventWithData){

        //Setup intent for deleting an alarm
        Intent intent = new Intent(mContext, AlarmReceiver.class);
        intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));

        //Setup pending intent for deleting an alarm, need to be the exact same that was used when creating the alarm
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, eventWithData.event.getId(), intent, 0);
        alarmManager.cancel(pendingIntent);


        //Log
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        Log.i("Alarm", "Alarm deleted at: " + sdf.format(new Date()));
    }
}
