package p8project.sw801.data;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.local.prefs.PreferencesHelper;
import p8project.sw801.data.model.db.User;

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
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        //mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }


    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }



    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null);
    }


    @Override
    public void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath) {

        setAccessToken(accessToken);
        setCurrentUserId(userId);
        setCurrentUserLoggedInMode(loggedInMode);
        setCurrentUserName(userName);
        setCurrentUserEmail(email);
        setCurrentUserProfilePicUrl(profilePicPath);
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
