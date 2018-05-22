package p8project.sw801.utils.ProximityBasedNotifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class ProximityService extends Service {

    public static ProximityReceiver proximityReceiver = null;

    @Override
    public void onCreate() {
    }

    /**
     * Method called when the service is started.
     * This method creates a new instance of the proximity receiver used for catching proximity alerts.
     *
     * @param intent  The intent starting this service.
     * @param flags   Possible flags used for starting the service.
     * @param startId An id used for the service.
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (proximityReceiver == null) {
            proximityReceiver = new ProximityReceiver();
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
