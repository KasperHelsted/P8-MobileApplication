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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (proximityReceiver == null){
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
