package p8project.sw801.utils.TimeBasedNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Calendar;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.utils.NotificationUtil;

public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Method called when an alarm is triggered
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtil n = new NotificationUtil(context);
        n.CreateNotification("TIME", "ALARM SOMETHING HEHRHERHERHERHEHR");
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();

        String jsonMyObject ="";
        Bundle result = intent.getExtras();
        if (result != null) {
            jsonMyObject = result.getString("eventWithDate");
        }
        EventWithData eventWithData = new Gson().fromJson(jsonMyObject, EventWithData.class);

        //TODO TRIGGER SMARTDEVICES OR SET UP NEXT WHEN PROXIMITY
    }
}
