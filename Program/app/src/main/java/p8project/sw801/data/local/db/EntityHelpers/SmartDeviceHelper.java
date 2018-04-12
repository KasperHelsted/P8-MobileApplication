package p8project.sw801.data.local.db.EntityHelpers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.model.db.SmartDevice;

/**
 * Created by Kasper Helsted on 3/26/2018.
 */

@Singleton
public class SmartDeviceHelper implements DbHelper<SmartDevice> {
    private final AppDatabase mAppDatabase;

    @Inject
    public SmartDeviceHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<SmartDevice>> getAll() {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().getAll());
    }

    @Override
    public Observable<List<SmartDevice>> getByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().loadAllByIds(ids));
    }

    @Override
    public Observable<SmartDevice> getById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().loadById(id));
    }

    @Override
    public Observable<Integer> getCount() {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().count());
    }

    @Override
    public Observable<Boolean> isEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.smartDeviceDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insert(final SmartDevice obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.smartDeviceDao().insert(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAll(final SmartDevice... obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.smartDeviceDao().insertAll(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> update(final SmartDevice obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.smartDeviceDao().update(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> delete(final SmartDevice obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.smartDeviceDao().delete(obj);
            return true;
        });
    }
}
