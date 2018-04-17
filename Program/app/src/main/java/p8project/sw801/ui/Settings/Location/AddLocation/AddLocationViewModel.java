package p8project.sw801.ui.Settings.Location.AddLocation;

import android.database.Observable;

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
    public void openLocationActivty(){
        getNavigator().openLocationActivty();
    }

    public void submitLocationToDatabase(String locName, Coordinate address){

        getCompositeDisposable().add(
                getDataManager().insertCoordinate(
                        address
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            System.out.println("Coordinate submitted");
                        })
        );
        Observable<Coordinate> coordinate;
        PredefinedLocation pref = new PredefinedLocation();
        getCompositeDisposable().add(
                getDataManager().getLast(
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null){
                                pref.setId(response.getId());
                            }
                            else{
                                pref.setId(1);
                                System.out.println("response was null!");
                            }

                        })
        );
        pref.setName(locName);

        getCompositeDisposable().add(
                getDataManager().insertPredefinedLocation(
                        pref
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            System.out.println("Location submitted!");
                        })
        );
    }

}
