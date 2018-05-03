package p8project.sw801.ui.event.addeventhue;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventHueViewModel extends BaseViewModel<AddEventHueNavigator> {
    public AddEventHueViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Method called when the user click the turn on light button
     */
    public void turnOnClick(){getNavigator().turnOn();}

    /**
     * Method called when the user click the turn off light button
     */
    public void turnOffClick(){getNavigator().turnOff();}

    /**
     * Method called when the user click the set brightness button
     */
    public void setBrightnessClick(){getNavigator().setBrightness();}






}
