package p8project.sw801.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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

    public Integer getInternalIdentifier() {return internalIdentifier;}

    public void setInternalIdentifier(Integer internalIdentifier) {this.internalIdentifier = internalIdentifier;}

}
