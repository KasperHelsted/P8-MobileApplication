package p8project.sw801.data.local.db.EntityHelpers;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.AppDatabase;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.model.db.Event;

/**
 * Created by Kasper Helsted on 3/26/2018.
 */

@Singleton
public class EventHelper implements DbHelper<Event> {
    private final AppDatabase mAppDatabase;

    @Inject
    public EventHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Event>> getAll() {
        return Observable.fromCallable(new Callable<List<Event>>() {
            @Override
            public List<Event> call() throws Exception {
                return mAppDatabase.eventDao().getAll();
            }
        });
    }

    @Override
    public Observable<Event> getById(final Integer id) {
        return Observable.fromCallable(new Callable<Event>() {
            @Override
            public Event call() throws Exception {
                return mAppDatabase.eventDao().loadById(id);
            }
        });
    }

    @Override
    public Observable<List<Event>> getByIds(final Integer[] ids) {
        return Observable.fromCallable(new Callable<List<Event>>() {
            @Override
            public List<Event> call() throws Exception {
                return mAppDatabase.eventDao().loadAllByIds(ids);
            }
        });
    }

    @Override
    public Observable<Integer> getCount() {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mAppDatabase.eventDao().count();
            }
        });
    }

    @Override
    public Observable<Boolean> isEmpty() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return mAppDatabase.eventDao().count() == 0;
            }
        });
    }

    @Override
    public Observable<Boolean> insert(final Event obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.eventDao().insert(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertAll(final Event... obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.eventDao().insertAll(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> update(final Event obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.eventDao().update(obj);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> delete(final Event obj) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.eventDao().delete(obj);
                return true;
            }
        });
    }
}
