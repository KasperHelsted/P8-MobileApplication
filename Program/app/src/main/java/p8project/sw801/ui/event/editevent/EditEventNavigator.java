package p8project.sw801.ui.event.editevent;

public interface EditEventNavigator {
    void addEventTrigger();

    void chooseLocation();

    void cancelEditEvent();

    String reverseGeocode(double lat, double lng);

    void makeToast(String msg);
}
