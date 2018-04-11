package p8project.sw801.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.PredefinedLocation;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Dao
public interface PredefinedLocationDao {
    @Query("SELECT * FROM predefinedlocation")
    List<PredefinedLocation> getAll();

    @Query("SELECT * FROM predefinedlocation WHERE id IN (:predefinedLocationIds)")
    List<PredefinedLocation> loadAllByIds(Integer[] predefinedLocationIds);

    @Query("SELECT * FROM predefinedlocation WHERE id == :predefinedLocationId LIMIT 1")
    PredefinedLocation loadById(Integer predefinedLocationId);

    @Query("SELECT COUNT(*) from predefinedlocation")
    Integer countPredefinedLocations();

    @Insert
    void insertAll(PredefinedLocation... predefinedLocations);

    @Insert
    void insert(PredefinedLocation predefinedLocation);

    @Update
    void update(PredefinedLocation predefinedLocation);

    @Delete
    void delete(PredefinedLocation predefinedLocation);
}
