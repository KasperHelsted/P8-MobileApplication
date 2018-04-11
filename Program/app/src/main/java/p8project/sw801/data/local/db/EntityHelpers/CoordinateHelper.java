package p8project.sw801.data.local.db.EntityHelpers;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.model.db.Coordinate;


/**
 * Created by Kasper Helsted on 3/26/2018.
 */

@Singleton
public class CoordinateHelper implements DbHelper<Coordinate> {
    private final AppDatabase mAppDatabase;

    @Inject
    public CoordinateHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Coordinate>> getAll() {
        return Observable.fromCallable(new Callable<List<Coordinate>>() {
            @Override
            public List<Coordinate> call() throws Exception {
                return mAppDatabase.coordinateDao().getAll();
            }
        });
    }

    @Override
    public Observable<Coordinate> getById(final Integer id) {
        return Observable.fromCallable(new Callable<Coordinate>() {
            @Override
            public Coordinate call() throws Exception {
                return mAppDatabase.coordinateDao().loadById(id);
            }
        });
    }

    @Override
    public Observable<List<Coordinate>> getByIds(final Integer[] ids) {
        return Observable.fromCallable(new Callable<List<Coordinate>>() {
            @Override
            public List<Coordinate> call() throws Exception {
                return mAppDatabase.coordinateDao().loadAllByIds(ids);
            }
        });
    }

    @Override
    public Observable<Integer> getCount() {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mAppDatabase.coordinateDao().count();
            }
        });
    }

    @Override
    public Observable<Boolean> isEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mAppDatabase.coordinateDao().count() == 0;
            }
        });
    }

    @Override
    public Observable<Boolean> insert(final Coordinate obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.coordinateDao().insert(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertAll(final Coordinate... obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.coordinateDao().insertAll(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> update(final Coordinate obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.coordinateDao().update(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> delete(final Coordinate obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.coordinateDao().delete(obj);
                return true;
            }
        });
    }
}
