package p8project.sw801.data.model.db.Smartdevice.Accessories;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
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
}
