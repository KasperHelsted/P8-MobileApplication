package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.nestlabs.sdk.*;

import com.nestlabs.sdk.GlobalUpdate;
import com.nestlabs.sdk.NestAuthActivity;
import com.nestlabs.sdk.NestConfig;
import com.nestlabs.sdk.NestException;
import com.nestlabs.sdk.NestListener;
import com.nestlabs.sdk.NestToken;
import com.nestlabs.sdk.Structure;
import com.nestlabs.sdk.Thermostat;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHHueParsingError;
import com.philips.lighting.model.PHLight;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;
import p8project.sw801.databinding.ActivityAddSmartDeviceBinding;
import p8project.sw801.ui.SmartDevice.AccessPointListAdapter;
import p8project.sw801.ui.SmartDevice.AddNestSmartDevice.AddNestSmartDevice;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.custom.PHPushlinkActivity;
import p8project.sw801.ui.custom.PHWizardAlertDialog;
import p8project.sw801.utils.CommonUtils;
import p8project.sw801.utils.HueUtilities;
import p8project.sw801.utils.Nest.*;

public class AddSmartDeviceActivity extends BaseActivity<ActivityAddSmartDeviceBinding, AddSmartDeviceViewModel> implements AdapterView.OnItemClickListener, AddSmartDeviceNavigator, HasSupportFragmentInjector {

    //Adapter for listing HueAccessPoints
    private AccessPointListAdapter adapter;
    private boolean lastSearchWasIPScan = false;
    //Class scope SDK access
    private PHHueSDK phHueSDK;
    //Mvvm injects
    @Inject
    AddSmartDeviceViewModel mSmartDeviceViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    //Mvvm view binding
    private ActivityAddSmartDeviceBinding mActivityAddSmartDeviceBinding;
    //Initializting a bridge object for null checking
    private HueBridge mhueBridge= null;
    private Button searchBridge;
    private Button searchNest;
    private ListView brigdeListview;
    private NestToken mToken;
    private String deviceIdtester;
    private Thermostat mThermostat;
    private Structure mStructure;
    private Activity mActivity;
    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String REDIRECT_URL;
    private NestAPI nestAPI;
    public static final int AUTH_TOKEN_REQUEST_CODE = 27015;



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
        //MVVM Bindings
        mActivityAddSmartDeviceBinding = getViewDataBinding();
        mSmartDeviceViewModel.setNavigator(this);
        //Setup the hue SDK
        phHueSDK = PHHueSDK.create();
        phHueSDK.setAppName("NotifyMe");
        phHueSDK.setDeviceName(android.os.Build.MODEL);

        //ViewBindings
        brigdeListview = mActivityAddSmartDeviceBinding.bridgeList;
        searchBridge = mActivityAddSmartDeviceBinding.findNewBridge;
        searchNest = mActivityAddSmartDeviceBinding.findNewNest;

        //
        mActivity = this;

        //Register a SDK listener for actions related to connection
        phHueSDK.getNotificationManager().registerSDKListener(phsdkListener);
        //Initial setup of listview
        adapter = new AccessPointListAdapter(getApplicationContext(), phHueSDK.getAccessPointsFound());
        brigdeListview.setOnItemClickListener(this);
        brigdeListview.setAdapter(adapter);
    }

    private PHSDKListener phsdkListener = new PHSDKListener() {
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
        public void onBridgeConnected(PHBridge b, String username)
        {
            SmartDevice sd = new SmartDevice();
            sd.setDeviceName(b.getResourceCache().getBridgeConfiguration().getName());
            sd.setInternalIdentifier(1);
            mhueBridge = new HueBridge();
            phHueSDK.setSelectedBridge(b);
            phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
            phHueSDK.getLastHeartbeat().put(b.getResourceCache().getBridgeConfiguration().getIpAddress(), System.currentTimeMillis());
            mhueBridge.setUsername(b.getResourceCache().getBridgeConfiguration().getUsername());
            mhueBridge.setDeviceIP(b.getResourceCache().getBridgeConfiguration().getIpAddress());
            List<PHLight> allLights = b.getResourceCache().getAllLights();
            List<HueLightbulbWhite> uniqueID = new ArrayList<>();
            for (PHLight pl : allLights){
                HueLightbulbWhite hbw = new HueLightbulbWhite();
                hbw.setDeviceName(pl.getName());
                hbw.setDeviceId(pl.getUniqueId());
                uniqueID.add(hbw);
            }
            //Transfer all data to Viewmodel
            mSmartDeviceViewModel.smartDeviceinsertHandler(sd,mhueBridge,uniqueID);
            PHWizardAlertDialog.getInstance().closeProgressDialog();
            ChangeToSmartDevice();
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
            phHueSDK.getLastHeartbeat().put(bridge.getResourceCache().getBridgeConfiguration().getIpAddress(), System.currentTimeMillis());
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
            } else if (code == PHHueError.AUTHENTICATION_FAILED || code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
                PHWizardAlertDialog.getInstance().closeProgressDialog();
            } else if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
                Log.w(HueUtilities.TAG, "Bridge Not Responding . . . ");
                PHWizardAlertDialog.getInstance().closeProgressDialog();
            } else if (code == PHMessageType.BRIDGE_NOT_FOUND) {

                if (!lastSearchWasIPScan) {  // Perform an IP Scan (backup mechanism) if UPNP and Portal Search fails.
                    phHueSDK = PHHueSDK.getInstance();
                    PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
                    sm.search(false, false, true);
                    lastSearchWasIPScan = true;
                } else {
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
            for (PHHueParsingError parsingError : parsingErrorsList) {
                Log.e(HueUtilities.TAG, "ParsingError : " + parsingError.getMessage());
            }
        }
    };

    public void searchOrConnect()
    {
        if (mhueBridge != null && !CommonUtils.isNullOrEmpty(mhueBridge.getDeviceIP()) && !CommonUtils.isNullOrEmpty(mhueBridge.getUsername())) {
            mhueBridge = new HueBridge();
            PHAccessPoint lastAccessPoint = new PHAccessPoint();
            lastAccessPoint.setIpAddress(mhueBridge.getDeviceIP());
            lastAccessPoint.setUsername(mhueBridge.getUsername());
            if (!phHueSDK.isAccessPointConnected(lastAccessPoint))
            {
                PHWizardAlertDialog.getInstance().showProgressDialog(R.string.connecting, AddSmartDeviceActivity.this);
                phHueSDK.connect(lastAccessPoint);
            }
        } else {
            doBridgeSearch();
        }
    }


    public void doBridgeSearch()
    {
        PHWizardAlertDialog.getInstance().showProgressDialog(R.string.search_progress, AddSmartDeviceActivity.this);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(phHueSDK.SEARCH_BRIDGE);
        // Start the UPNP Searching of local bridges.
        sm.search(true, true);
    }

    public void ChangeToSmartDevice() {
        PHWizardAlertDialog.getInstance().closeProgressDialog();
        finish();
    }

    @Override
    public void searchForBridge(){
        mSmartDeviceViewModel.getBridges();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        PHAccessPoint accessPoint = (PHAccessPoint) adapter.getItem(position);
        PHBridge connectedBridge = phHueSDK.getSelectedBridge();

        if (connectedBridge != null) {
            String connectedIP = connectedBridge.getResourceCache().getBridgeConfiguration().getIpAddress();
            if (connectedIP != null) {   // We are already connected here:-
                phHueSDK.disableHeartbeat(connectedBridge);
                phHueSDK.disconnect(connectedBridge);
            }
        }
        PHWizardAlertDialog.getInstance().showProgressDialog(R.string.connecting, AddSmartDeviceActivity.this);
        phHueSDK.connect(accessPoint);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void setupBridges(List<HueBridge> smartDeviceList) {
        if (!smartDeviceList.isEmpty())
        {
            for (HueBridge bridge : smartDeviceList)
            {
                mhueBridge = bridge;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                doBridgeSearch();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(getApplicationContext(), "Canceled bridge search", Toast.LENGTH_SHORT).show();
                                PHWizardAlertDialog.getInstance().closeProgressDialog();
                                finish();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("An existing bridge already exists.\nDo you wish to search for new bridges anyway?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
                break;
            }
            searchOrConnect();
        }
        else{
            doBridgeSearch();
        }
    }




    //--- NEST SETUP ---
    @Override
    public void searchForNest(List<NestHub> nestHubs) {
        if (nestHubs == null){
            Intent i = new Intent(AddSmartDeviceActivity.this, AddNestSmartDevice.class);
            startActivityForResult(i, 2);
        }
        else{
            NestHub tester = new NestHub();
            for (NestHub n : nestHubs){
                tester = n;
            }
            NestUtilities.InitializeNestForCurrentContext(mActivity,tester.getBearerToken(),tester);
            while (!NestUtilities.ready){
            }
            NestUtilities.nestAPI.thermostats.setTargetTemperatureC("JhubbFxXG2y1HH9kfuRI49RhWBXZ6L5T",50);

        }
    }

    public void addNest(String client_id, String secret_id){
        CLIENT_ID = client_id;
        CLIENT_SECRET = secret_id;
        NestAPI.setAndroidContext(mActivity);
        nestAPI = NestAPI.getInstance();
        nestAPI.setConfig(client_id,secret_id,"http://localhost:8080/auth/nest/callback");
        nestAPI.launchAuthFlow(mActivity, AUTH_TOKEN_REQUEST_CODE);
    }

    private void getAllConnectedToNest(NestAPI n, NestToken token)
    {
        n.addGlobalListener(new NestListener.GlobalListener() {
            @Override
            public void onUpdate(@NonNull GlobalUpdate update) {
                ArrayList<Thermostat> thermostatArrayList = update.getThermostats();
                n.removeAllListeners();

                //Add Nest to db
                NestHub nestHub = new NestHub();
                nestHub.setBearerToken(token.toString());
                nestHub.setClientId(n.getConfig().getClientID());
                nestHub.setSecretId(n.getConfig().getClientSecret());
                nestHub.setExpires(token.getExpiresIn());

                //Add Thermo to db
                List<NestThermostat> nestThermostatList = new ArrayList<>();
                for (Thermostat thermostat : thermostatArrayList) {
                    NestThermostat nestThermostat = new NestThermostat();
                    nestThermostat.setName(thermostat.getName());
                    nestThermostat.setDeviceId(thermostat.getDeviceId());
                    nestThermostatList.add(nestThermostat);
                    deviceIdtester = thermostat.getDeviceId();
                }
                mSmartDeviceViewModel.insertNest(nestHub,nestThermostatList);
            }
        });



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == AUTH_TOKEN_REQUEST_CODE && resultCode == RESULT_OK) {
            NestToken token = NestAPI.getAccessTokenFromIntent(intent);
            nestAPI.authWithToken(token, new NestListener.AuthListener() {
                @Override
                public void onAuthSuccess() {
                    getAllConnectedToNest(nestAPI,token);
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

        //Return from entering the client and secret
         else if(requestCode == 2 && resultCode == RESULT_OK){
            String id = intent.getStringExtra("id");
            String secret = intent.getStringExtra("secret");
            addNest(id, secret);
        }
    }


}
