package p8project.sw801.data.local.dao.Accessories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbRGB;

@Dao
public interface HueLightbulbRGBDao {
    @Query("SELECT * FROM huelightbulbrgb WHERE hueBridgeId == :id")
    List<HueLightbulbRGB> getRGBLightsByBridgeId(Integer id);

    @Query("SELECT * FROM huelightbulbrgb WHERE smartDeviceId == :id")
    List<HueLightbulbRGB> getRGBHueLightsBySmartDeviceId(Integer id);

    @Query("DELETE FROM huelightbulbrgb WHERE smartDeviceId=:id")
    void deleteRGBHueLightsBySmartDeviceId(Integer id);

    @Insert
    void insertAllRGBHueLightbulbs(HueLightbulbRGB... hueLightbulbRGBS);

    @Insert
    void insertRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB);

    @Update
    void updateRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB);

    @Delete
    void deleteRGBHueLightbulb(HueLightbulbRGB hueLightbulbRGB);
}
