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
    public static final String TAG = "NotifyUs";
    public static PHHueSDK phHueSDK = null;
    private static PHBridge bridge = null;
    private static Context context = null;
    private static boolean lastSearchWasIPScan = false;
    /**
     * An eventlistener from the HueSDK, used to update information from the bridge
     * and to connect and confirm connection and reconnect on failure
     */
    private static PHSDKListener phsdkListener = new PHSDKListener() {

        /**
         * Everytime the Bridge updates its information, this method is called
         * @param list -
         * @param phBridge phbridge object containing the new information
         */
        @Override
        public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {
            Log.w(HueUtilities.TAG, "On CacheUpdated");

        }

        /**
         * When the bridge is connected, this method is called
         * Used to set the active bridge in the SDK for usage in methods
         * @param phBridge Bridge that connected
         * @param s information string with connection data
         */
        @Override
        public void onBridgeConnected(PHBridge phBridge, String s) {
            phHueSDK.setSelectedBridge(phBridge);
        }

        /**
         * If the connection was successful but it did not establish connection because it needs auth
         * this method is called.
         * @param phAccessPoint Accesspoint that requires auth
         */
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

        /**
         * required by interface, not used in this class
         * Since we are only connecting to already known bridges
         * @param list
         */
        @Override
        public void onAccessPointsFound(List<PHAccessPoint> list) {

        }

        /**
         * General bridge errorhandling, most errors does not have a direct way to fix besides reconnecting
         * So mostly used for knowing there happenend an error or to handle auth flow
         * @param code errorcode
         * @param message message of error
         */
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

        /**
         * OnConnectionResumed - required by interface
         * Not used since we do not resume a connection in this class, but rather establish a new connecting
         * everytime we need to use the bridge again - hence freeing data in memory on the phone while not using the bridge functionality
         * @param phBridge
         */
        @Override
        public void onConnectionResumed(PHBridge phBridge) {

        }

        /**
         * If ConnectionLost add accesspoint to a list of DisconnectedAccessPoints
         * @param phAccessPoint
         */
        @Override
        public void onConnectionLost(PHAccessPoint phAccessPoint) {
            Log.v(HueUtilities.TAG, "onConnectionLost : " + phAccessPoint.getIpAddress());
            if (!phHueSDK.getDisconnectedAccessPoint().contains(phAccessPoint)) {
                phHueSDK.getDisconnectedAccessPoint().add(phAccessPoint);
            }
        }

        /**
         * Data errors from the SDK side - Mostly JSON related errors
         *  just printed out for the information
         * @param list List of parse errors
         */
        @Override
        public void onParsingErrors(List<PHHueParsingError> list) {
            for (PHHueParsingError parsingError : list) {
                Log.e(HueUtilities.TAG, "ParsingError : " + parsingError.getMessage());
            }

        }
    };

    /**
     * Sets up the SDK for initial usage
     * Creates an instance with appropriate information and registers an SDKListener
     */
    public static void setupSDK() {
        if (phHueSDK == null) {
            phHueSDK = PHHueSDK.create();
            phHueSDK.setAppName(TAG);
            phHueSDK.setDeviceName(android.os.Build.MODEL);
            phHueSDK.getNotificationManager().registerSDKListener(phsdkListener);
        }

    }

    /**
     * Takes a bridge object and attempts to connect to bridge
     *
     * @param mhueBridge Bridge object to connect to
     * @return a boolean value indicating success of connection
     */
    public static boolean connectToBridge(HueBridge mhueBridge) {
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
        } catch (PHHueException e) {
            return true;
        } catch (IllegalArgumentException f) {
            return false;
        }
        return true;
    }

    /**
     * Change the lightstate of a specific light connected to the bridge
     *
     * @param id         id of the lightbulb to change
     * @param hue        Hue level
     * @param brightness Brightness level
     */
    public static void changeLightstate(String id, int hue, int brightness) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        for (PHLight light : allLights) {
            if (light.getUniqueId().equals(id)) {
                PHLightState lightState = new PHLightState();
                lightState.setHue(hue);
                lightState.setBrightness(brightness);
                bridge.updateLightState(light, lightState);
            }

        }
    }

    /**
     * Turns a lightbulb to state on
     *
     * @param id lightbulb id
     */
    public static void turnLightOn(String id) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        for (PHLight light : allLights) {
            if (light.getUniqueId().equals(id.toString())) {
                PHLightState lightState = new PHLightState();
                lightState.setOn(true);
                bridge.updateLightState(light, lightState);
            }

        }
    }

    /**
     * Turns a lightbulb to state off
     *
     * @param id lightbulb id
     */
    public static void turnLightOff(String id) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        List<PHLight> allLights = bridge.getResourceCache().getAllLights();
        for (PHLight light : allLights) {
            if (light.getUniqueId().equals(id.toString())) {
                PHLightState lightState = new PHLightState();
                lightState.setOn(false);
                bridge.updateLightState(light, lightState);
            }

        }
    }


}
