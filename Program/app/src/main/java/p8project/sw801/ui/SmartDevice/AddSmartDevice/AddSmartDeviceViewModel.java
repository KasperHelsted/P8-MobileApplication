package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddSmartDeviceViewModel extends BaseViewModel<AddSmartDeviceNavigator> {

    public AddSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void search(){
        getNavigator().searchForBridge();
    }

    public void getBridges()
    {
        List<SmartDevice> smartDeviceList = new ArrayList<SmartDevice>();
        getCompositeDisposable().add(
                getDataManager().getAllSmartDevices(

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if(!response.isEmpty()){
                                smartDeviceList.addAll(response);
                            }
                            getNavigator().setupBridges(smartDeviceList);
                        })
        );
    }
    public void insertBridge(HueBridge bridge){
        getCompositeDisposable().add(
                getDataManager().insertSmartDevice(
                        bridge

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                        })
        );
    }
}
