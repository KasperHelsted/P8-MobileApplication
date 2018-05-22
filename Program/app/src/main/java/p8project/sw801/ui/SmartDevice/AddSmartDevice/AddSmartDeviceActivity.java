package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.nestlabs.sdk.GlobalUpdate;
import com.nestlabs.sdk.NestAPI;
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
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.custom.PHPushlinkActivity;
import p8project.sw801.ui.custom.PHWizardAlertDialog;
import p8project.sw801.utils.CommonUtils;
import p8project.sw801.utils.HueUtilities;
import p8project.sw801.utils.KeyBoardUtil;


public class AddSmartDeviceActivity extends BaseActivity<ActivityAddSmartDeviceBinding, AddSmartDeviceViewModel> implements AdapterView.OnItemClickListener, AddSmartDeviceNavigator, HasSupportFragmentInjector {

    //Adapter for listing HueAccessPoints
    private AccessPointListAdapter adapter;
    private boolean lastSearchWasIPScan = false;
    //Class scope SDK access
    private PHHueSDK phHueSDK = null;
    //Mvvm injects
    @Inject
    AddSmartDeviceViewModel mSmartDeviceViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    //Mvvm view binding
    private ActivityAddSmartDeviceBinding mActivityAddSmartDeviceBinding;
    //Initializting a bridge object for null checking
    private HueBridge mhueBridge = null;
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
    private TextInputLayout TextInputSecret;
    private TextInputLayout TextInputClientId;
    public static final int AUTH_TOKEN_REQUEST_CODE = 27015;


    /**
     * MVVM method for bindings
     *
     * @return The viewmodel bindings
     */
    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * Specififies the view corresponding to the class
     *
     * @return the ID of the layout
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_add_smart_device;
    }

    /**
     * Gets the viewmodel and bindings
     *
     * @return A viewmodel instance of the class
     */
    @Override
    public AddSmartDeviceViewModel getViewModel() {
        return mSmartDeviceViewModel;
    }

    /**
     * '
     * The first method called when the Acvitvity is created
     * Used to initialize the SDK's and setup MVVM
     *
     * @param savedInstanceState the state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KeyBoardUtil.setHideKeyboardOnTouch(this, findViewById(R.id.addsmartdevicepage));

        //MVVM Bindings
        mActivityAddSmartDeviceBinding = getViewDataBinding();
        mSmartDeviceViewModel.setNavigator(this);
        //Setup the hue SDK
        if (phHueSDK == null) {
            phHueSDK = PHHueSDK.create();
            phHueSDK.setAppName(getResources().getString(R.string.app_name));
            phHueSDK.setDeviceName(android.os.Build.MODEL);
            phHueSDK.getNotificationManager().registerSDKListener(phsdkListener);

        }


        //ViewBindings
        brigdeListview = mActivityAddSmartDeviceBinding.bridgeList;
        searchBridge = mActivityAddSmartDeviceBinding.findNewBridge;
        searchNest = mActivityAddSmartDeviceBinding.buttonNestconfirm;
        TextInputClientId = mActivityAddSmartDeviceBinding.textInputLayout2;
        TextInputSecret = mActivityAddSmartDeviceBinding.textInputLayout3;

        mActivity = this;

        //Register a SDK listener for actions related to connection
        //Initial setup of listview
        adapter = new AccessPointListAdapter(getApplicationContext(), phHueSDK.getAccessPointsFound());
        brigdeListview.setOnItemClickListener(this);
        brigdeListview.setAdapter(adapter);
    }

    /**
     * Hue SDK listener
     * used to establish connections and handle errors coming from the bridge
     */
    private PHSDKListener phsdkListener = new PHSDKListener() {

        /**
         * Event raised by the SDK listener when accesspoints has been found
         * @param accessPoint The list of found accesspoints
         */
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

        /**
         * Bridge automatically does internal updates of information, everytime it does, this method is called.
         * @param arg0 Bridge arguments - unused.
         * @param bridge Bridge instance
         */
        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge bridge) {
            Log.w(HueUtilities.TAG, "On CacheUpdated");
        }

        /**
         * When the bridge establishes connection, this method is called.
         * Used to input the bridge and smartdevices into the database
         * @param phBridge Bridge instance
         * @param username Username who connected
         */
        @Override
        public void onBridgeConnected(PHBridge phBridge, String username) {
            SmartDevice sd = new SmartDevice();
            sd.setDeviceName(phBridge.getResourceCache().getBridgeConfiguration().getName());
            sd.setInternalIdentifier(1);
            mhueBridge = new HueBridge();
            phHueSDK.setSelectedBridge(phBridge);
            phHueSDK.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL);
            phHueSDK.getLastHeartbeat().put(phBridge.getResourceCache().getBridgeConfiguration().getIpAddress(), System.currentTimeMillis());
            mhueBridge.setUsername(phBridge.getResourceCache().getBridgeConfiguration().getUsername());
            mhueBridge.setDeviceIP(phBridge.getResourceCache().getBridgeConfiguration().getIpAddress());
            List<PHLight> allLights = phBridge.getResourceCache().getAllLights();
            List<HueLightbulbWhite> uniqueID = new ArrayList<>();
            for (PHLight pl : allLights) {
                HueLightbulbWhite hbw = new HueLightbulbWhite();
                hbw.setDeviceName(pl.getName());
                hbw.setDeviceId(pl.getUniqueId());
                uniqueID.add(hbw);
            }
            //Transfer all data to Viewmodel
            mSmartDeviceViewModel.smartDeviceinsertHandler(sd, mhueBridge, uniqueID);
            ChangeToSmartDevice();
        }

        /**
         * Called when the bridge cannot connect automatically
         * Opens a dialog telling the user to press the Pushlink button on their bridge
         * @param accessPoint The accesspoint which is attempted to connect to
         */
        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            Log.w(HueUtilities.TAG, "Authentication Required.");
            phHueSDK.startPushlinkAuthentication(accessPoint);
            startActivity(new Intent(AddSmartDeviceActivity.this, PHPushlinkActivity.class));
        }

        /**
         * If the connection to the bridge is resumed, this method is called.
         * If the activity is finishing, it does nothing
         * Else, since the connection is resumed
         * it checks the list of getDisconnected access points and removes the accesspoint it reconnected to
         * @param bridge Current bridge instance
         */
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

        /**
         * When the bridge connection is lost we add the accesspoint information to disconnected accesspoints
         * @param accessPoint the accesspoint, which we lost connection to
         */
        @Override
        public void onConnectionLost(PHAccessPoint accessPoint) {
            Log.v(HueUtilities.TAG, "onConnectionLost : " + accessPoint.getIpAddress());
            if (!phHueSDK.getDisconnectedAccessPoint().contains(accessPoint)) {
                phHueSDK.getDisconnectedAccessPoint().add(accessPoint);
            }
        }

        /**
         * Handles all the errors the bridge can throw
         * @param code Errorcode, used to differentiate between errors
         * @param message The errormessage thrown by the bridge
         */
        @Override
        public void onError(int code, final String message) {
            Log.e(HueUtilities.TAG, "on Error Called : " + code + ":" + message);
            if (code == 52) {
                Toast.makeText(getApplicationContext(), "Internal bridge error, please try again", Toast.LENGTH_SHORT).show();
                PHWizardAlertDialog.getInstance().closeProgressDialog();
            }
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

        /**
         * Prints out any errors found to the internal log
         * @param parsingErrorsList a list of parsing errors
         */
        @Override
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError : parsingErrorsList) {
                Log.e(HueUtilities.TAG, "ParsingError : " + parsingError.getMessage());
            }
        }
    };

    /**
     * Method that either connects to the existing bridge, using its accesspoint or searches for a new bridge accesspoint
     */
    public void searchOrConnect() {
        if (mhueBridge != null && !CommonUtils.isNullOrEmpty(mhueBridge.getDeviceIP()) && !CommonUtils.isNullOrEmpty(mhueBridge.getUsername())) {
            mhueBridge = new HueBridge();
            PHAccessPoint lastAccessPoint = new PHAccessPoint();
            lastAccessPoint.setIpAddress(mhueBridge.getDeviceIP());
            lastAccessPoint.setUsername(mhueBridge.getUsername());
            if (!phHueSDK.isAccessPointConnected(lastAccessPoint)) {
                PHWizardAlertDialog.getInstance().showProgressDialog(R.string.connecting, AddSmartDeviceActivity.this);
                phHueSDK.connect(lastAccessPoint);
            }
        } else {
            doBridgeSearch();
        }
    }

    /**
     * Searching for bridge accesspoints
     */
    public void doBridgeSearch() {
        PHWizardAlertDialog.getInstance().showProgressDialog(R.string.search_progress, AddSmartDeviceActivity.this);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(phHueSDK.SEARCH_BRIDGE);
        // Start the UPNP Searching of local bridges.
        sm.search(true, true);
    }

    /**
     * Closes all overlays
     * Unregisters the hue SDK listener
     * Finishes the activity - returning the user to the Smartdevice page
     */
    @Override
    public void ChangeToSmartDevice() {
        PHWizardAlertDialog.getInstance().closeProgressDialog();
        phHueSDK.getNotificationManager().cancelSearchNotification();
        phHueSDK.getNotificationManager().unregisterSDKListener(phsdkListener);
        finish();
    }

    /**
     * Navigator method to call the viewmodel from the UI
     */
    @Override
    public void searchForBridge() {
        mSmartDeviceViewModel.getBridges();
    }

    /**
     * Handles the clickEvent from the listview of Bridge Accesspoints
     *
     * @param parent   adapter information - Information of the list
     * @param view     An instance of the view
     * @param position The position of where the user clicked
     * @param id       The id of the listview
     */
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

    /**
     * Dagger injection method
     *
     * @return Androidinjector to handle fragments
     */
    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    /**
     * Method called after the user presses the Search for bridge button
     * Warns the user if a bridge already exists
     * Either searches for a new bridge or connects to an existing one
     *
     * @param hueBridgeList List of Huebridges from the database
     */
    @Override
    public void setupBridges(List<HueBridge> hueBridgeList) {
        if (!hueBridgeList.isEmpty()) {
            for (HueBridge bridge : hueBridgeList) {
                mhueBridge = bridge;
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
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
        } else {
            doBridgeSearch();
        }
    }


    //--- NEST SETUP ---

    /**
     * Method that is called after the list of already existing nests has been received from the database
     * Attempts to establish connection the the Nest OR warns a user if a nest already exists
     *
     * @param nestHubs The list of existing nesthubs
     */
    @Override
    public void searchForNest(List<NestHub> nestHubs) {
        if (nestHubs == null) {
            String id = TextInputClientId.getEditText().getText().toString();
            String secret = TextInputSecret.getEditText().getText().toString();
            if (CommonUtils.isNullOrEmpty(id) || CommonUtils.isNullOrEmpty(secret)) {
                Toast.makeText(getApplicationContext(), "Please input ClientID and Secret ID to add a Nest", Toast.LENGTH_SHORT).show();
            } else {
                addNest(id, secret);
            }
        } else {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            String id = TextInputClientId.getEditText().getText().toString();
                            String secret = TextInputSecret.getEditText().getText().toString();
                            addNest(id, secret);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            Toast.makeText(getApplicationContext(), "Canceled Nest search", Toast.LENGTH_SHORT).show();
                            PHWizardAlertDialog.getInstance().closeProgressDialog();
                            finish();
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("An existing Nest already exists.\nDo you wish to search anyway?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    /**
     * Authenticates the Nest with our App
     *
     * @param client_id Input client ID from the user
     * @param secret_id Input Secret ID from the user
     */
    public void addNest(String client_id, String secret_id) {
        CLIENT_ID = client_id;
        CLIENT_SECRET = secret_id;
        NestAPI.setAndroidContext(mActivity);
        nestAPI = NestAPI.getInstance();
        nestAPI.setConfig(client_id, secret_id, "http://localhost:8080/auth/nest/callback");
        nestAPI.launchAuthFlow(mActivity, AUTH_TOKEN_REQUEST_CODE);
    }

    /**
     * Method that establishes a listener waiting for the Nest to update its information
     * To which it adds the Nest to the database
     *
     * @param localNestApi Received instance of the nest api
     * @param token        The nest token from the successful auth
     */
    private void getAllConnectedToNest(NestAPI localNestApi, NestToken token) {
        localNestApi.addGlobalListener(new NestListener.GlobalListener() {
            @Override
            public void onUpdate(@NonNull GlobalUpdate update) {
                ArrayList<Thermostat> thermostatArrayList = update.getThermostats();
                localNestApi.removeAllListeners();


                //Add Nest to db
                NestHub nestHub = new NestHub();
                nestHub.setBearerToken(token.getToken());
                nestHub.setClientId(localNestApi.getConfig().getClientID());
                nestHub.setSecretId(localNestApi.getConfig().getClientSecret());
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
                mSmartDeviceViewModel.insertNest(nestHub, nestThermostatList);
            }
        });


    }


    /**
     * Method that handles Activity Results
     * Handles the user finishing authenfication
     *
     * @param requestCode The requestCode from the activity
     * @param resultCode  Status of the result
     * @param intent      The runtime binding between the activity and its caller
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == AUTH_TOKEN_REQUEST_CODE && resultCode == RESULT_OK) {
            NestToken token = NestAPI.getAccessTokenFromIntent(intent);
            nestAPI.authWithToken(token, new NestListener.AuthListener() {
                @Override
                public void onAuthSuccess() {
                    getAllConnectedToNest(nestAPI, token);
                }

                @Override
                public void onAuthFailure(NestException e) {
                    Toast.makeText(getApplicationContext(), "Nest Auth failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthRevoked() {
                    // Your previously authenticated connection has become unauthenticated.
                    // Recommendation: Relaunch an auth flow with nest.launchAuthFlow().
                }
            });
        }
    }
}
