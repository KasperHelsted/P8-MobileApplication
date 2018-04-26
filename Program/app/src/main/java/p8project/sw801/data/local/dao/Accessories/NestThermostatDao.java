package p8project.sw801.data.local.dao.Accessories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;

@Dao
public interface NestThermostatDao {
    @Query("SELECT * FROM nestthermostat WHERE nestHubId == :id")
    List<NestThermostat> getThermostatByHubId(Integer id);

    @Query("SELECT * FROM nestthermostat WHERE smartDeviceId == :id")
    List<NestThermostat> getNestThermostatBySmartDeviceId(Integer id);

    @Query("DELETE FROM nestthermostat WHERE smartDeviceId=:id")
    void deleteNestThermostatBySmartDeviceId(Integer id);

    @Insert
    void insertAllNestThermostats(NestThermostat... nestThermostats);

    @Insert
    void insertNestThermostat(NestThermostat nestThermostat);

    @Update
    void updateNestThermostat(NestThermostat nestThermostat);

    @Delete
    void deleteNestThermostat(NestThermostat nestThermostat);
}
