package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Coordinate;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */
@Dao
public interface CoordinateDao {
    @Query("SELECT * FROM coordinate")
    List<Coordinate> getAll();

    @Query("SELECT * FROM coordinate WHERE id IN (:coordinateIds)")
    List<Coordinate> loadAllByIds(Integer[] coordinateIds);

    @Query("SELECT * FROM coordinate WHERE id == :coordinateId LIMIT 1")
    Coordinate loadById(Integer coordinateId);

    @Query("SELECT COUNT(*) from coordinate")
    Integer count();

    @Insert
    void insertAll(Coordinate... coordinates);

    @Insert
    void insert(Coordinate coordinate);

    @Update
    void update(Coordinate coordinate);

    @Delete
    void delete(Coordinate coordinate);
}
