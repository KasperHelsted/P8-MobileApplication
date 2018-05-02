package p8project.sw801.ui.event.editevent;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import java.util.ArrayList;

import p8project.sw801.data.DataManager;
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

    public ObservableField<Integer> startTime = new ObservableField<>(0);
    /*
    public TimePicker startTime = new TimePicker();
    public TimePicker endTime = new TimePicker();
    */

    public DayPicker dayPicker = new DayPicker();

    public EditEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    void initializeLists() {
        eventName.set("Test event");

        locationCondition.set(2);
        whenCondition.set(4);

        dayPicker.setDays(new ArrayList<Integer>() {{
            add(0);
            add(1);
            add(3);
            add(5);
            add(6);
        }});
    }

    public void close() {
        ((EditEvent) getNavigator()).finish();
    }

    public void tester() {
        System.out.println("locationCondition: " + locationCondition.get());
        System.out.println("whenCondition: " + whenCondition.get());

        System.out.println("days: " + dayPicker.getDays());
        //System.out.println("text: " + timeText.get());
    }
}
