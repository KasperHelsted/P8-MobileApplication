package p8project.sw801.utils.ProximityBasedNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
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
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.When;
import p8project.sw801.ui.base.BaseService;
import p8project.sw801.utils.HueUtilities;
import p8project.sw801.utils.NotificationUtil;

public class ProximityReceiver extends BroadcastReceiver {

    private static NotificationUtil n;
    BaseService baseService = new BaseService();

    @Override
    public void onReceive(Context context, Intent intent) {
        n = new NotificationUtil(context);

        //Get entering attribute
        final String key = LocationManager.KEY_PROXIMITY_ENTERING;
        final Boolean entering = intent.getBooleanExtra(key, false);

        //Get EventWithData
        String jsonMyObject = "";
        Bundle result = intent.getExtras();
        if (result != null) {
            jsonMyObject = result.getString("eventWithDate");
        }

        AppDatabase db = baseService.getDatabase(context);
        EventWithData eventWithData = db.eventWithDataDao().getEventWithData(
                new Gson().fromJson(jsonMyObject, EventWithData.class).event.getId()
        );

        //Get updated eventlist and global mute list
        List<GlobalMute> globalMuteList = db.globalMuteDao().getAll();

        List<TriggerWithSmartDevice> triggerWithSmartDevices = eventWithData.triggers;
        WhenWithCoordinate whenWithCoordinate = eventWithData.whens.get(0);
        When when = whenWithCoordinate.when;

        Log.i("Log", "Recieved prox alarm");

        //Get database
        AppDatabase appDatabase = baseService.getDatabase(context);

        //Get time of day to compare. Are only encoded with hour and minute
        GregorianCalendar gc = new GregorianCalendar();
        int ho = gc.get(GregorianCalendar.HOUR_OF_DAY);
        int minute = gc.get(GregorianCalendar.MINUTE);
        gc.clear();
        gc.add(Calendar.HOUR_OF_DAY, ho);
        gc.add(Calendar.MINUTE, minute);
        long time = gc.getTime().getTime();


        if (eventWithData.event.getActive() && !globalMuted(globalMuteList, time))
        //If the user is entering/At a location
        if (when.getLocationCondition() != 0 && when.getLocationCondition() != 3 && entering) {
            Log.i("PROXIMITY", "Entering");
            triggerFunction(triggerWithSmartDevices, eventWithData.event.getName());

            //If the user is leaving a location
        } else if (when.getLocationCondition() == 3 && !entering) {
            Log.i("PROXIMITY", "Leaving");
            triggerFunction(triggerWithSmartDevices, eventWithData.event.getName());

        }
    }

    public void triggerFunction(List<TriggerWithSmartDevice> triggerList, String eventName) {

        HueUtilities.setupSDK();

        Boolean notification = false;
        HueBridge hueBridge = new HueBridge();

        Log.i("Log", "Triggering");
        for (TriggerWithSmartDevice t : triggerList) {
            String uniqueId = "";
            if (t.trigger.getAction() == 1 || t.trigger.getAction() == 2 || t.trigger.getAction() == 3) {

                if (hueBridge.getDeviceIP() != t.smartDeviceWithDataList.get(0).hueBridgeList.get(0).getDeviceIP()){
                    hueBridge = t.smartDeviceWithDataList.get(0).hueBridgeList.get(0);
                    HueUtilities.connectToBridge(hueBridge);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            switch (t.trigger.getAction()) {
                case 0:
                    n.CreateNotification(eventName, t.trigger.getNotificationText());
                    notification = true;
                    break;
                case 1:
                    for (HueLightbulbWhite lightbulbWhite : t.smartDeviceWithDataList.get(0).hueLightbulbWhiteList) {
                        if (t.trigger.getAccessorieId() == lightbulbWhite.getId()) {
                            uniqueId = lightbulbWhite.getDeviceId();
                        }
                    }

                    HueUtilities.turnLightOn(uniqueId);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    for (HueLightbulbWhite lightbulbWhite : t.smartDeviceWithDataList.get(0).hueLightbulbWhiteList) {
                        if (t.trigger.getAccessorieId() == lightbulbWhite.getId()) {
                            uniqueId = lightbulbWhite.getDeviceId();
                        }
                    }
                    HueUtilities.turnLightOff(uniqueId);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    for (HueLightbulbWhite lightbulbWhite : t.smartDeviceWithDataList.get(0).hueLightbulbWhiteList) {
                        if (t.trigger.getAccessorieId() == lightbulbWhite.getId()) {
                            uniqueId = lightbulbWhite.getDeviceId();
                        }
                    }
                    HueUtilities.changeLightstate(uniqueId, 40000, t.trigger.getValue());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    break;//TODO TRIGGER NEST THERMO ON
                case 5:
                    break;//TODO TRIGGER NEST THERMO OFF
                case 6:
                    break;//TODO CHANGE NEST THERMO TEMP
                default:
                    break;
            }
        }

        if (!notification) {
            n.CreateNotification(eventName, "");
            notification = false;
        }
    }

    public Boolean globalMuted(List<GlobalMute> globalMuteList, long time){
        Boolean muted = false;
        for (GlobalMute globalmute: globalMuteList) {
            if (globalmute.getStartTime() <= time && globalmute.getEndTime() >= time){
                muted = true;
                return muted;
            }

        }
        return muted;
    }


}
