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
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;
import p8project.sw801.data.model.db.When;
import p8project.sw801.ui.base.BaseService;
import p8project.sw801.utils.HueUtilities;
import p8project.sw801.utils.Nest.NestUtilities;
import p8project.sw801.utils.NotificationUtil;
import p8project.sw801.utils.TimeBasedNotifications.TimeBasedNotification;

public class ProximityReceiver extends BroadcastReceiver {

    private static NotificationUtil n;
    BaseService baseService = new BaseService();

    /**
     * Method called when a proximity alert is posted. This method then calls the trigger function if the current time is not in the span of a global mute setting.
     * @param context The context of the application. Needed for the notifications.
     * @param intent The posted proximity alert intent. This intent contains the EventWithData object.
     */
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

        //Check for deleted
        if (eventWithData != null) {


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
                    triggerFunction(eventWithData, eventWithData.event.getName(), context);

                    //If the user is leaving a location
                } else if (when.getLocationCondition() == 3 && !entering) {
                    Log.i("PROXIMITY", "Leaving");
                    triggerFunction(eventWithData, eventWithData.event.getName(), context);

                }
        }
    }

    /**
     * Method called when triggering the functionality of notifications.
     * This functionality is to create notifications for the phone, trigger hue related actions and trigger nest related actions.
     * @param eventWithData The event with data object
     * @param eventName The name of the event. Used for the notification that are posted to the user.
     * @param context The context of the applicaiton. Used for posting notifications.
     */
    public void triggerFunction(EventWithData eventWithData, String eventName, Context context) {

        //Delete set alarms and remake. Used to ensure repeated triggering
        //TimeBasedNotification.cancelAlarm(eventWithData, context);
        //TimeBasedNotification.setAlarm(context, eventWithData);

        List<TriggerWithSmartDevice> triggerList = eventWithData.triggers;


        //Initializes the Notification utility, hue bridge and nest hub objects.
        NotificationUtil notificationUtil = new NotificationUtil(context);
        HueUtilities.setupSDK();

        Boolean notification = false;
        HueBridge hueBridge = new HueBridge();
        NestHub nestHub = new NestHub();


        Log.i("Log", "Triggering");

        //Iterates through each trigger
        for (TriggerWithSmartDevice t : triggerList) {
            String uniqueId = "";

            //Checks if the trigger is a hue related trigger
            if (t.trigger.getAction() == 1 || t.trigger.getAction() == 2 || t.trigger.getAction() == 3) {
                //Checks if the ip of the triggering device is the same as the ip used for the hue bridge object
                if (hueBridge.getDeviceIP() != t.smartDeviceWithDataList.get(0).hueBridgeList.get(0).getDeviceIP()) {
                    //If the id is not the same make a connection to the new bridge
                    hueBridge = t.smartDeviceWithDataList.get(0).hueBridgeList.get(0);
                    HueUtilities.connectToBridge(hueBridge);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Checks if the trigger is a nest related trigger
            } else if (t.trigger.getAction() == 4 || t.trigger.getAction() == 5 || t.trigger.getAction() == 6) {
                //Checks if the ip of the triggering device is the same as the ip used for the nest hub object
                if (nestHub.getClientId() != t.smartDeviceWithDataList.get(0).nestHubList.get(0).getClientId()) {
                    //If the id is not the same make a connection to the new hub
                    nestHub = t.smartDeviceWithDataList.get(0).nestHubList.get(0);
                    NestUtilities.InitializeNestForCurrentContext(context, nestHub);
                }
            }

            //Switch case to check which trigger need to be posted.
            // Actions:
            // 0. Normal Notification
            // 1. Turn on light
            // 2. Turn off light
            // 3. Adjust brightness + value
            // 4. Turn on Thermo
            // 5. Turn off Thermo
            // 6. Adjust temp + value
            switch (t.trigger.getAction()) {
                case 0:
                    notificationUtil.CreateNotification(eventName, t.trigger.getNotificationText());
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
                        Thread.sleep(500);
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
                        Thread.sleep(500);
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
                    HueUtilities.turnLightOn(uniqueId);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    HueUtilities.changeLightstate(uniqueId, 40000, t.trigger.getValue());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    for (NestThermostat nestThermostat : t.smartDeviceWithDataList.get(0).nestThermostatList){
                        if (t.trigger.getAccessorieId() == nestThermostat.getId()){
                            uniqueId = nestThermostat.getDeviceId();
                        }
                    }
                    NestUtilities.nestAPI.thermostats.setHVACMode(uniqueId, "heat-cool");
                    break;
                case 5:
                    for (NestThermostat nestThermostat : t.smartDeviceWithDataList.get(0).nestThermostatList){
                        if (t.trigger.getAccessorieId() == nestThermostat.getId()){
                            uniqueId = nestThermostat.getDeviceId();
                        }
                    }
                    NestUtilities.nestAPI.thermostats.setHVACMode(uniqueId, "off");
                    break;
                case 6:
                    for (NestThermostat nestThermostat : t.smartDeviceWithDataList.get(0).nestThermostatList){
                        if (t.trigger.getAccessorieId() == nestThermostat.getId()){
                            uniqueId = nestThermostat.getDeviceId();
                        }
                    }
                    NestUtilities.nestAPI.thermostats.setTargetTemperatureC(uniqueId, t.trigger.getValue());
                    break;
                default:
                    break;
            }
        }

        if (!notification) {
            notificationUtil.CreateNotification(eventName, "Notification");
            notification = false;
        }
    }

    /**
     * Method used to check if the time for the current trigger is in the span of a global mute setting.
     * @param globalMuteList The list of global mutes.
     * @param time The current time.
     * @return
     */
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
