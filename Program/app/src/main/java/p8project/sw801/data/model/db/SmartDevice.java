package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import p8project.sw801.R;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */
@Entity
public class SmartDevice {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "deviceName")
    private String deviceName;

    @ColumnInfo(name = "active")
    private Boolean active;

    //1 = Hue, 2 = Nest
    @ColumnInfo(name = "internalIdentifier")
    private Integer internalIdentifier;

    @Ignore
    public SmartDevice() {
    }

    public SmartDevice(String deviceName, Boolean active, Integer internalIdentifier) {
        this.deviceName = deviceName;
        this.active = active;
        this.internalIdentifier = internalIdentifier;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String toString() {
        return id + ": " + deviceName;
    }

    public Integer getInternalIdentifier() {
        return internalIdentifier;
    }

    public void setInternalIdentifier(Integer internalIdentifier) {
        this.internalIdentifier = internalIdentifier;
    }

    public String getSmartDeviceType() {
        if (this.internalIdentifier == 1)
            return "Philips Hue Light Bulb";
        return "Nest Thermostat";
    }

    public Integer getSmartDeviceImage() {
        if (this.internalIdentifier == 1)
            return R.drawable.hue;
        return R.drawable.nest;
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
        SmartDevice that = (SmartDevice) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.deviceName.equals(that.deviceName) && this.active.equals(that.active) && this.internalIdentifier.equals(that.internalIdentifier))
            return true;

        return false;
    }

}
