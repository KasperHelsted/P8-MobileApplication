package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Kasper Helsted on 3/22/2018.
 */

@Entity(indices = {
        @Index("eventId"),
        @Index("smartDeviceId"),
}, foreignKeys = {
        @ForeignKey(entity = Event.class, parentColumns = "id", childColumns = "eventId", onDelete = CASCADE),
        @ForeignKey(entity = SmartDevice.class, parentColumns = "id", childColumns = "smartDeviceId", onDelete = CASCADE)
})
public class Trigger {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "eventId")
    private Integer eventId;

    @ColumnInfo(name = "smartDeviceId")
    private Integer smartDeviceId;

    @ColumnInfo(name = "accessorieId")
    private Integer accessorieId;

    @ColumnInfo(name = "notification")
    private Boolean notification;

    @ColumnInfo(name = "notificationText")
    private String notificationText;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer getSmartDeviceId() {
        return smartDeviceId;
    }

    public void setSmartDeviceId(Integer smartDeviceId) {
        this.smartDeviceId = smartDeviceId;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public Integer getAccessorieId() {
        return accessorieId;
    }

    public void setAccessorieId(Integer accessorieId) {
        this.accessorieId = accessorieId;
    }
}
