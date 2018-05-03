package p8project.sw801.ui.event.editevent;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.ui.custom.DayPicker;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditEventViewModel extends BaseViewModel<EditEventNavigator> {
    public ArrayList<String> locationConditions = new ArrayList<String>() {{
        add("No location chosen");
        add("At Location");
        add("Near Location");
        add("Leaving Location");
    }};
    public ArrayList<String> whenConditions = new ArrayList<String>() {{
        add("No time condition chosen");
        add("Before this time");
        add("At this time");
        add("After this time");
        add("Between these times");
    }};

    public ObservableField<String> eventName = new ObservableField<>();
    public ObservableInt locationCondition = new ObservableInt(0);
    public ObservableInt whenCondition = new ObservableInt(0);

    public ObservableInt startTime = new ObservableInt();
    public ObservableInt endTime = new ObservableInt();

    public DayPicker dayPicker = new DayPicker();

    public final ObservableList<Trigger> eventTriggersObservableArrayList = new ObservableArrayList<>();
    private final MutableLiveData<List<Trigger>> eventTriggersListLiveData;


    public EditEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        eventTriggersListLiveData = new MutableLiveData<>();

    }

    void initializeLists() {
//        eventName.set("Test event");
//        locationCondition.set(2);
//        whenCondition.set(4);
//        startTime.set(3600 * 10000);
//        endTime.set(3600 * 10000);
//        dayPicker.setDays(new ArrayList<Integer>() {{
//            add(0);
//            add(1);
//            add(3);
//            add(5);
//            add(6);
//        }});

        List<Trigger> test = new ArrayList<>();
        test.add(new Trigger() {{
            setNotificationText("TESTER 1");
        }});
        test.add(new Trigger() {{
            setNotificationText("TESTER 2");
        }});
        test.add(new Trigger() {{
            setNotificationText("TESTER 3");
        }});
        test.add(new Trigger() {{
            setNotificationText("TESTER 4");
        }});
        test.add(new Trigger() {{
            setNotificationText("TESTER 5");
        }});
        test.add(new Trigger() {{
            setNotificationText("TESTER 6");
        }});

        eventTriggersListLiveData.setValue(test);
    }

    public void close() {
        ((EditEvent) getNavigator()).finish();
    }

    public void tester() {
        System.out.println("locationCondition: " + locationCondition.get());
        System.out.println("whenCondition: " + whenCondition.get());

        System.out.println("days: " + dayPicker.getDays());

        System.out.println("start: " + startTime.get());
        System.out.println("end: " + endTime.get());
    }

    public void addTriggersToList(List<Trigger> triggerList) {
        eventTriggersObservableArrayList.clear();
        eventTriggersObservableArrayList.addAll(triggerList);
    }

    public MutableLiveData<List<Trigger>> getEventTriggersListLiveData() {
        return eventTriggersListLiveData;
    }

}
