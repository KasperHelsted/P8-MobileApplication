package p8project.sw801.ui.event.addeventhue;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventHueViewModel extends BaseViewModel<AddEventHueNavigator> {
    public AddEventHueViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
