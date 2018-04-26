package p8project.sw801.ui.event.addevent;

import java.util.List;

import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;

public interface AddEventNavigator {
    void handleError(Throwable throwable);

    void openCreateMapActivity();

    void showNotificationOrSmartdevice();

    void showTimePickerDialog(int i);

    void submitEventClick();

    /*void testerfunction(EventWithData e);

    void submitEventToDatabase(Event event, When when, List<Trigger> trigList);*/

}
