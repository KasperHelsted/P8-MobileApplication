package p8project.sw801.data.local.db;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.model.db.*;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
//Implements dbhelper....
@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List> getAll() {
        return null;
    }

    @Override
    public Observable<List> getByIds(Integer[] ids) {
        return null;
    }

    @Override
    public Observable getById(Integer id) {
        return null;
    }

    @Override
    public Observable<Integer> getCount() {
        return null;
    }

    @Override
    public Observable<Boolean> isEmpty() {
        return null;
    }

    @Override
    public Observable<Boolean> insert(Object obj) {
        return null;
    }

    @Override
    public Observable<Boolean> insertAll(Object[] obj) {
        return null;
    }

    @Override
    public Observable<Boolean> update(Object obj) {
        return null;
    }

    @Override
    public Observable<Boolean> delete(Object obj) {
        return null;
    }
}
