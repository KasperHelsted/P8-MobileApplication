package p8project.sw801.data.local.db.EntityHelpers;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.model.db.PredefinedLocation;

/**
 * Created by Kasper Helsted on 3/26/2018.
 */

@Singleton
public class PredefinedLocationHelper implements DbHelper<PredefinedLocation> {
    private final AppDatabase mAppDatabase;

    @Inject
    public PredefinedLocationHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<PredefinedLocation>> getAll() {
        return Observable.fromCallable(new Callable<List<PredefinedLocation>>() {
            @Override
            public List<PredefinedLocation> call() throws Exception {
                return mAppDatabase.predefinedLocationDao().getAll();
            }
        });
    }

    @Override
    public Observable<PredefinedLocation> getById(final Integer id) {
        return Observable.fromCallable(new Callable<PredefinedLocation>() {
            @Override
            public PredefinedLocation call() throws Exception {
                return mAppDatabase.predefinedLocationDao().loadById(id);
            }
        });
    }

    @Override
    public Observable<List<PredefinedLocation>> getByIds(final Integer[] ids) {
        return Observable.fromCallable(new Callable<List<PredefinedLocation>>() {
            @Override
            public List<PredefinedLocation> call() throws Exception {
                return mAppDatabase.predefinedLocationDao().loadAllByIds(ids);
            }
        });
    }

    @Override
    public Observable<Integer> getCount() {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mAppDatabase.predefinedLocationDao().countPredefinedLocations();
            }
        });
    }

    @Override
    public Observable<Boolean> isEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mAppDatabase.predefinedLocationDao().countPredefinedLocations() == 0;
            }
        });
    }

    @Override
    public Observable<Boolean> insert(final PredefinedLocation obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.predefinedLocationDao().insert(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertAll(final PredefinedLocation... obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.predefinedLocationDao().insertAll(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> update(final PredefinedLocation obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.predefinedLocationDao().update(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> delete(final PredefinedLocation obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.predefinedLocationDao().delete(obj);
                return true;
            }
        });
    }
}
