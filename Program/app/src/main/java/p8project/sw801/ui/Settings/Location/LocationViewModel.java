package p8project.sw801.ui.Settings.Location;

import android.databinding.ObservableArrayList;

import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class LocationViewModel extends BaseViewModel<LocationNavigator> {
    public LocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Clickevent for location clicked
     *
     * @param predefinedLocation
     */
    public void onLocationClicked(PredefinedLocation predefinedLocation) {
        getNavigator().onLocationClicked(predefinedLocation);
    }

    /**
     * Clickevent for creating new location
     */
    public void createLocation() {
        getNavigator().createLocation();
    }

    /**
     * Receives the latest predefined locationdata and renders it on the page
     */
    public void getLatestPredefinedLocationData() {
        final ObservableArrayList<PredefinedLocation> predefinedLocationList = new ObservableArrayList<>();

        getCompositeDisposable().add(
                getDataManager().getAllPredefinedLocations(
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            predefinedLocationList.addAll(response);
                            renderList(predefinedLocationList);
                        }, throwable -> {
                        })
        );

    }

    /**
     * Sends the newest data to the view
     *
     * @param predefinedLocationList list of predefined lcoations to send
     */
    private void renderList(List<PredefinedLocation> predefinedLocationList) {
        getNavigator().createList(predefinedLocationList);
    }

    /**
     * Removes a predefined location from the db
     *
     * @param pred a location to delete
     */
    public void removePredefinedLocation(PredefinedLocation pred) {
        getCompositeDisposable().add(
                getDataManager().deletePredefinedLocation(
                        pred
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {

                        })
        );
    }

}
