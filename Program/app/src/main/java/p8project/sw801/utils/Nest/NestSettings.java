package p8project.sw801.utils.Nest;

import android.content.Context;
import android.content.SharedPreferences;

import com.nestlabs.sdk.NestToken;

public class NestSettings {
    private static final String TOKEN_KEY = "token";
    private static final String EXPIRATION_KEY = "expiration";

    /**
     * Saves the NestToken in preferences, or removes from preferences if null.
     */
    public static void saveAuthToken(Context context, NestToken token) {
        if (token == null) { // remove access token and expiration entries
            getPrefs(context).edit().remove(TOKEN_KEY).remove(EXPIRATION_KEY).commit();
            return;
        }
        getPrefs(context).edit() // save access token in preferences with expiration
                .putString(TOKEN_KEY, token.getToken())
                .putLong(EXPIRATION_KEY, token.getExpiresIn())
                .commit();
    }

    /**
     * Loads token from preferences.
     */
    public static NestToken loadAuthToken(Context context) {
        final SharedPreferences prefs = getPrefs(context);
        final String token = prefs.getString(TOKEN_KEY, null);
        final long expirationDate = prefs.getLong(EXPIRATION_KEY, -1);

        if (token == null || expirationDate == -1) {
            return null;
        }
        return new NestToken(token, expirationDate);
    }

    /**
     * Helper to return the NestToken preference map.
     */
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(NestToken.class.getSimpleName(), 0);
    }

}
