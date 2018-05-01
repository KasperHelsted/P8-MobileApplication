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

    public void searchBridge(){
        getNavigator().searchForBridge();
    }
    public void searchNest(List<NestHub> nestHubs){getNavigator().searchForNest(nestHubs);}

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
                                getNavigator().changeToSmartDevice();
                            })
            );
        }
    }


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
