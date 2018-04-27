package p8project.sw801.data.local.dao.Accessories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;

@Dao
public interface HueBridgeDao {
    @Query("SELECT * FROM huebridge")
    List<HueBridge> getAllHueBridges();

    @Query("SELECT * FROM huebridge ORDER BY huebridge.id DESC LIMIT 1")
    HueBridge getLastInsertedHueBridge();

    @Query("SELECT * FROM huebridge WHERE smartDeviceId=:id")
    List<HueBridge> getAllHueBridgesBySmartDeviceId(Integer id);

    @Query("DELETE FROM huebridge WHERE smartDeviceId=:id")
    void deleteHueBridgeBySmartDeviceId(Integer id);

    @Insert
    void insertAllHueBridges(HueBridge... hueBridges);

    @Insert
    void insertHueBridge(HueBridge hueBridge);

    @Update
    void updateHueBridge(HueBridge hueBridge);

    @Delete
    void deleteHueBridge(HueBridge hueBridge);

}
