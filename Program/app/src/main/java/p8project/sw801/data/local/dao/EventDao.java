package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Event;

/**
 * Created by Kasper Helsted on 3/21/2018.
 */

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Query("SELECT * FROM event WHERE id IN (:eventIds)")
    List<Event> loadAllByIds(Integer[] eventIds);

    @Query("SELECT * FROM event WHERE id == :eventId LIMIT 1")
    Event loadById(Integer eventId);

    @Query("SELECT COUNT(*) from event")
    Integer count();

    @Query("SELECT * FROM event ORDER BY event.id DESC LIMIT 1")
    Event getLastEvent();

    @Insert
    void insertAll(Event... events);

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);
}
