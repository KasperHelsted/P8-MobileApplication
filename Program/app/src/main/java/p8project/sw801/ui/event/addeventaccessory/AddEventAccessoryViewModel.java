package p8project.sw801.ui.event.addeventaccessory;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventAccessoryViewModel extends BaseViewModel<AddEventAccessoryNavigator> {
    public AddEventAccessoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
