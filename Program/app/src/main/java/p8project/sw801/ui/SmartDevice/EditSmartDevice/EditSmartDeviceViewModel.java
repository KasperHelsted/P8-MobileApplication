package p8project.sw801.ui.SmartDevice.EditSmartDevice;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditSmartDeviceViewModel extends BaseViewModel<EditSmartDeviceNavigator> {
    public EditSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void confirmPressed(){
        getNavigator().confirmPress();
    }

    public void saveToDb(SmartDevice smartDevice){

        getCompositeDisposable().add(
                getDataManager().updateSmartDevice(smartDevice).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    if (response) {
                        getNavigator().close();
                    }
                })
        );


    }
}
