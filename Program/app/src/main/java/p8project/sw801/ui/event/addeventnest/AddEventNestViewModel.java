package p8project.sw801.ui.event.addeventnest;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventNestViewModel extends BaseViewModel<AddEventNestNavigator> {
    public AddEventNestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Method called when the user click the turn on thermostat button
     */
    public void turnOnClick(){getNavigator().turnOn();}

    /**
     * Method called when the user click the turn off thermostat button
     */
    public void turnOffClick(){getNavigator().turnOff();}

    /**
     * Method called when the user click the set temperature button
     */
    public void setTempClick(){getNavigator().setTemp();}

}
