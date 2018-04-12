package p8project.sw801.data.local.db.EntityHelpers;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.model.db.Trigger;

/**
 * Created by Kasper Helsted on 3/26/2018.
 */

@Singleton
public class TriggerHelper implements DbHelper<Trigger> {
    private final AppDatabase mAppDatabase;

    @Inject
    public TriggerHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Trigger>> getAll() {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().getAll());
    }

    @Override
    public Observable<List<Trigger>> getByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Trigger> getById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().loadById(id));
    }

    @Override
    public Observable<Integer> getCount() {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().count());
    }

    @Override
    public Observable<Boolean> isEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.triggerDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insert(final Trigger obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().insert(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAll(final Trigger... obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().insertAll(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> update(final Trigger obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().update(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> delete(final Trigger obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.triggerDao().delete(obj);
            return true;
        });
    }
}
