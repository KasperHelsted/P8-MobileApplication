package p8project.sw801.data.local.db.EntityHelpers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.model.db.When;

/**
 * Created by Kasper Helsted on 3/26/2018.
 */

@Singleton
public class WhenHelper implements DbHelper<When> {
    private final AppDatabase mAppDatabase;

    @Inject
    public WhenHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<When>> getAll() {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().getAll());
    }

    @Override
    public Observable<List<When>> getByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().loadAllByIds(ids));
    }

    @Override
    public Observable<When> getById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().loadById(id));
    }

    @Override
    public Observable<Integer> getCount() {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().count());
    }

    @Override
    public Observable<Boolean> isEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.whenDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insert(final When obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().insert(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAll(final When... obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().insertAll(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> update(final When obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().update(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> delete(final When obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.whenDao().delete(obj);
            return true;
        });
    }
}
