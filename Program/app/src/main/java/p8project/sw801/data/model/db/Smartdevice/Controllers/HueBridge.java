package p8project.sw801.data.model.db.Smartdevice.Controllers;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.philips.lighting.model.PHBridge;

import p8project.sw801.data.model.db.SmartDevice;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity
public class HueBridge{
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "deviceIP")
    private String deviceIP;


    @ColumnInfo(name = "smartDeviceId")
    private Integer smartDeviceId;

    @ColumnInfo(name = "username")
    private String username;


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
}
