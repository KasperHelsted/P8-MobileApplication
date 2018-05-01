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
    public void submitEditLocationClick(){
        getNavigator().submitEditLocationClick();
    }

    public void showMapActivity() {
        getNavigator().openCreateMapActivity();
    }

    public void openLocationActivty(){
        getNavigator().openLocationActivty();
    }

    public void updatePredefinedLoc(Coordinate coordinate,String locName, PredefinedLocation predefinedLocation){
        getCompositeDisposable().add(
                getDataManager().updateCoordinate(
                        coordinate
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            updatePredefinedLocation(coordinate,locName,predefinedLocation);
                        })
        );
    }

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
                            if (response != null) {
                                System.out.println("Updated Location");
                                getNavigator().openLocationActivty();

                            } else {
                                System.out.println("response was null!");
                            }

                        })
        );
    }

    public void getLocationFromId(int id){
        getCompositeDisposable().add(
                getDataManager().getPredefinedLocationById(
                        id
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null){
                                PredefinedLocation predefinedLocation = response;
                                sendCoordinateFromId(predefinedLocation);
                            }
                            else{
                                //TODO: ERROR?
                                System.out.println("response was null!");
                            }

                        })
        );
    }
    private void sendCoordinateFromId(PredefinedLocation predefinedLocation){
        getCompositeDisposable().add(
                getDataManager().getCoordinateById(
                        predefinedLocation.getCoordinateId()
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null) {
                                renderView(predefinedLocation,response);
                            } else {
                                System.out.println("response was null!");
                            }
                        })
        );


    }
    private void renderView(PredefinedLocation predefinedLocation, Coordinate coordinate){
        getNavigator().renderFields(predefinedLocation, coordinate);
    }

}
