package p8project.sw801.data;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.local.prefs.PreferencesHelper;

public class AppDataManager implements DataManager {

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mGson = gson;
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
