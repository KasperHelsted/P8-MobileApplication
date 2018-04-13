package p8project.sw801.ui.Settings.Location.EditLocation;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditLocationViewModel extends BaseViewModel<EditLocationNavigator> {
    public EditLocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
