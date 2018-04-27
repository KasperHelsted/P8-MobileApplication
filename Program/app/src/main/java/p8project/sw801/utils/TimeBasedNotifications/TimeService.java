package p8project.sw801.utils.TimeBasedNotifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import p8project.sw801.utils.ProximityBasedNotifications.ProximityReceiver;

public class TimeService extends Service {

    public static AlarmReceiver alarmReceiver;

    @Override
    public void onCreate() {
        alarmReceiver = new AlarmReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
