package p8project.sw801.utils.TimeBasedNotifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.local.RelationEntity.WhenWithCoordinate;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.ui.base.BaseService;
import p8project.sw801.utils.ProximityBasedNotifications.ProximityService;

public final class TimeBasedNotification {
    private static BaseService baseService = new BaseService();

    /**
     * Constructs a new time based alarm based on the time and weekdays chosen when creating an event.
     * @param ctx The context of the application.
     * @param e The EventWithData object for which the time based alarm should be constructed.
     */
    public static void setAlarm(Context ctx, EventWithData e){

        //Starts a new time based service and proximity based service.
        //These are started so when a time based alarm or proximity based alarm are posted it is possible to still catch these even when the application are closed.
        Intent in = new Intent(ctx, TimeService.class);
        ctx.startService(in);
        Intent i = new Intent(ctx, ProximityService.class);
        ctx.startService(i);

        //Creates a new connection to the database to fetch the newest EventWithData object in case the user have updated it since they created the event the first time.
        AppDatabase db = baseService.getDatabase(ctx);
        EventWithData eventWithData = db.eventWithDataDao().getEventWithData(
                e.event.getId()
        );

        //Check to see if the user have deleted the event
        if (eventWithData != null) {
            WhenWithCoordinate time = eventWithData.whens.get(0);
            //Initialize alarmManager with the context
            AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
            //Get instance of the calendar
            Calendar calendar = Calendar.getInstance();
            //Initialize the interval for the alarm
            long intervalMillis = 0;
            //If there is not a time for the event
            if (time.when.getTimeCondition() == 1 || time.when.getTimeCondition() == 0) {
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY + 1), 00, 00);
            } else {
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), time.when.getStartHour(), time.when.getStartMinute(), 00);
            }
            List<Integer> weekdayList = null;
            try {
                weekdayList = time.when.getListWeekDays();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            boolean weekAlarm = isWeekAlarm(weekdayList);

            if (weekAlarm) {
                //If the alarm is for every day of the week
                intervalMillis = 24 * 3600 * 1000; //Alarm once a day
                Intent intent = new Intent(ctx, AlarmReceiver.class);
                intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));
                intent.putExtra("weekDayInt", 0);
                PendingIntent sender = PendingIntent.getBroadcast(ctx, eventWithData.event.gethashcode(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                am.setRepeating(AlarmManager.RTC_WAKEUP, timeHelper(0, calendar.getTimeInMillis()), intervalMillis, sender);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
                Log.i("Alarm", "Alarm added at: " + sdf.format(new Date()));
            } else {
                //Else alarm on specific days in the week
                intervalMillis = 24 * 3600 * 1000 * 7;
                for (Integer day : weekdayList) {
                    Intent intent = new Intent(ctx, AlarmReceiver.class);
                    intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));
                    intent.putExtra("weekDayInt", day);
                    PendingIntent sender = PendingIntent.getBroadcast(ctx, eventWithData.event.gethashcode() + day, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    am.setRepeating(AlarmManager.RTC_WAKEUP, timeHelper(day, calendar.getTimeInMillis()), intervalMillis, sender);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
                    Log.i("Alarm", "Alarm added at: " + sdf.format(new Date()) + "With time:´" + sdf.format(timeHelper(day, calendar.getTimeInMillis())));
                }
            }
        }
    }

    /**
     * Deletes an alarm from the alarm manager
     * @param eventWithData The EventWithData object associated with the time based alarm.
     */
    public static void cancelAlarm(EventWithData eventWithData, Context ctx){
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        WhenWithCoordinate time = eventWithData.whens.get(0);
        List<Integer> weekdayList = null;
        try {
            weekdayList = time.when.getListWeekDays();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean weekAlarm = isWeekAlarm(weekdayList);
        //Setup intent for deleting an alarm
        if (weekAlarm)
        {
            Intent intent = new Intent(ctx,AlarmReceiver.class);
            intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));
            intent.putExtra("weekDayInt",0);
            PendingIntent sender = PendingIntent.getBroadcast(ctx, eventWithData.event.gethashcode(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
            am.cancel(sender);
        }
        else
        {
            for(Integer day : weekdayList)
            {
                Intent intent = new Intent(ctx,AlarmReceiver.class);
                intent.putExtra("eventWithDate", new Gson().toJson(eventWithData));
                intent.putExtra("weekDayInt",day);
                PendingIntent sender = PendingIntent.getBroadcast(ctx, eventWithData.event.getId()+day, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                am.cancel(sender);
            }
        }
        //Log
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        Log.i("Alarm", "Alarm deleted at: " + sdf.format(new Date()));
    }

    /**
     * Method used to calculate the time needed to construct alarms for the different weekdays.
     * @param weekflag An integer describing the day of the week for the alarm.
     * @param dateTime Time describing when the alarm should trigger.
     * @return A new time describing the corresponding day and time for the alarm to trigger.
     */
    private static long timeHelper(int weekflag, long dateTime) {
        long time = 0;
        if (weekflag != 0)
        {
            Calendar c = Calendar.getInstance();
            int week = c.get(Calendar.DAY_OF_WEEK);
            if (0 == week)
            {
                week = 7;
            }
            if (weekflag == week) {
                if (dateTime > System.currentTimeMillis()) {
                    time = dateTime;
                } else {
                    time = dateTime + 7 * 24 * 3600 * 1000;
                }
            } else if (weekflag > week) {
                time = dateTime + (weekflag - week) * 24 * 3600 * 1000;
            } else if (weekflag < week) {
                time = dateTime + (weekflag - week + 7) * 24 * 3600 * 1000;
            }
        } else {
            if (dateTime > System.currentTimeMillis()) {
                time = dateTime;
            } else {
                time = dateTime + 24 * 3600 * 1000;
            }
        }
        return time;
    }

    /**
     * Method to check if an event have need an alarm every day of the week.
     * @param inputList List of days to trigger.
     * @return Boolean depending on if the list of days contain every day.
     */
    private static boolean isWeekAlarm(List<Integer> inputList){
        if (inputList != null){
            List<Integer> allDays = new ArrayList<Integer>(){{
                add(1);
                add(2);
                add(3);
                add(4);
                add(5);
                add(6);
                add(7);
            }};
            boolean weekAlarm = inputList.containsAll(allDays);
            return weekAlarm;
        }
        return false;
    }
}
