package p8project.sw801.ui.event.notificationorsmartdevice;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class NotificationOrSmartdeviceViewModel extends BaseViewModel<NotificationOrSmartdeviceNavigator> {

    /**
     * Constructor for the class.
     *
     * @param dataManager       The active instance of the datamanager.
     * @param schedulerProvider The active instance of the schedulerProvider.
     */
    public NotificationOrSmartdeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Method called when the user clicks on the trigger smart device button.
     * Uses the navigator to call a method from the activity.
     */
    public void openAddSmartDeviceList() {
        getNavigator().openAddSmartDeviceList();
    }

    /**
     * Method called when the user clicks on the add notification button.
     * Uses the navigator to call a method from the activity.
     */
    public void addNotificationMethod() {
        getNavigator().addNotification();
    }
}
