package p8project.sw801.ui.event.choosenotificationorsmartdevice;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class ChooseNotificationOrSmartDeviceViewModel extends BaseViewModel<ChooseNotificationOrSmartDeviceNavigator> {
    public ChooseNotificationOrSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
