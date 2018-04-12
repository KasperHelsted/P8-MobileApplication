package p8project.sw801.ui.event.notificationorsmartdevice;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class NotificationOrSmartdeviceViewModel extends BaseViewModel<NotificationOrSmartdeviceNavigator> {
    public NotificationOrSmartdeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onNavBackClick() {
        getNavigator().goBack();
    }

    public void openAddSmartDeviceList() {
        getNavigator().openAddSmartDeviceList();
    }
}
