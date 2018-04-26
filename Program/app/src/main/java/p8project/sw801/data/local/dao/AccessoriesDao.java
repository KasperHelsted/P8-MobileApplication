package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbRGB;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;

@Dao
public interface AccessoriesDao {

    @Query("SELECT * FROM huelightbulbwhite WHERE hueBridgeId == :id")
    List<HueLightbulbWhite> getLightsByBridgeIdDB(Integer id);

    @Query("SELECT * FROM huelightbulbrgb WHERE hueBridgeId == :id")
    List<HueLightbulbRGB> getRGBLightsByBridgeIdDB(Integer id);

    @Query("SELECT * FROM nestthermostat WHERE nestHubId == :id")
    List<NestThermostat> getNestByHubIdDB(Integer id);

    @Query("SELECT * FROM huelightbulbwhite WHERE smartDeviceId == :id")
    List<HueLightbulbWhite> getHueLightsBySmartDeviceId(Integer id);

    @Query("SELECT * FROM nestthermostat WHERE smartDeviceId == :id")
    List<NestThermostat> getNestThermoBySmartDeviceId(Integer id);

    @Query("SELECT * FROM huebridge")
    List<HueBridge> getAllHueBridges();

    @Query("SELECT * FROM NestHub")
    List<NestHub> getAllNestHubs();

    @Query("SELECT * FROM HueBridge ORDER BY HueBridge.id DESC LIMIT 1")
    HueBridge getLastHueBridge();

    @Insert
    void insertAllHue(HueLightbulbWhite... hueLightbulbWhites);

    @Insert
    void insertHue(HueLightbulbWhite hueLightbulbWhite);

    @Insert
    void insertAllNestThermos(NestThermostat... nestThermostats);

    @Insert
    void insertNestThermo(NestThermostat nestThermostat);

    @Insert
    void insertNestHub(NestHub nestHub);

    @Insert
    void insertHueBidge(HueBridge hueBridge);
}
