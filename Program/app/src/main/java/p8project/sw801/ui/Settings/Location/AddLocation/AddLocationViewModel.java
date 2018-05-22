package p8project.sw801.ui.Settings.Location.AddLocation;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddLocationViewModel extends BaseViewModel<AddLocationNavigator> {
    public AddLocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * ClickEvent navigators
     */
    public void showMapActivity() {
        getNavigator().openCreateMapActivity();
    }

    public void submitLocationClick() {
        getNavigator().submitLocationClick();
    }

    public void openLocationActivty(PredefinedLocation pred) {
        getNavigator().openLocationActivty(pred);
    }

    /**
     * Submits a location to the database by first inserting the coordiate
     *
     * @param locName name of location
     * @param address coordinate of the location
     */
    public void submitLocationToDatabase(String locName, Coordinate address) {

        getCompositeDisposable().add(
                getDataManager().insertCoordinate(
                        address
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            createPredefinedLocation(locName);
                        })
        );
    }

    /**
     * Creates predifined location and inserts coordinate id
     *
     * @param locName name of predefined location
     */
    private void createPredefinedLocation(String locName) {

        PredefinedLocation pref = new PredefinedLocation();

        getCompositeDisposable().add(
                getDataManager().getLast(
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            pref.setCoordinateId(response.getId());
                            pref.setName(locName);
                            submitLocation(pref);
                        }, throwable -> {
                        })
        );
    }

    /**
     * Submits the predefined location to the db
     *
     * @param predefinedLocation location to insert
     */
    private void submitLocation(PredefinedLocation predefinedLocation) {
        getCompositeDisposable().add(
                getDataManager().insertPredefinedLocation(
                        predefinedLocation
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            getLastLocation();
                        })
        );
    }

    /**
     * Gets the last inserted predefined location
     */
    private void getLastLocation() {
        getCompositeDisposable().add(
                getDataManager().getLastPredefinedLocation(
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            openLocationActivty(response);
                        })
        );
    }

}
