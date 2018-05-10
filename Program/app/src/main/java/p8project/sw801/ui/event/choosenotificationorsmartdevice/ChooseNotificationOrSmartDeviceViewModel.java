package p8project.sw801.ui.event.choosenotificationorsmartdevice;

import android.databinding.ObservableField;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class ChooseNotificationOrSmartDeviceViewModel extends BaseViewModel<ChooseNotificationOrSmartDeviceNavigator> {
    public final ObservableField<String> notificationText = new ObservableField<>("");

    public ChooseNotificationOrSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void openAddSmartDeviceList() {
        getNavigator().openAddSmartDeviceList();
    }

    public void addNotification(){
        getNavigator().addNotification(notificationText.get());
    }
}
