package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Chain;
import p8project.sw801.data.model.db.Event;

@Dao
public interface ChainDao {
    @Query("SELECT * FROM chain")
    List<Chain> getAll();

    @Query("SELECT COUNT(*) from chain")
    Integer count();

    @Query("SELECT * FROM chain WHERE active=1")
    List<Chain> getActiveChains();

    @Query("SELECT * FROM chain WHERE id IN (:chainIds)")
    List<Chain> loadAllByIds(Integer[] chainIds);

    @Query("SELECT * FROM chain WHERE id == :chainId LIMIT 1")
    Chain loadById(Integer chainId);

    @Insert
    void insertAll(List<Chain> chains);

    @Insert
    void insert(Chain chain);

    @Update
    void update(Chain chain);

    @Delete
    void delete(Chain chain);


}
