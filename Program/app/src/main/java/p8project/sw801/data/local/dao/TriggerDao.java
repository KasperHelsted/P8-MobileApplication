package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Trigger;

/**
 * Created by Kasper Helsted on 3/22/2018.
 */

@Dao
public interface TriggerDao {
    @Query("SELECT * FROM `trigger`")
    List<Trigger> getAll();

    @Query("SELECT * FROM `trigger` WHERE id IN (:triggerIds)")
    List<Trigger> loadAllByIds(Integer[] triggerIds);

    @Query("SELECT * FROM `trigger` WHERE id == :triggerId LIMIT 1")
    Trigger loadById(Integer triggerId);

    @Query("SELECT COUNT(*) from `trigger`")
    Integer count();

    @Query("SELECT * FROM `trigger` WHERE eventId == :eId")
    List<Trigger> loadAllByEventId(Integer eId);

    @Query("SELECT * FROM `trigger` WHERE smartDeviceId = :eId")
    List<Trigger> loadAllBySmartDeviceId(Integer eId);

    @Insert
    void insertAll(Trigger... triggers);

    @Insert
    void insert(Trigger trigger);

    @Update
    void update(Trigger trigger);

    @Delete
    void delete(Trigger trigger);
}
