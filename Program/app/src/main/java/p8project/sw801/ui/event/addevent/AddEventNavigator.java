package p8project.sw801.ui.event.addevent;

import java.util.List;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.PredefinedLocation;

public interface AddEventNavigator {

    void openCreateMapActivity();

    void showNotificationOrSmartdevice();

    void showTimePickerDialog(int i);

    void notitfyActivity();

    void submitEventClick();

    void displayPredefinedLocations(List<PredefinedLocation> predefinedLocationList);

    void updateActiveLocation(PredefinedLocation loc);


    void createNotifications(EventWithData eventWithData);


    /*void testerfunction(EventWithData e);

    void submitEventToDatabase(Event event, When when, List<Trigger> trigList);*/

}
