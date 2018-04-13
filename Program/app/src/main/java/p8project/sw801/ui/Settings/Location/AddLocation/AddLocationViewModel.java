package p8project.sw801.ui.Settings.Location.AddLocation;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddLocationViewModel extends BaseViewModel<AddLocationNavigator> {
    public AddLocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void showMapActivity() {
        System.out.println("test");
        getNavigator().openCreateMapActivity();
    }
}
