package p8project.sw801.utils;

import android.content.Context;
import android.util.Log;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.exception.PHHueException;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import java.util.List;

import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.ui.custom.PHWizardAlertDialog;

public final class HueUtilities {
    public static PHHueSDK phHueSDK;
    public static final String TAG = "NotifyUs";
    private static PHBridge bridge = null;
    private static Context context = null;
    private static boolean lastSearchWasIPScan = false;


    public static void setupSDK()
    {
        phHueSDK = PHHueSDK.create();
        phHueSDK.setAppName(TAG);
        phHueSDK.setDeviceName(android.os.Build.MODEL);
        phHueSDK.getNotificationManager().registerSDKListener(phsdkListener);
    }

    public static boolean connectToBridge(HueBridge mhueBridge){
        PHAccessPoint lastAccessPoint = new PHAccessPoint();
        lastAccessPoint.setIpAddress(mhueBridge.getDeviceIP());
        lastAccessPoint.setUsername(mhueBridge.getUsername());
        PHBridge connectedBridge = phHueSDK.getSelectedBridge();
        if (connectedBridge != null) {
            String connectedIP = connectedBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
            if (connectedIP != null) {   // We are already connected here:-
                phHueSDK.disableHeartbeat(connectedBridge);
                phHueSDK.disconnect(connectedBridge);
            }
        }
        try {
            phHueSDK.connect(lastAccessPoint);
        }
        catch (PHHueException e){
            return true;
        }
        catch (IllegalArgumentException f){
            return false;
        }
        return true;
    }
    private static PHSDKListener phsdkListener = new PHSDKListener() {

        @Override
        public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
            Log.w(HueUtilities.TAG, "On CacheUpdated");

        }

        @Override
        public void onBridgeConnected(PHBridge phBridge, String s) {
            phHueSDK.setSelectedBridge(phBridge);
        }


        @Override
        public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {
            Log.v(HueUtilities.TAG, "onConnectionResumed" + bridge.getResourceCache().getBridgeConfiguration().getIpAddress());
            phHueSDK.getLastHeartbeat().put(bridge.getResourceCache().getBridgeConfiguration().getIpAddress(), System.currentTimeMillis());
            for (int i = 0; i < phHueSDK.getDisconnectedAccessPoint().size(); i++) {

                if (phHueSDK.getDisconnectedAccessPoint().get(i).getIpAddress().equals(bridge.getResourceCache().getBridgeConfiguration().getIpAddress())) {
                    phHueSDK.getDisconnectedAccessPoint().remove(i);
                }
            }
        }

        @Override
        public void onAccessPointsFound(List<PHAccessPoint> list) {

        }

        @Override
        public void onError(int code, final String message) {
            Log.e(HueUtilities.TAG, "on Error Called : " + code + ":" + message);
            if (code == PHHueError.NO_CONNECTION) {
                Log.w(HueUtilities.TAG, "On No Connection");
            } else if (code == PHHueError.AUTHENTICATION_FAILED || code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                PHWizardAlertDialog.getInstance().closeProgressDialog();
            } else if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                Log.w(HueUtilities.TAG, "Bridge Not Responding . . . ");
                //todo CANNOT TO TOASTS WHAT DO?
            } else if (code == PHMessageType.BRIDGE_NOT_FOUND) {

                if (!lastSearchWasIPScan) {  // Perform an IP Scan (backup mechanism) if UPNP and Portal Search fails.
                    phHueSDK = PHHueSDK.getInstance();
                    PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
                    sm.search(false, false, true);
                    lastSearchWasIPScan = true;
                } else {
                    PHWizardAlertDialog.getInstance().closeProgressDialog();
                }
            }
        }

        @Override
        public void onConnectionResumed(PHBridge phBridge) {

        }

        @Override
        public void onConnectionLost(PHAccessPoint phAccessPoint) {
            Log.v(HueUtilities.TAG, "onConnectionLost : " + phAccessPoint.getIpAddress());
            if (!phHueSDK.getDisconnectedAccessPoint().contains(phAccessPoint)) {
                phHueSDK.getDisconnectedAccessPoint().add(phAccessPoint);
            }
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> list) {
            for (PHHueParsingError parsingError : list) {
                Log.e(HueUtilities.TAG, "ParsingError : " + parsingError.getMessage());
            }

        }
    };

    public static void changeLightstate(String id, int hue, int brightness){
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        for (PHLight light : allLights)
        {
            if (light.getUniqueId() == id)
            {
                PHLightState lightState = new PHLightState();
                lightState.setHue(hue);
                lightState.setBrightness(brightness);
                bridge.updateLightState(light, lightState);
            }

        }
    }

    public static void turnLightOn(String id){
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        for (PHLight light : allLights)
        {
            if (light.getUniqueId() == id.toString())
            {
                PHLightState lightState = new PHLightState();
                lightState.setOn(true);
                bridge.updateLightState(light, lightState);
            }

        }
    }
    public static void turnLightOff(String id){
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        for (PHLight light : allLights)
        {
            if (light.getUniqueId().equals(id.toString()))
            {
                PHLightState lightState = new PHLightState();
                lightState.setOn(false);
                bridge.updateLightState(light, lightState);
            }

        }
    }


}
