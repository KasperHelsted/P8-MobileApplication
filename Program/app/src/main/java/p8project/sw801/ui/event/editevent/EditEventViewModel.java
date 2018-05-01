package p8project.sw801.ui.event.editevent;

import android.databinding.ObservableInt;

import java.util.ArrayList;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditEventViewModel extends BaseViewModel<EditEventNavigator> {
    public ArrayList<String> locationConditions = new ArrayList<>();
    public ArrayList<String> whenConditions = new ArrayList<>();

    public final ObservableInt locationCondition = new ObservableInt(0);
    public final ObservableInt whenCondition = new ObservableInt(0);

    public EditEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        initializeLists();
    }

    private void initializeLists() {
        locationConditions.add("No location chosen");
        locationConditions.add("At Location");
        locationConditions.add("Near Location");
        locationConditions.add("Leaving Location");

        whenConditions.add("No time condition chosen");
        whenConditions.add("Before this time");
        whenConditions.add("At this time");
        whenConditions.add("After this time");
        whenConditions.add("Between these times");
    }

    public void tester() {
        System.out.println(locationCondition.get()  );
    }
}
