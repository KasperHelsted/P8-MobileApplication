package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.databinding.ActivityAddSmartDeviceBinding;
import p8project.sw801.ui.SmartDevice.AccessPointListAdapter;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.custom.PHPushlinkActivity;
import p8project.sw801.ui.custom.PHWizardAlertDialog;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceFragment;
import p8project.sw801.utils.CommonUtils;
import p8project.sw801.utils.HueUtilities;

public class AddSmartDeviceActivity extends BaseActivity<ActivityAddSmartDeviceBinding, AddSmartDeviceViewModel> implements AdapterView.OnItemClickListener, AddSmartDeviceNavigator, HasSupportFragmentInjector {

    private AccessPointListAdapter adapter;
    private boolean lastSearchWasIPScan = false;
    private HueBridge hueBridge = null;
    private PHHueSDK phHueSDK;
    @Inject
    AddSmartDeviceViewModel mSmartDeviceViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private ActivityAddSmartDeviceBinding mActivityAddSmartDeviceBinding;
    PHBridge mbridge;
    private HueBridge mhueBridge;



    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_smart_device;
    }

    @Override
    public AddSmartDeviceViewModel getViewModel() {
        return mSmartDeviceViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddSmartDeviceBinding = getViewDataBinding();
        mSmartDeviceViewModel.setNavigator(this);
        HueUtilities.setupSDK();
        PHAccessPoint lastAccessPoint = new PHAccessPoint();
        lastAccessPoint.setIpAddress("localhost:80");
        lastAccessPoint.setUsername("newdeveloper");
        HueUtilities.phHueSDK.getNotificationManager().registerSDKListener(listener);
        HueUtilities.phHueSDK.connect(lastAccessPoint);



        //setUp();
    }
    private PHSDKListener listener = new PHSDKListener() {
        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPoint) {
            Log.w(HueUtilities.TAG, "Access Points Found. " + accessPoint.size());

            PHWizardAlertDialog.getInstance().closeProgressDialog();
            if (accessPoint != null && accessPoint.size() > 0) {
                phHueSDK.getAccessPointsFound().clear();
                phHueSDK.getAccessPointsFound().addAll(accessPoint);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(phHueSDK.getAccessPointsFound());
                    }
                });

            }

        }

        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge bridge) {
            Log.w(HueUtilities.TAG, "On CacheUpdated");

        }

        @Override
        public void onBridgeConnected(PHBridge b, String username) {
            phHueSDK.setSelectedBridge(b);
            phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
            phHueSDK.getLastHeartbeat().put(b.getResourceCache().getBridgeConfiguration() .getIpAddress(), System.currentTimeMillis());
            mhueBridge.setUsername(username);
            mhueBridge.setDeviceIP(b.getResourceCache().getBridgeConfiguration().getIpAddress());
            PHWizardAlertDialog.getInstance().closeProgressDialog();
            startMainActivity();
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            Log.w(HueUtilities.TAG, "Authentication Required.");
            phHueSDK.startPushlinkAuthentication(accessPoint);
            startActivity(new Intent(AddSmartDeviceActivity.this, PHPushlinkActivity.class));

        }

        @Override
        public void onConnectionResumed(PHBridge bridge) {
            if (AddSmartDeviceActivity.this.isFinishing())
                return;

            Log.v(HueUtilities.TAG, "onConnectionResumed" + bridge.getResourceCache().getBridgeConfiguration().getIpAddress());
            phHueSDK.getLastHeartbeat().put(bridge.getResourceCache().getBridgeConfiguration().getIpAddress(),  System.currentTimeMillis());
            for (int i = 0; i < phHueSDK.getDisconnectedAccessPoint().size(); i++) {

                if (phHueSDK.getDisconnectedAccessPoint().get(i).getIpAddress().equals(bridge.getResourceCache().getBridgeConfiguration().getIpAddress())) {
                    phHueSDK.getDisconnectedAccessPoint().remove(i);
                }
            }

        }

        @Override
        public void onConnectionLost(PHAccessPoint accessPoint) {
            Log.v(HueUtilities.TAG, "onConnectionLost : " + accessPoint.getIpAddress());
            if (!phHueSDK.getDisconnectedAccessPoint().contains(accessPoint)) {
                phHueSDK.getDisconnectedAccessPoint().add(accessPoint);
            }
        }

        @Override
        public void onError(int code, final String message) {
            Log.e(HueUtilities.TAG, "on Error Called : " + code + ":" + message);

            if (code == PHHueError.NO_CONNECTION) {
                Log.w(HueUtilities.TAG, "On No Connection");
            }
            else if (code == PHHueError.AUTHENTICATION_FAILED || code==PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                PHWizardAlertDialog.getInstance().closeProgressDialog();
            }
            else if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                Log.w(HueUtilities.TAG, "Bridge Not Responding . . . ");
                PHWizardAlertDialog.getInstance().closeProgressDialog();
                AddSmartDeviceActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PHWizardAlertDialog.showErrorDialog(AddSmartDeviceActivity.this, message, R.string.button_ok);
                    }
                });

            }
            else if (code == PHMessageType.BRIDGE_NOT_FOUND) {

                if (!lastSearchWasIPScan) {  // Perform an IP Scan (backup mechanism) if UPNP and Portal Search fails.
                    phHueSDK = PHHueSDK.getInstance();
                    PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
                    sm.search(false, false, true);
                    lastSearchWasIPScan=true;
                }
                else {
                    PHWizardAlertDialog.getInstance().closeProgressDialog();
                    AddSmartDeviceActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            PHWizardAlertDialog.showErrorDialog(AddSmartDeviceActivity.this, message, R.string.button_ok);
                        }
                    });
                }


            }
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError: parsingErrorsList) {
                Log.e(HueUtilities.TAG, "ParsingError : " + parsingError.getMessage());
            }
        }
    };


        public void setUp()
    {
        phHueSDK = PHHueSDK.create();
        mSmartDeviceViewModel.getBridges();
        // Register the PHSDKListener to receive callbacks from the bridge.
        //HueUtilities.phHueSDK.getNotificationManager().registerSDKListener(listenerSDK);
        adapter = new AccessPointListAdapter(getApplicationContext(), HueUtilities.phHueSDK.getAccessPointsFound());

        ListView accessPointList = (ListView) findViewById(R.id.bridge_list);
        accessPointList.setAdapter(adapter);
        accessPointList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PHAccessPoint accessPoint = (PHAccessPoint) adapter.getItem(i);

                PHBridge connectedBridge = HueUtilities.phHueSDK.getSelectedBridge();

                if (connectedBridge != null) {
                    String connectedIP = connectedBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
                    if (connectedIP != null) {   // We are already connected here:-
                        HueUtilities.phHueSDK.disableHeartbeat(connectedBridge);
                        HueUtilities.phHueSDK.disconnect(connectedBridge);
                    }
                }
                PHWizardAlertDialog.getInstance().showProgressDialog(R.string.connecting, AddSmartDeviceActivity.this);
                HueUtilities.phHueSDK.connect(accessPoint);
            }
        });



    }

    public void searchOrConnect()
    {
        if (hueBridge != null && CommonUtils.isNullOrEmpty(hueBridge.getDeviceIP()))
        {
            PHAccessPoint lastAccessPoint = new PHAccessPoint();
            lastAccessPoint.setIpAddress(hueBridge.getDeviceIP());
            lastAccessPoint.setUsername(hueBridge.getUsername());
            if (!HueUtilities.phHueSDK.isAccessPointConnected(lastAccessPoint)) {
                PHWizardAlertDialog.getInstance().showProgressDialog(R.string.connecting, AddSmartDeviceActivity.this);
                HueUtilities.phHueSDK.connect(lastAccessPoint);
            }
        }
        else
            {
            doBridgeSearch();
        }
    }



    public void doBridgeSearch() {
        PHWizardAlertDialog.getInstance().showProgressDialog(R.string.search_progress, AddSmartDeviceActivity.this);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) HueUtilities.phHueSDK.getSDKService(HueUtilities.phHueSDK.SEARCH_BRIDGE);
        // Start the UPNP Searching of local bridges.
        sm.search(true, true);
    }

    // Starting the main activity this way, prevents the PushLink Activity being shown when pressing the back button.
    public void startMainActivity() {
        ChangeToSmartDevice();
    }
    public void ChangeToSmartDevice(){
        // Create fragment and give it an argument specifying the article it should show
        MySmartDeviceFragment newFragment = new MySmartDeviceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.homeactivity, newFragment, "Smart" );
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        //Removes the fragment after changing the page
        transaction.remove(newFragment);

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }
    @Override
    public void searchForBridge(){
        if (hueBridge != null){
        int i = 0;
        while(i < 10) {
            PHBridge hbridge = HueUtilities.phHueSDK.getSelectedBridge();
            List<PHLight> allLights = hbridge.getResourceCache().getAllLights();
            Random rand = new Random();

            for (PHLight light : allLights) {
                PHLightState lightState = new PHLightState();
                lightState.setHue(rand.nextInt(65535));
                lightState.setBrightness(rand.nextInt(254));
                i++;
                hbridge.updateLightState(light, lightState, (PHLightListener) listener);
            }
        }
        }
        setUp();
    }

    @Override
    public void setupBridges(List<SmartDevice> smartDeviceList) {
        if (smartDeviceList != null && !smartDeviceList.isEmpty()){
            for (SmartDevice bridge: smartDeviceList)
            {
                if(bridge.getInternalIdentifier() == 1){
                    //hueBridge = (HueBridge) bridge;
                    //break;
                }
            }
            searchOrConnect();
        }
        else{
            doBridgeSearch();
        }
    }
    /*
    public PHSDKListener listenerSDK = new PHSDKListener() {
        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPoint) {
            PHWizardAlertDialog.getInstance().closeProgressDialog();
            if (accessPoint != null && accessPoint.size() > 0) {
                HueUtilities.phHueSDK.getAccessPointsFound().clear();
                HueUtilities.phHueSDK.getAccessPointsFound().addAll(accessPoint);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(HueUtilities.phHueSDK.getAccessPointsFound());
                    }
                });

            }

        }

        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge bridge) {
            Log.w(HueUtilities.TAG, "On CacheUpdated");
        }

        @Override
        public void onBridgeConnected(PHBridge b, String username)
        {
            HueUtilities.phHueSDK.setSelectedBridge(b);
            HueUtilities.phHueSDK.enableHeartbeat(b, HueUtilities.phHueSDK.HB_INTERVAL);
            HueUtilities.phHueSDK.getLastHeartbeat().put(b.getResourceCache().getBridgeConfiguration() .getIpAddress(), System.currentTimeMillis());
            if (hueBridge.equals(null))
            {
                HueBridge newHueBridge = new HueBridge();
                newHueBridge.setDeviceIP(b.getResourceCache().getBridgeConfiguration().getIpAddress());
                newHueBridge.setUsername(username);
                mSmartDeviceViewModel.insertBridge(newHueBridge);
                hueBridge = newHueBridge;
            }
            PHWizardAlertDialog.getInstance().closeProgressDialog();
            finish();

            startMainActivity();
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            Log.w(HueUtilities.TAG, "Authentication Required.");
            HueUtilities.phHueSDK.startPushlinkAuthentication(accessPoint);
            startActivity(new Intent(AddSmartDeviceActivity.this, PHWizardAlertDialog.class));

        }

        @Override
        public void onConnectionResumed(PHBridge bridge) {
            mbridge = bridge;
                PHWizardAlertDialog.getInstance().closeProgressDialog();
            if (AddSmartDeviceActivity.this.isFinishing()){
                return;
            }
            Log.v(HueUtilities.TAG, "onConnectionResumed " + bridge.getResourceCache().getBridgeConfiguration().getIpAddress());
            HueUtilities.phHueSDK.getLastHeartbeat().put(bridge.getResourceCache().getBridgeConfiguration().getIpAddress(),  System.currentTimeMillis());
            for (int i = 0; i < HueUtilities.phHueSDK.getDisconnectedAccessPoint().size(); i++) {

                if (HueUtilities.phHueSDK.getDisconnectedAccessPoint().get(i).getIpAddress().equals(bridge.getResourceCache().getBridgeConfiguration().getIpAddress())) {
                    HueUtilities.phHueSDK.getDisconnectedAccessPoint().remove(i);
                }
            }


        }

        @Override
        public void onConnectionLost(PHAccessPoint accessPoint) {
            Log.v(HueUtilities.TAG, "onConnectionLost : " + accessPoint.getIpAddress());
            if (!HueUtilities.phHueSDK.getDisconnectedAccessPoint().contains(accessPoint)) {
                HueUtilities.phHueSDK.getDisconnectedAccessPoint().add(accessPoint);
            }
        }

        @Override
        public void onError(int code, final String message) {
            Log.e(HueUtilities.TAG, "on Error Called : " + code + ":" + message);

            if (code == PHHueError.NO_CONNECTION) {
                Log.w(HueUtilities.TAG, "On No Connection");
            }
            else if (code == PHHueError.AUTHENTICATION_FAILED || code== PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                PHWizardAlertDialog.getInstance().closeProgressDialog();
            }
            else if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                Log.w(HueUtilities.TAG, "Bridge Not Responding . . . ");
                PHWizardAlertDialog.getInstance().closeProgressDialog();
                AddSmartDeviceActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PHWizardAlertDialog.showErrorDialog(AddSmartDeviceActivity.this, message, R.string.button_ok);
                    }
                });

            }
            else if (code == PHMessageType.BRIDGE_NOT_FOUND) {

                if (!lastSearchWasIPScan) {  // Perform an IP Scan (backup mechanism) if UPNP and Portal Search fails.
                    HueUtilities.phHueSDK = HueUtilities.phHueSDK.getInstance();
                    PHBridgeSearchManager sm = (PHBridgeSearchManager) HueUtilities.phHueSDK.getSDKService(HueUtilities.phHueSDK.SEARCH_BRIDGE);
                    sm.search(false, false, true);
                    lastSearchWasIPScan=true;
                }
                else {
                    PHWizardAlertDialog.getInstance().closeProgressDialog();
                    AddSmartDeviceActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            PHWizardAlertDialog.showErrorDialog(AddSmartDeviceActivity.this, message, R.string.button_ok);
                        }
                    });
                }


            }
        }

        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError: parsingErrorsList) {
                Log.e(HueUtilities.TAG, "ParsingError : " + parsingError.getMessage());
            }
        }
    };



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listenerSDK !=null) {
            HueUtilities.phHueSDK.getNotificationManager().unregisterSDKListener(listenerSDK);
        }
        HueUtilities.phHueSDK.disableAllHeartbeat();
    }
 */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PHAccessPoint accessPoint = (PHAccessPoint) adapter.getItem(position);

        PHBridge connectedBridge = HueUtilities.phHueSDK.getSelectedBridge();

        if (connectedBridge != null) {
            String connectedIP = connectedBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
            if (connectedIP != null) {   // We are already connected here:-
                HueUtilities.phHueSDK.disableHeartbeat(connectedBridge);
                HueUtilities.phHueSDK.disconnect(connectedBridge);
            }
        }
        PHWizardAlertDialog.getInstance().showProgressDialog(R.string.connecting, AddSmartDeviceActivity.this);
        HueUtilities.phHueSDK.connect(accessPoint);
    }

}
