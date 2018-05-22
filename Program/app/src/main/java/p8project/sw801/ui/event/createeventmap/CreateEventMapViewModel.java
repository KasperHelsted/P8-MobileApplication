package p8project.sw801.ui.event.createeventmap;

import android.location.Location;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class CreateEventMapViewModel extends BaseViewModel<CreateEventMapNavigator> {
    public CreateEventMapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Sets a default location if the last known location of the user is null
     *
     * @return The default location
     */
    public Location setDefaultLocation() {
        Location l = new Location("");
        l.setLatitude(57.016959);
        l.setLongitude(9.991390);
        return l;

    }

    /**
     * Method called when the user clicks the cancel button
     */
    public void cancleButton() {
        getNavigator().cancelButton();
    }

    /**
     * Method called when the user clicks the confirms button
     */
    public void confirmButton() {
        getNavigator().confirmButton();
    }

}
