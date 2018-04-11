package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.GlobalMute;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Dao
public interface GlobalMuteDao {
    @Query("SELECT * FROM globalmute")
    List<GlobalMute> getAll();

    @Query("SELECT * FROM globalmute WHERE id IN (:globalMuteIds)")
    List<GlobalMute> loadAllByIds(Integer[] globalMuteIds);

    @Query("SELECT * FROM globalmute WHERE id == :globalMuteId LIMIT 1")
    GlobalMute loadById(Integer globalMuteId);

    @Query("SELECT COUNT(*) from globalmute")
    Integer count();

    @Insert
    void insertAll(GlobalMute... globalMutes);

    @Insert
    void insert(GlobalMute globalMute);

    @Update
    void update(GlobalMute globalMute);

    @Delete
    void delete(GlobalMute globalMute);
}
