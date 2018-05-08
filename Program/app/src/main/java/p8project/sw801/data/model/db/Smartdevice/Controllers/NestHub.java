package p8project.sw801.data.model.db.Smartdevice.Controllers;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
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

    @Ignore
    public NestHub() {
    }

    public NestHub(String bearerToken, Integer smartDeviceId, String clientId, String secretId, Long expires) {
        this.bearerToken = bearerToken;
        this.smartDeviceId = smartDeviceId;
        this.clientId = clientId;
        this.secretId = secretId;
        this.expires = expires;
    }

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

    @Override
    public boolean equals(Object object) {
        // Checks if object is from same instance
        if (this == object)
            return true;

        // Checks if object is null or if the two classes is of same type
        if (object == null || getClass() != object.getClass())
            return false;

        // We can assume that the object we are comparing is of same type so we can cast
        NestHub that = (NestHub) object;

        // This checks if it's the same object but initialized at two different times
        if (this.id != null && that.id != null)
            return this.id.equals(that.id);

        // Here we can compare two objects before they have unique primary keys and are inserted into the database
        if (this.bearerToken.equals(that.bearerToken) && this.smartDeviceId.equals(that.smartDeviceId) && this.clientId.equals(that.clientId) && this.secretId.equals(that.secretId) && this.expires.equals(that.expires))
            return true;

        return false;
    }
}