package p8project.sw801.ui.event.addeventnest;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventNestViewModel extends BaseViewModel<AddEventNestNavigator> {
    public AddEventNestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
