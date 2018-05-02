package p8project.sw801.utils.Nest;

import android.app.Activity;
import android.content.Context;

import com.firebase.client.Firebase;
import com.nestlabs.sdk.NestAPI;
import com.nestlabs.sdk.NestException;
import com.nestlabs.sdk.NestListener;
import com.nestlabs.sdk.NestToken;

import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;

import static p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceActivity.AUTH_TOKEN_REQUEST_CODE;

public final class NestUtilities {
    public static NestAPI nestAPI;
    public static boolean ready = false;


    public static void InitializeNestForCurrentContext(Context ctx, NestHub nestHub) {
        NestToken nestToken = new NestToken(nestHub.getBearerToken(),nestHub.getExpires());
        Firebase.setAndroidContext(ctx);
        NestAPI nest = NestAPI.getInstance();
        nest.authWithToken(nestToken, new NestListener.AuthListener() {
            @Override
            public void onAuthSuccess() {
                System.out.println("WE ARE CONNECTED!");
                ready = true;
                nestAPI = nest;
            }

            @Override
            public void onAuthFailure(NestException e) {
                System.out.println("WE FAILED TO AUTH!");
            }

            @Override
            public void onAuthRevoked() {
                System.out.println("AUTH REVOKED!");
                NestAPI.setAndroidContext(ctx);
                NestAPI nest = NestAPI.getInstance();
                nest.setConfig(nestHub.getClientId(),nestHub.getSecretId(),"http://localhost:8080/auth/nest/callback");
                nest.launchAuthFlow((Activity)ctx, AUTH_TOKEN_REQUEST_CODE);
            }
        });
        nestAPI = nest;
    }
}
