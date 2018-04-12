package p8project.sw801.data.local.db.EntityHelpers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.model.db.GlobalMute;

/**
 * Created by Kasper Helsted on 3/26/2018.
 */

@Singleton
public class GlobalMuteHelper implements DbHelper<GlobalMute> {
    private final AppDatabase mAppDatabase;

    @Inject
    public GlobalMuteHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<GlobalMute>> getAll() {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().getAll());
    }

    @Override
    public Observable<GlobalMute> getById(final Integer id) {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().loadById(id));
    }

    @Override
    public Observable<List<GlobalMute>> getByIds(final Integer[] ids) {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().loadAllByIds(ids));
    }

    @Override
    public Observable<Integer> getCount() {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().count());
    }

    @Override
    public Observable<Boolean> isEmpty() {
        return Observable.fromCallable(() -> mAppDatabase.globalMuteDao().count() == 0);
    }

    @Override
    public Observable<Boolean> insert(final GlobalMute obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().insert(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> insertAll(final GlobalMute... obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().insertAll(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> update(final GlobalMute obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().update(obj);
            return true;
        });
    }

    @Override
    public Observable<Boolean> delete(final GlobalMute obj) {
        return Observable.fromCallable(() -> {
            mAppDatabase.globalMuteDao().delete(obj);
            return true;
        });
    }
}
