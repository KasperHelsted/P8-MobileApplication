package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import p8project.sw801.data.model.db.Store;

@Dao
public interface StoreDao {

    @Query("SELECT * FROM store WHERE storeName==:name LIMIT 1")
    Store getStoreByName(String name);

    @Query("SELECT * FROM store")
    List<Store> getAll();

    @Query("SELECT COUNT(*) from store")
    Integer count();

    @Query("SELECT * FROM store WHERE id IN (:storeIds)")
    List<Store> loadAllByIds(Integer[] storeIds);

    @Query("SELECT * FROM store WHERE id == :storeId LIMIT 1")
    Store loadById(Integer storeId);

    @Insert
    void insertAll(Store... Stores);

    @Insert
    void insert(Store store);

    @Update
    void update(Store store);

    @Delete
    void delete(Store store);
}
