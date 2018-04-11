package p8project.sw801.ui.event.createeventmap;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class CreateEventMapViewModel extends BaseViewModel<CreateEventMapNavigator> {
    public CreateEventMapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
