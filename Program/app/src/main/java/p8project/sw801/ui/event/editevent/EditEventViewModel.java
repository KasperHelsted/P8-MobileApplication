package p8project.sw801.ui.event.editevent;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.location.Address;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.ui.custom.DayPicker;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditEventViewModel extends BaseViewModel<EditEventNavigator> {
    public final ObservableList<Trigger> eventTriggersObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Trigger>> eventTriggersListLiveData;
    public ArrayList<String> locationConditions;
    public ArrayList<String> whenConditions;
    public ObservableField<String> eventName = new ObservableField<>();
    public ObservableInt locationCondition = new ObservableInt(0);
    public ObservableInt whenCondition = new ObservableInt(0);
    public ObservableField<String> locationString = new ObservableField<>();
    public ObservableInt startTime = new ObservableInt();
    public ObservableInt endTime = new ObservableInt();
    public DayPicker dayPicker = new DayPicker();
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private Event event;
    private When when;
    private List<Trigger> triggers = new ArrayList<>();

    /**
     * Constructor for viewmodel
     * Sets up the data
     *
     * @param dataManager       db instance
     * @param schedulerProvider schedulers
     */
    public EditEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        eventTriggersListLiveData = new MutableLiveData<>();
        whenConditions = new ArrayList<String>() {{
            add("No time condition chosen");
            add("Before this time");
            add("At this time");
            add("After this time");
            add("Between these times");
        }};

        locationConditions = new ArrayList<String>() {{
            add("No location chosen");
            add("At Location");
            add("Near Location");
            add("Leaving Location");
        }};
    }

    /**
     * Converts hours and minutes to an integer
     *
     * @param hour   hour value
     * @param minute minute value
     * @return time as int
     * @throws ParseException Exception if it all fails
     */
    private int hourAndMinuteToInt(Integer hour, Integer minute) throws ParseException {
        @SuppressLint("DefaultLocale")
        Date date = timeFormat.parse(
                String.format("%d:%02d", hour, minute)
        );

        return (int) date.getTime();
    }

    /**
     * Populates the view from the data
     *
     * @throws IOException    if reading the data went wrong
     * @throws ParseException if parsing the data went wrong
     */
    private void populate() throws IOException, ParseException {
        eventName.set(this.event.getName());

        dayPicker.setDays((ArrayList<Integer>) when.getListWeekDays());

        addTriggersToList(triggers);

        if (when.getStartHour() != null && when.getStartMinute() != null) {
            startTime.set(hourAndMinuteToInt(
                    when.getStartHour(),
                    when.getStartMinute()
            ));
        }

        if (when.getEndHour() != null && when.getEndMinute() != null) {
            endTime.set(hourAndMinuteToInt(
                    when.getEndHour(),
                    when.getEndMinute()
            ));
        }

        whenCondition.set(when.getTimeCondition());
        locationCondition.set(when.getLocationCondition());

        checkForPredefinedLocation(when.getCoordinateId());
    }

    private void checkForPredefinedLocation(Integer coordianteId) {
        if (coordianteId == null)
            return;
        getCompositeDisposable().add(
                getDataManager().getPredefinedLocationByCoordinateId(
                        coordianteId
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(predefinedLocation -> {
                    locationString.set(predefinedLocation.getName());
                }, throwable -> {
                    reverseCoordinate(coordianteId);
                })
        );
    }

    private void reverseCoordinate(Integer coordianteId) {
        getCompositeDisposable().add(
                getDataManager().getCoordinateById(
                        coordianteId
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(coordiante -> {
                    locationString.set(
                            getNavigator().reverseGeocode(
                                    coordiante.getLatitude(),
                                    coordiante.getLongitude()
                            )
                    );
                }, throwable -> {
                })
        );
    }

    /**
     * Loads the event from the stored data
     *
     * @param eventId id to load event from
     */
    void loadInitialEvent(int eventId) {
        if (eventId == -1)
            getNavigator().cancelEditEvent();

        getCompositeDisposable().add(
                getDataManager().getEventWithData(
                        eventId
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(
                        eventWithData -> {
                            this.event = eventWithData.event;
                            this.when = eventWithData.whens.get(0).when;

                            for (TriggerWithSmartDevice triggerWithSmartDevice : eventWithData.triggers)
                                this.triggers.add(triggerWithSmartDevice.trigger);

                            populate();
                        }, throwable -> {
                            getNavigator().cancelEditEvent();
                        }
                )
        );
    }

    public void addTriggersToList(List<Trigger> triggerList) {
        eventTriggersObservableArrayList.clear();
        eventTriggersObservableArrayList.addAll(triggerList);
    }

    /**
     * Get current eventtrigger data
     *
     * @return List of triggers
     */
    public MutableLiveData<List<Trigger>> getEventTriggersListLiveData() {
        return eventTriggersListLiveData;
    }

    public void deleteTrigger(Trigger trigger) {
        getCompositeDisposable().add(
                getDataManager().deleteTrigger(
                        trigger
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(deleted -> {
                    eventTriggersObservableArrayList.remove(trigger);
                })
        );
    }

    public void addEvent() {
        getNavigator().addEventTrigger();
    }

    public void chooseLocation() {
        getNavigator().chooseLocation();
    }

    public void addTrigger(Trigger trigger) {
        trigger.setEventId(this.event.getId());

        getCompositeDisposable().add(
                getDataManager().insertTrigger(
                        trigger
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(deleted -> {
                    eventTriggersObservableArrayList.add(trigger);
                })
        );
    }

    private void updateWhen(When when) {
        getCompositeDisposable().add(
                getDataManager().updateWhen(
                        when
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe()
        );
    }

    private void updateEvent(Event event) {
        getCompositeDisposable().add(
                getDataManager().updateEvent(
                        event
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe()
        );
    }

    public void setPredefinedLocation(PredefinedLocation predefinedLocation) {
        when.setCoordinateId(predefinedLocation.getCoordinateId());
        locationString.set(predefinedLocation.getName());

        updateWhen(when);
    }

    public void setAddress(Address address) {
        Coordinate coordinate = new Coordinate(address.getLatitude(), address.getLongitude());

        getCompositeDisposable().add(
                getDataManager().insertCoordinate(
                        coordinate
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe((Boolean success) -> {
                    updateFromNewlyInsertedCoordinate();
                })
        );


        locationString.set(address.getAddressLine(0));
    }

    private void updateFromNewlyInsertedCoordinate() {
        getCompositeDisposable().add(
                getDataManager().getLast().subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(coordinate -> {
                    when.setCoordinateId(coordinate.getId());

                    updateWhen(when);
                })
        );
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
                    this.close();
                })
        );
    }

    public void tester() {
        if (this.eventName.get().equals("") || this.eventName.get() == null) {
            getNavigator().makeToast("A name for the event should be set.");
            return;
        } else if (this.triggers.size() == 0) {
            getNavigator().makeToast("A trigger must be set.");
            return;
        } else if (this.dayPicker.getDays().isEmpty()) {
            getNavigator().makeToast("You must choose which days the event should trigger");
            return;
        } else if (this.locationCondition.get() == 0 && this.whenCondition.get() == 0) {
            getNavigator().makeToast("You must either choose location or a time for your event to trigger");
            return;
        } else if (this.locationCondition.get() != 0 && this.when.getCoordinateId() == null) {
            getNavigator().makeToast("No location has been set.");
            return;
        } else if (this.whenCondition.get() != 0 && this.startTime.get() == 0) {
            getNavigator().makeToast("No start time is set.");
            return;
        } else if (this.whenCondition.get() == 4 && this.endTime.get() == 0) {
            getNavigator().makeToast("No end time is set.");
            return;
        }
        when.setLocationCondition(locationCondition.get());
        when.setTimeCondition(whenCondition.get());

        event.setName(eventName.get());

        updateWhen(when);
        updateEvent(event);

        getNavigator().cancelEditEvent();
    }

    public void close() {
        getNavigator().cancelEditEvent();
    }

    public void deleteEvent() {
        getNavigator().deleteEvent(event);
    }

}
