package p8project.sw801.ui.event.editevent;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.local.RelationEntity.TriggerWithSmartDevice;
import p8project.sw801.data.model.db.Event;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.data.model.db.When;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.ui.custom.DayPicker;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditEventViewModel extends BaseViewModel<EditEventNavigator> {
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public ArrayList<String> locationConditions;
    public ArrayList<String> whenConditions;

    public ObservableField<String> eventName = new ObservableField<>();
    public ObservableInt locationCondition = new ObservableInt(0);
    public ObservableInt whenCondition = new ObservableInt(0);

    public ObservableInt startTime = new ObservableInt();
    public ObservableInt endTime = new ObservableInt();

    public DayPicker dayPicker = new DayPicker();

    private Event event;
    private When when;
    private List<Trigger> triggers = new ArrayList<>();

    public final ObservableList<Trigger> eventTriggersObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Trigger>> eventTriggersListLiveData;


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

    private int hourAndMinuteToInt(Integer hour, Integer minute) throws ParseException {
        @SuppressLint("DefaultLocale")
        Date date = timeFormat.parse(
                String.format("%d:%02d", hour, minute)
        );

        return (int) date.getTime();

    }

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
    }

    void loadInitialEvent(int eventId) {
        if (eventId == -1)
            ((EditEvent) getNavigator()).finish();

        getCompositeDisposable().add(
                getDataManager().getEventWithData(
                        eventId
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribeOn(
                        getSchedulerProvider().ui()
                ).subscribe(
                        eventWithData -> {
                            this.event = eventWithData.event;
                            this.when = eventWithData.whens.get(0).when;

                            for (TriggerWithSmartDevice triggerWithSmartDevice : eventWithData.triggers)
                                this.triggers.add(triggerWithSmartDevice.trigger);

                            populate();
                        }
                )
        );
    }

    public void addTriggersToList(List<Trigger> triggerList) {
        eventTriggersObservableArrayList.clear();
        eventTriggersObservableArrayList.addAll(triggerList);
    }

    public MutableLiveData<List<Trigger>> getEventTriggersListLiveData() {
        return eventTriggersListLiveData;
    }

    public void deleteTrigger(Trigger trigger) {
        eventTriggersObservableArrayList.remove(trigger);
    }

    public void addEvent() {
        getNavigator().addEventTrigger();
    }

    public void tester() {
        System.out.println("locationCondition: " + locationCondition.get());
        System.out.println("whenCondition: " + whenCondition.get());

        System.out.println("days: " + dayPicker.getDays());

        System.out.println("start: " + startTime.get());
        System.out.println("end: " + endTime.get());
    }

    public void close() {
        ((EditEvent) getNavigator()).finish();
    }
}
