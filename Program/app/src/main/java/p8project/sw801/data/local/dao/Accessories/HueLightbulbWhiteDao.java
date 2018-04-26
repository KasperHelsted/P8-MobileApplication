package p8project.sw801.data.local.dao.Accessories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;

@Dao
public interface HueLightbulbWhiteDao {
    @Query("SELECT * FROM huelightbulbwhite WHERE hueBridgeId == :id")
    List<HueLightbulbWhite> getWhiteLightsByBridgeId(Integer id);

    @Query("SELECT * FROM huelightbulbwhite WHERE smartDeviceId == :id")
    List<HueLightbulbWhite> getWhiteHueLightsBySmartDeviceId(Integer id);

    @Query("DELETE FROM huelightbulbwhite WHERE smartDeviceId=:id")
    void deleteWhiteHueLightsBySmartDeviceId(Integer id);

    @Insert
    void insertAllWhiteHueLightbulbs(HueLightbulbWhite... hueLightbulbWhites);

    @Insert
    void insertWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite);

    @Update
    void updateWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite);

    @Delete
    void deleteWhiteHueLightbulb(HueLightbulbWhite hueLightbulbWhite);
}
