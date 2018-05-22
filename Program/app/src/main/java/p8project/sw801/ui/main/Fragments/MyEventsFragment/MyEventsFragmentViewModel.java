package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;


public class MyEventsFragmentViewModel extends BaseViewModel<MyEventsFragmentNavigator> {
    private final ObservableArrayList<Event> eventArrayList = new ObservableArrayList<>();

    /**
     * Constructor for the class.
     *
     * @param dataManager       The active instance of the datamanager.
     * @param schedulerProvider The active instance of the schedulerProvider.
     */
    public MyEventsFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        fetchFromDatabase();
    }

    /**
     * Calls the addNewEvent function from MyEventFragment.
     */
    public void addNewEvent() {
        getNavigator().addNewEvent();
    }

    /**
     * Method used to fetch the list of events from the database and calling a method to update the view.
     */
    public void fetchFromDatabase() {
        List<Event> a = new ArrayList<>();
        ArrayList<Event> arrayList = null;

        //Fetch list from database
        getCompositeDisposable().add(
                getDataManager().getAllEvents().subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(list -> {
                            RenderList(list);
                        })
        );
    }

    /**
     * Method used to update the view by calling updatelist function from MyEventFragment.
     *
     * @param e The list of Events to render.
     */
    public void RenderList(List<Event> e) {
        eventArrayList.clear();
        eventArrayList.addAll(e);
        getNavigator().updatelist();
    }

    /**
     * Returns the observable list of events.
     *
     * @return The observable list of events.
     */
    public ObservableList<Event> getEventObservableList() {
        return eventArrayList;
    }

    /**
     * Updates an event in the database and calls a method to update the view.
     *
     * @param event     The updated Event object.
     * @param condition The boolean condition used to set if the event is active.
     */
    public void updateEvent(Event event, Boolean condition) {
        event.setActive(condition);

        getCompositeDisposable().add(
                getDataManager().updateEvent(event).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                })
        );
        fetchFromDatabase();
    }

    /**
     * Method used to delete the When objects associated with an event.
     *
     * @param id The event id.
     */
    private void deleteWhens(Integer id) {
        getCompositeDisposable().add(
                getDataManager().deleteWhenByEventId(
                        id
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe());
    }

    /**
     * Method used to delete the Trigger objects associated with an event.
     *
     * @param id The event id.
     */
    private void deleteTriggers(Integer id) {
        getCompositeDisposable().add(
                getDataManager().deleteTriggerByEventId(
                        id
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe());
    }

    /**
     * Method used to delete the event object.
     * Further this method are calling other methods to delete the When and Trigger objects associated with the event.
     *
     * @param event The event object
     */
    void deleteEventFromDatabase(Event event) {
        Integer id = event.getId();

        getCompositeDisposable().add(
                getDataManager().deleteEvent(
                        event
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(deleted -> {
                    this.deleteTriggers(id);
                    this.deleteWhens(id);

                    this.fetchFromDatabase();
                }));

    }
}