package p8project.sw801.ui.main;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
public class MainViewModel extends BaseViewModel<MainNavigator> {
    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

    }

    public void buttonMyEventsOnClick() {
        getNavigator().buttonMyEventsOnClick();
    }

    public void buttonMySmartDevicesOnClick() {
        getNavigator().buttonMySmartDevicesOnClick();
    }

}
