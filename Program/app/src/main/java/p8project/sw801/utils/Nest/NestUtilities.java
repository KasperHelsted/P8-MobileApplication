package p8project.sw801.utils.Nest;

import android.content.Context;
import android.util.Log;

import com.nestlabs.sdk.*;
import com.nestlabs.sdk.NestAuthActivity.*;

public final class NestUtilities {
    public static NestAPI nest;


    public static void InitializeNestForCurrentContext(Context ctx, String token) {
        nest = NestAPI.getInstance();

        //This instance does not work??

        // Authenticate with token.
        nest.authWithToken(token, new NestListener.AuthListener() {
            @Override
            public void onAuthSuccess() {
                Log.i("log", "FSAHFUSAHSAHUFASHFSAF");
            }

            @Override
            public void onAuthFailure(NestException e) {
                // Handle exceptions here.
            }

            @Override
            public void onAuthRevoked() {
                // Your previously authenticated connection has become unauthenticated.
                // Recommendation: Relaunch an auth flow with nest.launchAuthFlow().
            }
        });

    }
}
