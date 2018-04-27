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
    public void showMapActivity() {
        getNavigator().openCreateMapActivity();
    }
    public void submitLocationClick(){
        getNavigator().submitLocationClick();
    }
    public void openLocationActivty(PredefinedLocation pred)
    {
        getNavigator().openLocationActivty(pred);
    }


    public void submitLocationToDatabase(String locName, Coordinate address) {

        getCompositeDisposable().add(
                getDataManager().insertCoordinate(
                        address
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            System.out.println("Coordinate submitted");
                            createPredefinedLocation(locName);
                        })
        );
    }
    private void createPredefinedLocation(String locName) {

        PredefinedLocation pref = new PredefinedLocation();

        getCompositeDisposable().add(
                getDataManager().getLast(
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null) {
                                pref.setCoordinateId(response.getId());
                                pref.setName(locName);
                                submitLocation(pref);
                            } else {
                                System.out.println("response was null!");
                            }

                        })
        );
    }
    private void submitLocation(PredefinedLocation predefinedLocation){
        getCompositeDisposable().add(
                getDataManager().insertPredefinedLocation(
                        predefinedLocation
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            System.out.println("Location submitted!");
                            getLastLocation();
                        })
        );
    }
    private void getLastLocation(){
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
