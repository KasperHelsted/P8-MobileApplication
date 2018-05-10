package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.Nullable;

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
    @Nullable
    private Integer eventId;

    @ColumnInfo(name = "smartDeviceId")
    private Integer smartDeviceId;

    @ColumnInfo(name = "accessorieId")
    private Integer accessorieId;

    @ColumnInfo(name = "notification")
    private Boolean notification;

    @ColumnInfo(name = "notificationText")
    private String notificationText;

    @ColumnInfo(name = "action")
    private Integer action;

    @ColumnInfo(name = "value")
    private Integer value;

    @Ignore
    public Trigger() {
    }

    public Trigger(Integer eventId, Integer smartDeviceId, Integer accessorieId, Boolean notification, String notificationText, Integer action, Integer value) {
        this.eventId = eventId;
        this.smartDeviceId = smartDeviceId;
        this.accessorieId = accessorieId;
        this.notification = notification;
        this.notificationText = notificationText;
        this.action = action;
        this.value = value;
    }


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

    /*Actions:
      0. Normal Notification
      1. Turn on light
      2. Turn off light
      3. Adjust brightness + value
      4. Turn on Thermo
      5. Turn off Thermo
      6. Adjust temp + value
      */


    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object object) {
        // Checks if object is from same instance
        if (this == object)
            return true;

        // Checks if object is null or if the two classes is of same type
        if (object == null || getClass() != object.getClass())
            return false;

        // We can assume that the object we are comparing is of same type so we can cast
        Trigger that = (Trigger) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.eventId.equals(that.eventId) && this.smartDeviceId.equals(that.smartDeviceId) && this.accessorieId.equals(that.accessorieId) && this.notification.equals(that.notification) && this.notificationText.equals(that.notificationText) && this.action.equals(that.action) && this.value.equals(that.value))
            return true;

        return false;
    }

}
