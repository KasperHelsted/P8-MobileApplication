package p8project.sw801.ui.Settings;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class SettingsViewModel extends BaseViewModel<SettingsNavigator> {
    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Navigators that handles clickEvents from the UI to activity code
     * In this case all of them are Events that open up new activities -> Navigators
     */
    public void navigateToGlobalMute() {
        getNavigator().navigateToGlobalMute();
    }

    public void navigateToPredefinedLocation() {
        getNavigator().navigateToPredefinedLocation();
    }

    public void navigateToPreferedShopping() {
        getNavigator().navigateToPreferedShopping();
    }
}
