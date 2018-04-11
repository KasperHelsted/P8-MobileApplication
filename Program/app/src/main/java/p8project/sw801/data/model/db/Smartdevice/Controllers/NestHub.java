package p8project.sw801.data.model.db.Smartdevice.Controllers;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import p8project.sw801.data.model.db.SmartDevice;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity
public class NestHub extends SmartDevice {
    @ColumnInfo(name = "bearerToken")
    private String bearerToken;

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }
}