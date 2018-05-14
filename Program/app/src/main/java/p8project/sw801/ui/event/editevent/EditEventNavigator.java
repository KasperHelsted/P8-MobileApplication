package p8project.sw801.ui.event.editevent;

import p8project.sw801.data.model.db.Event;

public interface EditEventNavigator {
    void addEventTrigger();

    void chooseLocation();

    void cancelEditEvent();

    String reverseGeocode(double lat, double lng);

    void makeToast(String msg);

    void deleteEvent(Event event);
}
