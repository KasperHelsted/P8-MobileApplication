package p8project.sw801.data;

import java.util.List;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.local.prefs.PreferencesHelper;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
public interface DataManager<T> extends DbHelper<T>, PreferencesHelper {
    //CoordinateHelper
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
