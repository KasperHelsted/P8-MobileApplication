package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.When;

/**
 * Created by Kasper Helsted on 3/22/2018.
 */

@Dao
public interface WhenDao {
    @Query("SELECT * FROM `when`")
    List<When> getAll();

    @Query("SELECT * FROM `when` WHERE id IN (:whenIds)")
    List<When> loadAllByIds(Integer[] whenIds);

    @Query("SELECT * FROM `when` WHERE id == :whenId LIMIT 1")
    When loadById(Integer whenId);

    @Query("SELECT COUNT(*) from `when`")
    Integer count();

    @Insert
    void insertAll(When... whens);

    @Insert
    void insert(When when);

    @Update
    void update(When when);

    @Delete
    void delete(When when);
}
