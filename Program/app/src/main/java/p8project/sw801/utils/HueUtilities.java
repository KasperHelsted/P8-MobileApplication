package p8project.sw801.utils;

import com.philips.lighting.hue.sdk.PHHueSDK;

import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;

public final class HueUtilities {
    private HueBridge hueBridge = null;
    public static PHHueSDK phHueSDK;
    public static final String TAG = "NotifyUs";
    private boolean lastSearchWasIPScan = false;
    public static final String LAST_CONNECTED_USERNAME      = "NotifyUs";
    public static final String LAST_CONNECTED_IP            = "LastConnectedIP";


    public static void setupSDK(){
        phHueSDK = PHHueSDK.create();
        phHueSDK.setAppName(TAG);
        phHueSDK.setDeviceName(android.os.Build.MODEL);
    }
}
