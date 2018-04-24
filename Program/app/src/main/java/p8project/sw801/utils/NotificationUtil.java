package p8project.sw801.utils;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import p8project.sw801.R;
import p8project.sw801.ui.main.MainActivity;


public class NotificationUtil extends ContextWrapper {

    //For SDK 22
    private NotificationManager mNotifyManager ;
    private Notification mNotify;

    //For SDK 26
    private NotificationManager mManager;
    public static final String ANDROID_CHANNEL_ID = "p8project.sw801.ANDROID";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";

    private Intent notifyIntent;
    private PendingIntent notifyPendingIntent;

    public NotificationUtil(Context base) {
        super(base);
        creationOfIntent();
    }


    private void creationOfIntent(){
        notifyIntent = new Intent(this, MainActivity.class);
        // Set the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Create the PendingIntent
        notifyPendingIntent = PendingIntent.getActivity(
                this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );

    }

    public void CreateNotification(String title, String content) {

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Notification.Builder mNotificationBuilder = new Notification.Builder(this);
            mNotificationBuilder.setSmallIcon(R.drawable.ic_dashboard_black_24dp)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(notifyPendingIntent);
            mNotify = mNotificationBuilder.build();
            ShowNotify();
        }
        else{
            createChannels();
            Notification.Builder mNotificationBuilder = new Notification.Builder(getApplicationContext(), ANDROID_CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(android.R.drawable.ic_delete)
                    .setContentIntent(notifyPendingIntent);
            mManager.notify(101, mNotificationBuilder.build());
        }
    }

    //SDK < 26
    private void ShowNotify(){
        mNotifyManager.notify(0, mNotify);
    }

    //SDK >= 26
    private void createChannels() {
        // create android channel
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        // Sets whether notifications posted to this channel should display notification lights
        androidChannel.enableLights(true);
        // Sets whether notification posted to this channel should vibrate.
        androidChannel.enableVibration(true);
        androidChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        // Sets the notification light color for notifications posted to this channel
        androidChannel.setLightColor(Color.GREEN);
        // Sets whether notifications posted to this channel appear on the lockscreen or not
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getManager().createNotificationChannel(androidChannel);
    }
    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }


}
