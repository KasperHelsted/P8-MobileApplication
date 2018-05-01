package p8project.sw801.data.model.db.Smartdevice.Controllers;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import p8project.sw801.data.model.db.SmartDevice;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Entity
public class NestHub{
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "bearerToken")
    private String bearerToken;

    @ColumnInfo(name = "smartDeviceId")
    private Integer smartDeviceId;

    @ColumnInfo(name = "clientId")
    private String clientId;

    @ColumnInfo(name = "secretId")
    private String secretId;

    @ColumnInfo(name = "expires")
    private Long expires;

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }
}