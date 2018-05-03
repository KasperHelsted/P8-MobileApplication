package p8project.sw801.ui.main.Fragments.HomeFragment;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class HomeFragmentViewModel extends BaseViewModel<HomeFragmentNavigator> {
    /**
     * Constructor for the class.
     * @param dataManager The active instance of the datamanager.
     * @param schedulerProvider The active instance of the schedulerProvider.
     */
    public HomeFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Method called when a user click on the create event button.
     * Uses the navigator to call a method in the AddEvent activity class.
     */
    public void buttonCreateEventOnClick() {
        getNavigator().buttonCreateEventOnClick();
    }

    /**
     * Method called when a user click on the My event button.
     * Uses the navigator to call a method in the AddEvent activity class.
     */
    public void buttonMyEventsOnClick() {
        getNavigator().buttonMyEventsOnClick();
    }

    /**
     * Method called when a user click on the My smart devices button.
     * Uses the navigator to call a method in the AddEvent activity class.
     */
    public void buttonMySmartDevicesOnClick() {
        getNavigator().buttonMySmartDevicesOnClick();
    }

    /**
     * Method called when a user click on the Settings button.
     * Uses the navigator to call a method in the AddEvent activity class.
     */
    public void buttonSettingsOnClick() {
        getNavigator().buttonSettingsOnClick();
    }
}
