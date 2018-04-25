package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import p8project.sw801.BR;
import p8project.sw801.R;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.databinding.ActivityAddSmartDeviceBinding;
import p8project.sw801.ui.SmartDevice.AccessPointListAdapter;
import p8project.sw801.ui.base.BaseActivity;
import p8project.sw801.ui.custom.PHPushlinkActivity;
import p8project.sw801.ui.custom.PHWizardAlertDialog;
import p8project.sw801.utils.CommonUtils;
import p8project.sw801.utils.HueUtilities;

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
    private Button searchButton;
    private ListView brigdeListview;



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
        searchButton = mActivityAddSmartDeviceBinding.findNewBridge;

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
                AddSmartDeviceActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PHWizardAlertDialog.showErrorDialog(AddSmartDeviceActivity.this, message, R.string.button_ok);
                    }
                });

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
                Toast.makeText(getApplicationContext(), "An existing bridge already exists", Toast.LENGTH_SHORT).show();
                break;
            }
            searchOrConnect();
        }
        else{
            doBridgeSearch();
        }
    }
}
