package p8project.sw801.utils.TimeBasedNotifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.Trigger;

public class TimeBasedNotification {

    private Context mContext;


    public TimeBasedNotification(Context base){
        mContext = base;
    }

    public void setAlarm(long alarmTime, int requestcode, EventWithData eventWithData){
        Calendar now = Calendar.getInstance();
        Intent intent = new Intent(mContext, AlarmReceiver.class);

        //

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestcode, intent, 0);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }
}
