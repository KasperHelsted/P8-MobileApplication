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

    public void submitLocationToDatabase(String locName, Coordinate address){
        int id = address.getId();
        PredefinedLocation pref = new PredefinedLocation();
        pref.setCoordinateId(id);
        pref.setName(locName);

        getCompositeDisposable().add(
                getDataManager().insertPredefinedLocation(
                        pref
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            System.out.println("YEAH!");
                        })
        );
    }
}
