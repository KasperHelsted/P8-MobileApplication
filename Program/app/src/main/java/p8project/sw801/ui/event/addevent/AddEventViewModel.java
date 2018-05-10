package p8project.sw801.ui.event.addevent;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.ui.custom.DayPicker;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventViewModel extends BaseViewModel<AddEventNavigator> {

    public DayPicker dayPicker = new DayPicker();

    /**
     * Constructor for the class.
     *
     * @param dataManager       The active instance of the datamanager.
     * @param schedulerProvider The active instance of the schedulerProvider.
     */
    public AddEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Method used to fetch a list of predefined locations from the database.
     */
    public void displayPredefinedLocations() {
        List<PredefinedLocation> predefinedLocationList = new ArrayList<>();
        getCompositeDisposable().add(
                getDataManager().getAllPredefinedLocations().subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response == null) {
                                throw new NullPointerException("DENNE ER NULL");
                            }
                            predefinedLocationList.addAll(response);
                            getNavigator().displayPredefinedLocations(predefinedLocationList);
                        })
        );

    }

    /**
     * Method called when a user clicks on the functionality to add a new trigger.
     * Uses the navigator to call a method in the AddEvent activity class.
     */
    public void showNotificationOrSmartdevice() {
        getNavigator().showNotificationOrSmartdevice();
    }

    /**
     * Method called when a user clicks on a time picker.
     * Uses the navigator to call a method in the AddEvent activity class.
     *
     * @param i Variables used to distinguish between the two different timepickers.
     */
    public void showTimePickerDialog(int i) {
        getNavigator().showTimePickerDialog(i);
    }

    /**
     * Method called when a user click on the create event button.
     * Uses the navigator to call a method in the AddEvent activity class.
     */
    public void submitEventClick() {
        getNavigator().submitEventClick();

    }

    /**
     * Method called from the activity to save all required data for an event in the database.
     * This method fetches the id of the event object and calls methods to save the when and list of triggers using the event id.
     *
     * @param when     The when object describing the time and location of the notification to trigger.
     * @param trigList List of triggers for the smart devices.
     * @param pred     The predefined location chosen by the user.
     */
    public void submitEventToDatabase(When when, List<Trigger> trigList, PredefinedLocation pred) {
        if (pred != null) {
            when.setCoordinateId(pred.getCoordinateId());
        }
        Event eventId = new Event();
        //Getting Last event for the ID, used to save Triggers and When associated with the event
        getCompositeDisposable().add(
                getDataManager().getLastEvent().subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            eventId.setId(response.getId());
                            saveTriggers(trigList, eventId);
                            saveWhen(when, eventId);
                        })
        );
    }

    /**
     * Method used to save an event object in the database.
     *
     * @param event The event object that is saved in the database.
     */
    public void saveEvent(Event event) {
        // Save Event to DB
        getCompositeDisposable().add(
                getDataManager().insertEvent(event).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getNavigator().notitfyActivity();
                        })
        );
    }

    /**
     * Method used to insert a coordinate object into the database.
     * Called as part of inserting an event and the associated data to the database.
     *
     * @param when       The when object describing the time and location of the notification to trigger.
     * @param trigList   List of triggers for the smart devices.
     * @param coordinate The coordinate for a location chosen by the user.
     */
    public void saveCoordinate(When when, List<Trigger> trigList, Coordinate coordinate) {
        getCompositeDisposable().add(
                getDataManager().insertCoordinate(coordinate).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getCoordinateId(when, trigList);
                        })
        );
    }

    /**
     * Fetches the save coordinate object from the database and attaches this to the when object used in a later function.
     *
     * @param when     The when object describing the time and location of the notification to trigger.
     * @param trigList List of triggers for the smart devices.
     */
    public void getCoordinateId(When when, List<Trigger> trigList) {
        getCompositeDisposable().add(
                getDataManager().getLast().subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            when.setCoordinateId(response.getId());
                            submitEventToDatabase(when, trigList, null);
                        })
        );
    }

    /**
     * Method used to save the list of triggers for an event.
     *
     * @param tList   List of triggers for the smart devices.
     * @param eventId The id of the event associated with the triggers.
     */
    public void saveTriggers(List<Trigger> tList, Event eventId) {
        List<Trigger> tListWithId = new ArrayList<>();
        for (Trigger trigger : tList) {
            trigger.setEventId(eventId.getId());
            tListWithId.add(trigger);
        }
        getCompositeDisposable().add(
                getDataManager().insertAllTriggers(tListWithId).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getNavigator().notitfyActivity();
                        })
        );
    }

    /**
     * Method used to save the When object for an event.
     *
     * @param when    The when object describing the time and location of the notification to trigger.
     * @param eventId The id of the event.
     */
    public void saveWhen(When when, Event eventId) {
        when.setEventId(eventId.getId());
        getCompositeDisposable().add(
                getDataManager().insertWhen(when).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            geteventwithdata(eventId);
                        })
        );
    }

    /**
     * Method used to fetch an EventWithData object that contains all relevant data to an object such as the when object and list of tiggers.
     * This object is used to create the relevant alarms and notification for the event.
     *
     * @param e The event object inserted previous.
     */
    public void geteventwithdata(Event e) {
        getCompositeDisposable().add(
                getDataManager().getEventWithData(e.getId()).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(event -> {
                    getNavigator().createNotifications(event);
                })
        );
    }

}
