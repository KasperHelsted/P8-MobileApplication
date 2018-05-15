package p8project.sw801.ui.Settings.Location.EditLocation;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditLocationViewModel extends BaseViewModel<EditLocationNavigator> {
    public EditLocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Navigator for submitting an eventClick
     */
    public void submitEditLocationClick() {
        getNavigator().submitEditLocationClick();
    }

    /**
     * Clickevent Navigator for opening map activity
     */
    public void showMapActivity() {
        getNavigator().openCreateMapActivity();
    }

    /**
     * Submits the updated coords to the db
     *
     * @param coordinate         new coords object
     * @param locName            name of location
     * @param predefinedLocation predefined location to update
     */
    public void updatePredefinedLoc(Coordinate coordinate, String locName, PredefinedLocation predefinedLocation) {
        getCompositeDisposable().add(
                getDataManager().updateCoordinate(
                        coordinate
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            updatePredefinedLocation(coordinate, locName, predefinedLocation);
                        })
        );
    }

    /**
     * Takes the updates coords and submits the predifined location
     *
     * @param coords             new coords object
     * @param locName            new name of predefined location
     * @param predefinedLocation actual predefined location object
     */
    private void updatePredefinedLocation(Coordinate coords, String locName, PredefinedLocation predefinedLocation) {

        predefinedLocation.setName(locName);
        predefinedLocation.setCoordinateId(coords.getId());
        predefinedLocation.setName(locName);

        getCompositeDisposable().add(
                getDataManager().updatePredefinedLocation(
                        predefinedLocation
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            System.out.println("Updated Location");
                            getNavigator().openLocationActivty();
                        }, throwable -> {
                            System.out.println("response was null!");

                        })
        );
    }

    /**
     * Receives a location from the Id
     *
     * @param id locationId
     */
    public void getLocationFromId(int id) {
        getCompositeDisposable().add(
                getDataManager().getPredefinedLocationById(
                        id
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            PredefinedLocation predefinedLocation = response;
                            sendCoordinateFromId(predefinedLocation);
                        }, throwable -> {
                            System.out.println("response was null!");
                        })
        );
    }

    /**
     * Gets coordinate from id and sends coordinate + predefined location to the view for updates
     *
     * @param predefinedLocation predefined location to receive corresponding location from
     */
    private void sendCoordinateFromId(PredefinedLocation predefinedLocation) {
        getCompositeDisposable().add(
                getDataManager().getCoordinateById(
                        predefinedLocation.getCoordinateId()
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            renderView(predefinedLocation, response);
                        }, throwable -> {
                            System.out.println("response was null!");
                        })
        );
    }

    /**
     * Navigator for rendering the view
     *
     * @param predefinedLocation location to render
     * @param coordinate         coordinate to render
     */
    private void renderView(PredefinedLocation predefinedLocation, Coordinate coordinate) {
        getNavigator().renderFields(predefinedLocation, coordinate);
    }

}
