package p8project.sw801.ui.event.addevent;

public interface AddEventNavigator {
    void handleError(Throwable throwable);

    void openCreateMapActivity();

    void showNotificationOrSmartdevice();

    void showTimePickerDialog(int i);

    void submitEventClick();

}
