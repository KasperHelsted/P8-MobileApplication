package p8project.sw801.ui.event.addevent;

import p8project.sw801.data.local.RelationEntity.EventWithData;

public interface AddEventNavigator {
    void handleError(Throwable throwable);

    void openCreateMapActivity();

    void showNotificationOrSmartdevice();

    void showTimePickerDialog(int i);

    void submitEventClick();

    void testerfunction(EventWithData e);

}
