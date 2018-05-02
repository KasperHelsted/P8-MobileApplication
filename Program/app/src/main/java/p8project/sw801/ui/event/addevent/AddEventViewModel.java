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


    public AddEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //tempAddEvent();

    }

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

    public void showNotificationOrSmartdevice() {
        getNavigator().showNotificationOrSmartdevice();
    }

    public void showTimePickerDialog(int i) {
        getNavigator().showTimePickerDialog(i);
    }

    public void submitEventClick() {
        getNavigator().submitEventClick();

    }

    public void updateLocationData() {
        getCompositeDisposable().add(
                getDataManager().getLastPredefinedLocation().subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getNavigator().updateActiveLocation(response);
                        })
        );

    }

    /*public void submitEventToDatabase(Event event)
    {
        // Save Event to DB
        getCompositeDisposable().add(
                getDataManager().insertEvent(event).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe()
        );
    }*/


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

    public void saveEvent(Event event) {
        // Save Event to DB
        getCompositeDisposable().add(
                getDataManager().insertEvent(event).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe()
        );
    }

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
                        .subscribe()
        );
    }

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

    /*public void temp(Trigger t){
        getCompositeDisposable().add(
                getDataManager().insertTrigger(t).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(list -> {
                        })
        );
    }
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

    /*
    private void tempAddEvent(){
=======
    private void tempAddEvent() {
>>>>>>> master

        Event e = new Event();
        e.setName("1");
        e.setAlarmId("1");
        e.setIntentId("1");
        e.setActive(false);

        Event r = new Event();
        r.setName("2");
        r.setAlarmId("2");
        r.setIntentId("2");
        r.setActive(true);


        getCompositeDisposable().add(
                getDataManager().insertAllEvents(
                        e, r
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    tempAddSmartDevice();
                })
        );

    }

    private void tempAddSmartDevice() {

        SmartDevice e = new SmartDevice();
        e.setActive(true);
        e.setDeviceName("Hue");
        e.setInternalIdentifier(1);

        SmartDevice r = new SmartDevice();
        r.setActive(true);
        r.setDeviceName("Nest");
        r.setInternalIdentifier(2);

        getCompositeDisposable().add(
                getDataManager().insertAllSmartDevices(
                        e, r
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("SmartDevice", "Smart devices inserted");
                    tempHAN();
                })
        );

    }

    private void temphue() {


        HueLightbulbWhite e = new HueLightbulbWhite();
        e.setDeviceName("Kitchen");
        e.setHueBridgeId(1);
        e.setDeviceId("test123");
        e.setSmartDeviceId(1);
        HueLightbulbWhite r = new HueLightbulbWhite();
        r.setDeviceName("Living Room");
        r.setHueBridgeId(1);
        r.setDeviceId("test123b");
        r.setSmartDeviceId(1);

        getCompositeDisposable().add(
                getDataManager().insertAllWhiteHueLightbulbs(
                        e, r
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                })
        );
    }

    private void tempNest() {
        NestThermostat t = new NestThermostat();
        t.setName("Kitchen term");
        t.setNestHubId(1);
        t.setDeviceId("12321");
        t.setSmartDeviceId(2);
        NestThermostat y = new NestThermostat();
        y.setName("Living room term");
        y.setNestHubId(1);
        y.setDeviceId("123344");
        y.setSmartDeviceId(2);

        getCompositeDisposable().add(
                getDataManager().insertAllNestThermostats(
                        t, y
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("NEST THERMO", "NEST THERMO INSERTED");
                })
        );
    }

    private void tempHAN() {

        HueBridge h = new HueBridge();
        NestHub n = new NestHub();

        h.setDeviceIP("192.167.0.1");
        h.setDeviceMac("123456789");
        h.setDeviceToken("123456789");
        h.setSmartDeviceId(1);

        n.setBearerToken("123745782345yfgvgyfvdfwtyfys");
        n.setSmartDeviceId(2);


        getCompositeDisposable().add(
                getDataManager().insertNestHub(
                        n
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("nest", "NEST INSERTED");
                    tempNest();
                })
        );

        getCompositeDisposable().add(
                getDataManager().insertHueBridge(
                        h
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("Hue", "HUE INSERTED");
                    temphue();
                })
        );

    }*/

}
