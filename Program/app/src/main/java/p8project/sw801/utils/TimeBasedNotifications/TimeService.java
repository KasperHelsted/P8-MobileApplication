package p8project.sw801.utils.TimeBasedNotifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class TimeService extends Service {

    public static AlarmReceiver alarmReceiver = null;

    @Override
    public void onCreate() {
    }

    /**
     * Method called when the service is started.
     * This method creates a new instance of the Time based receiver used for catching time based alerts.
     *
     * @param intent  The intent starting this service.
     * @param flags   Possible flags used for starting the service.
     * @param startId An id used for the service.
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (alarmReceiver == null) {
            alarmReceiver = new AlarmReceiver();
        }
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
