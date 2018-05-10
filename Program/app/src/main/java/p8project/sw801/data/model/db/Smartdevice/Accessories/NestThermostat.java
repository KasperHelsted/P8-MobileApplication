package p8project.sw801.data.model.db.Smartdevice.Accessories;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */
@Entity(indices = {
        @Index("nestHubId"),
}, foreignKeys = @ForeignKey(
        entity = NestHub.class, parentColumns = "id", childColumns = "nestHubId", onDelete = CASCADE
))
public class NestThermostat {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "nestHubId")
    private Integer nestHubId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "deviceId")
    private String deviceId;

    @ColumnInfo(name = "smartDeviceId")
    private Integer smartDeviceId;

    @Ignore
    public NestThermostat() {
    }

    public NestThermostat(Integer nestHubId, String name, String deviceId, Integer smartDeviceId) {
        this.nestHubId = nestHubId;
        this.name = name;
        this.deviceId = deviceId;
        this.smartDeviceId = smartDeviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNestHubId() {
        return nestHubId;
    }

    public void setNestHubId(Integer nestHubId) {
        this.nestHubId = nestHubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getSmartDeviceId() {
        return smartDeviceId;
    }

    public void setSmartDeviceId(Integer smartDeviceId) {
        this.smartDeviceId = smartDeviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
        NestThermostat that = (NestThermostat) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.nestHubId.equals(that.nestHubId) && this.name.equals(that.name) && this.deviceId.equals(that.deviceId) && this.smartDeviceId.equals(that.smartDeviceId))
            return true;

        return false;
    }
}
