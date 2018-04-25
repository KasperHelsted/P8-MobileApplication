package p8project.sw801.data.local.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import p8project.sw801.data.local.RelationEntity.*;

/**
 * Created by Kasper Helsted on 3/22/2018.
 */

@Dao
public interface EventWithDataDao {
    @Transaction
    @Query("SELECT * FROM event")
    List<EventWithData> getEventsWithData();

    @Transaction
    @Query("SELECT * FROM event WHERE id == :id")
    EventWithData getEventWithData(Integer id);
}

