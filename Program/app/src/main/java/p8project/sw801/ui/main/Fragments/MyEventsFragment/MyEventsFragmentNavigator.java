package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import p8project.sw801.data.model.db.Event;

public interface MyEventsFragmentNavigator {

    void addNewEvent();

    void updatelist();

    void deleteEvent(Event event);
}
