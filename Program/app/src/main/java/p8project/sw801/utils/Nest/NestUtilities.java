package p8project.sw801.utils.Nest;

import android.content.Context;

import com.nestlabs.sdk.*;
import com.nestlabs.sdk.NestAuthActivity.*;

public final class NestUtilities {
    public static NestAPI nest;
    public static final int AUTH_TOKEN_REQUEST_CODE = 27015;
    private static final String TOKEN_KEY = "token";
    private static final String EXPIRATION_KEY = "expiration";
    //private static WwnClient wwnClient;
    private static NestToken mToken;
    private static Thermostat mThermostat;
    private static Structure mStructure;

    public static void InitializeNestForCurrentContext(Context ctx) {
        /*
        NestAPI.setAndroidContext(ctx);
        nest = NestAPI.getInstance();
        //Test
        mToken = NestSettings.loadAuthToken(ctx);
        wwnClient = new WwnClient(errorHandler);

        // Start streaming if auth token exists or launch authentication otherwise.
        if (mToken != null) {
            startWithListeners(mToken);
        } else {
            wwnClient.oauth2.setConfig(CLIENT_ID, CLIENT_SECRET, REDIRECT_URL);
            wwnClient.oauth2.launchAuthFlow(ctx, AUTH_TOKEN_REQUEST_CODE);
        }

        // If saved state exists then populate thermostat and structure details.

        if (ctx.savedInstanceState != null) {
            mThermostat = savedInstanceState.getParcelable(THERMOSTAT_KEY);
            mStructure = savedInstanceState.getParcelable(STRUCTURE_KEY);
            updateViews();
        }
    }

*/
    }
}
