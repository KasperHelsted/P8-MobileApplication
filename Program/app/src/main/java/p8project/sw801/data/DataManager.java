package p8project.sw801.data;

import java.util.List;

import io.reactivex.Observable;
import p8project.sw801.data.local.db.DbHelper;
import p8project.sw801.data.local.prefs.*;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
public interface DataManager extends DbHelper, PreferencesHelper {

    void setUserAsLoggedOut();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);

    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }

}
