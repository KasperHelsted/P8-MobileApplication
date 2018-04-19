package p8project.sw801.ui.main.Fragments.HomeFragment;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class HomeFragmentViewModel extends BaseViewModel<HomeFragmentNavigator> {
    public HomeFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void buttonCreateEventOnClick() {
        getNavigator().buttonCreateEventOnClick();
    }

    public void buttonMyEventsOnClick() {
        getNavigator().buttonMyEventsOnClick();
    }

    public void buttonMySmartDevicesOnClick() {
        getNavigator().buttonMySmartDevicesOnClick();
    }

    public void buttonSettingsOnClick() {
        getNavigator().buttonSettingsOnClick();
    }
}
