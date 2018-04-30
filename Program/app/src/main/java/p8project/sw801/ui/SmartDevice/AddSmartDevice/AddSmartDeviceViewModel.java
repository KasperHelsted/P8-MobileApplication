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
    public void insertNest(NestHub nestHub, List<NestThermostat> nestThermostatList){

        //TODO CREATE SMART DEVICE, INSERT, GET, SET ID PÅ NESTHUB OG THERMOS, INSERT NEST, GET, SET ID på THERMOS
    }

    public void NestExists(){
        List<NestHub> nestHubs = null;
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
