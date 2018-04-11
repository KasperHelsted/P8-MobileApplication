package p8project.sw801.data.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import p8project.sw801.data.model.db.User;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
@Dao
public interface UserDao {

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    User findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Query("SELECT * FROM users")
    List<User> loadAll();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<User> loadAllByIds(List<Integer> userIds);
}