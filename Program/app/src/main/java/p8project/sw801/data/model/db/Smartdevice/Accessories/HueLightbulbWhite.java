package p8project.sw801.data.model.db.Smartdevice.Accessories;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity(indices = {
        @Index("hueBridgeId"),
}, foreignKeys = {
        @ForeignKey(entity = HueBridge.class, parentColumns = "id", childColumns = "hueBridgeId", onDelete = CASCADE)
})
public class HueLightbulbWhite {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "hueBridgeId")
    private Integer hueBridgeId;

    @ColumnInfo(name = "deviceName")
    private String deviceName;

    @ColumnInfo(name = "deviceId")
    private String deviceId;

    @ColumnInfo(name = "smartDeviceId")
    private Integer smartDeviceId;

    @Ignore
    public HueLightbulbWhite() {
    }

    public HueLightbulbWhite(Integer hueBridgeId, String deviceName, String deviceId, Integer smartDeviceId) {
        this.hueBridgeId = hueBridgeId;
        this.deviceName = deviceName;
        this.deviceId = deviceId;
        this.smartDeviceId = smartDeviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHueBridgeId() {
        return hueBridgeId;
    }

    public void setHueBridgeId(Integer hueBridgeId) {
        this.hueBridgeId = hueBridgeId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
        HueLightbulbWhite that = (HueLightbulbWhite) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.hueBridgeId.equals(that.hueBridgeId) && this.deviceName.equals(that.deviceName) && this.deviceId.equals(that.deviceId) && this.smartDeviceId.equals(that.smartDeviceId))
            return true;

        return false;
    }
}
