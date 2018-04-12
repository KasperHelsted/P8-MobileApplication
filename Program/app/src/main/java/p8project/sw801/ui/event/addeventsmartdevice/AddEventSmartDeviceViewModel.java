package p8project.sw801.ui.event.addeventsmartdevice;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.ui.event.createeventmap.CreateEventMapNavigator;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventSmartDeviceViewModel  extends BaseViewModel<AddEventSmartDeviceNavigator> {
    public AddEventSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
