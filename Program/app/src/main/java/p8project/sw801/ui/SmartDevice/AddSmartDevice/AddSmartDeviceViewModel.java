package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddSmartDeviceViewModel extends BaseViewModel<AddSmartDeviceNavigator> {

    public AddSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Navigator method from the SearchBridgeButton
     */
    public void searchBridge(){
        getNavigator().searchForBridge();
    }

    /**
     * Navigator method from the searchNest button
     * @param nestHubs List of existing nest hubs
     */
    public void searchNest(List<NestHub> nestHubs){getNavigator().searchForNest(nestHubs);}

    /**
     * Method to insert the Nest into the database
     * Public Viewmodel method that starts the process
     * @param nestHub Nesthub with data from the Activity
     * @param nestThermostatList List of Thermostats to add  to the database
     */
    public void insertNest(NestHub nestHub, List<NestThermostat> nestThermostatList)
    {
        SmartDevice sd = new SmartDevice();
        sd.setInternalIdentifier(2);
        sd.setDeviceName("Nest");
        getCompositeDisposable().add(
                getDataManager().insertSmartDevice(
                        sd
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getSmartdeviceIDfromNest(nestHub,nestThermostatList);
                        })
        );
    }

    /**
     * Internal method to continue insertNest dataflow
     * Receives the ID from the inserted Smartdevice
     * @param nestHub Nesthub with data from the Activity
     * @param nestThermostatList List of Thermostats to add  to the database
     */
    private void getSmartdeviceIDfromNest(NestHub nestHub, List<NestThermostat> nestThermostatList){
        getCompositeDisposable().add(
                getDataManager().getLastSmartDevice(

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null){
                                insertNesttodb(nestHub, nestThermostatList,response.getId());
                            }
                        })
        );
    }

    /**
     * Method that inserts the Nest into the database
     * @param nestHub Nesthub with data from the Activity
     * @param nestThermostatList List of Thermostats to add  to the database
     * @param smartDeviceId The received SmartDevice from earlier in the dataflow
     */
    public void insertNesttodb(NestHub nestHub, List<NestThermostat> nestThermostatList, int smartDeviceId){
        nestHub.setSmartDeviceId(smartDeviceId);
        getCompositeDisposable().add(
                getDataManager().insertNestHub(
                        nestHub
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getNestId(smartDeviceId,nestThermostatList);
                        })
        );
    }

    /**
     * Receives the Nest ID
     * @param smartDeviceId smartdevice ID to pass on
     * @param nestThermostatList List of thermostats to add to the Db
     */
    private void getNestId(int smartDeviceId,List<NestThermostat> nestThermostatList){
        getCompositeDisposable().add(
                getDataManager().getLastInsertedNestHub(

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null){
                                insertThermostats(nestThermostatList,smartDeviceId,response.getId());
                            }
                        })
        );
    }

    /**
     * Adds the thermostats to the nest and inserts them into the database
     * @param nestThermostatList List of thermostats to insert into db
     * @param smartdeviceId Nest smartdevice id
     * @param nestId Nest's id
     */
    private void insertThermostats(List<NestThermostat> nestThermostatList, int smartdeviceId, int nestId){
        for(NestThermostat nt : nestThermostatList) {
            nt.setSmartDeviceId(smartdeviceId);
            nt.setNestHubId(nestId);

            getCompositeDisposable().add(
                    getDataManager().insertNestThermostat(
                            nt
                    ).subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(response -> {
                                getNavigator().ChangeToSmartDevice();
                            })
            );
        }
    }


    /**
     * Receives all existing nests or NULL to the activity
     */
    public void NestExists(){
        List<NestHub> nestHubs  = new ArrayList<>();
        getCompositeDisposable().add(
                getDataManager().getAllNestHubs(

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null && !response.isEmpty()){
                                nestHubs.addAll(response);
                                searchNest(nestHubs);
                            }
                            else{
                                searchNest(null);
                            }
                        })
        );
    }

    /**
     * Handles initial dataflow of inserting a Hue bridge
     * @param smartDevice Smartdevice with data to insert
     * @param hueBridge Bridge with data
     * @param lightbulbWhiteList List of LightBulbs
     */
    public void smartDeviceinsertHandler(SmartDevice smartDevice, HueBridge hueBridge, List<HueLightbulbWhite> lightbulbWhiteList){
        getCompositeDisposable().add(
                getDataManager().insertSmartDevice(
                        smartDevice
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getSmartdeviceID(hueBridge,lightbulbWhiteList);
                        })
        );
    }

    /**
     * Receives smart devices id
     * @param hueBridge huebridge data
     * @param lightbulbWhiteList lightbulb data
     */
    private void getSmartdeviceID(HueBridge hueBridge, List<HueLightbulbWhite> lightbulbWhiteList){
        getCompositeDisposable().add(
                getDataManager().getLastSmartDevice(

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null){
                                insertBridge(hueBridge,response.getId(), lightbulbWhiteList);
                            }
                        })
        );
    }

    /**
     * Receives all hue bridges from the database
     */
    public void getBridges()
    {
        List<HueBridge> smartDeviceList = new ArrayList<>();
        getCompositeDisposable().add(
                getDataManager().getAllHueBridges(

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if(response != null && !response.isEmpty()){
                                smartDeviceList.addAll(response);
                            }
                            getNavigator().setupBridges(smartDeviceList);
                        })
        );
    }

    /**
     * Inserts the hue bridge into the database
     * @param bridge bridge instance
     * @param smartDeviceId smartdevice ID of bridge
     * @param lightbulbWhiteList list of lightbulbs
     */
    public void insertBridge(HueBridge bridge, int smartDeviceId, List<HueLightbulbWhite> lightbulbWhiteList){
        bridge.setSmartDeviceId(smartDeviceId);
        getCompositeDisposable().add(
                getDataManager().insertHueBridge(
                        bridge
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getBridgeId(smartDeviceId,lightbulbWhiteList);
                        })
        );
    }

    /**
     * Receives the bridge Id
     * Continues the dataflow
     * @param smartDeviceId smartdevice id of the bridge
     * @param lightbulbWhiteList list of lightbulbs
     */
    private void getBridgeId(int smartDeviceId,List<HueLightbulbWhite> lightbulbWhiteList){
        getCompositeDisposable().add(
                getDataManager().getLastInsertedHueBridge(

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null){
                               insertLightbulbs(lightbulbWhiteList,smartDeviceId,response.getId());
                            }
                        })
        );
    }

    /**
     * Inserts the lightbulbs to the associated bridge
     * @param lightbulbWhiteList list of lightbulbs
     * @param smartdeviceId smartdeviceid of the bridge
     * @param bridgeId id of the bridge
     */
    private void insertLightbulbs(List<HueLightbulbWhite> lightbulbWhiteList, int smartdeviceId, int bridgeId){
        for(HueLightbulbWhite hbw : lightbulbWhiteList) {
            hbw.setHueBridgeId(bridgeId);
            hbw.setSmartDeviceId(smartdeviceId);

            getCompositeDisposable().add(
                    getDataManager().insertWhiteHueLightbulb(
                            hbw
                    ).subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(response -> {

                            })
            );
        }
    }
}
