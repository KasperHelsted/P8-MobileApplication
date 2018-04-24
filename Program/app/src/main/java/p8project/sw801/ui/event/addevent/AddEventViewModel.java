package p8project.sw801.ui.event.addevent;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.text.Editable;
import android.util.Log;

import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.local.RelationEntity.EventWithData;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventViewModel extends BaseViewModel<AddEventNavigator> {



    public AddEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //tempAddEvent();

    }

    public void showMapActivity() {
        getNavigator().openCreateMapActivity();
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

    public void submitEventToDatabase(Event event, When when, List<Trigger> trigList) {
        //TODO NEED CORRECT PARAMETERS TO PASS TO DB

        Event eventId = new Event();

        // Save Event to DB
        getCompositeDisposable().add(
                getDataManager().insertEvent(event).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                .subscribe()
        );

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


        // Use this ID when saving Triggers and Whens


    }
    public void saveTriggers(List<Trigger> tList, Event eventId)
    {
        List<Trigger> tListWithId = null;
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

    public void saveWhen(When when, Event eventId)
    {
        when.setEventId(eventId.getId());
        getCompositeDisposable().add(
                getDataManager().insertWhen(when).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe()
        );
    }

    public void temp(Trigger t){
        getCompositeDisposable().add(
                getDataManager().insertTrigger(t).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(list -> {
                        })
        );
    }

    public void geteventwithdata(){
        getCompositeDisposable().add(
                getDataManager().getEventWithData(1).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(event -> {
                    getNavigator().testerfunction(event);
                })
        );
    }

    private void tempAddEvent(){

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
                        e,r
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    tempAddSmartDevice();
                })
        );

    }

    private void tempAddSmartDevice(){

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
                        e,r
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("SmartDevice", "Smart devices inserted");
                    tempHAN();
                })
        );

    }

    private void temphue(){


        HueLightbulbWhite e = new HueLightbulbWhite();
        e.setDeviceName("Kitchen");
        e.setHueBridgeId(1);
        e.setDeviceId(1);
        e.setSmartDeviceId(1);
        HueLightbulbWhite r = new HueLightbulbWhite();
        r.setDeviceName("Living Room");
        r.setHueBridgeId(1);
        r.setDeviceId(2);
        r.setSmartDeviceId(1);

        getCompositeDisposable().add(
                getDataManager().insertAllHueLights(
                        e,r
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                })
        );
    }

    private void tempNest(){
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
                getDataManager().insertAllNestThermos(
                        t,y
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("NEST THERMO", "NEST THERMO INSERTED");
                })
        );
    }

    private void tempHAN(){

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

    }

}
