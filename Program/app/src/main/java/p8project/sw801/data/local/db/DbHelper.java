package p8project.sw801.data.local.db;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Kasper Helsted on 3/26/2018.
 */

public interface DbHelper<T> {
    Observable<List<T>> getAll();

    Observable<List<T>> getByIds(final Integer[] ids);

    Observable<T> getById(final Integer id);

    Observable<Integer> getCount();

    Observable<Boolean> isEmpty();

    Observable<Boolean> insert(final T obj);

    Observable<Boolean> insertAll(final T... obj);

    Observable<Boolean> update(final T obj);

    Observable<Boolean> delete(final T obj);
}
