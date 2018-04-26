package p8project.sw801.data.local.dao.Accessories;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;

@Dao
public interface NestHubDao {
    @Query("SELECT * FROM nesthub")
    List<NestHub> getAllNestHubs();

    @Query("SELECT * FROM nesthub ORDER BY nesthub.id DESC LIMIT 1")
    NestHub getLastInsertedNestHub();

    @Query("SELECT * FROM nesthub WHERE smartDeviceId=:id")
    List<NestHub> getAllNestHubsBySmartDeviceId(Integer id);

    @Query("DELETE FROM nesthub WHERE smartDeviceId=:id")
    void deleteNestHubBySmartDeviceId(Integer id);

    @Insert
    void insertAllNestHubs(NestHub... nestHubs);

    @Insert
    void insertNestHub(NestHub nestHub);

    @Update
    void updateNestHub(NestHub nestHub);

    @Delete
    void deleteNestHub(NestHub nestHub);
}
