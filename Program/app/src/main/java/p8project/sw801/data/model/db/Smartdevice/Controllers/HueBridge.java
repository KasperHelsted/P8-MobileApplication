package p8project.sw801.data.model.db.Smartdevice.Controllers;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity
public class HueBridge {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "deviceIP")
    private String deviceIP;


    @ColumnInfo(name = "smartDeviceId")
    private Integer smartDeviceId;

    @ColumnInfo(name = "username")
    private String username;

    @Ignore
    public HueBridge() {
    }

    public HueBridge(String deviceIP, Integer smartDeviceId, String username) {
        this.deviceIP = deviceIP;
        this.smartDeviceId = smartDeviceId;
        this.username = username;
    }


    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSmartDeviceId() {
        return smartDeviceId;
    }

    public void setSmartDeviceId(Integer smartDeviceId) {
        this.smartDeviceId = smartDeviceId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        HueBridge that = (HueBridge) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.deviceIP.equals(that.deviceIP) && this.smartDeviceId.equals(that.smartDeviceId) && this.username.equals(that.username))
            return true;

        return false;
    }
}
