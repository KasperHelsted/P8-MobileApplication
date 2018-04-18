package p8project.sw801.data.model.db.Smartdevice.Controllers;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

import p8project.sw801.data.model.db.SmartDevice;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity
public class HueBridge extends SmartDevice {
    @ColumnInfo(name = "deviceIP")
    private String deviceIP;

    @ColumnInfo(name = "username")
    private String username;



    public String getDeviceIP() {
        return deviceIP;
    }

    public void setDeviceIP(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
