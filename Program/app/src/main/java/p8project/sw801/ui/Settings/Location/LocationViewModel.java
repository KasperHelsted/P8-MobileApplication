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
    public void onLocationClicked(){
        getNavigator().onLocationClicked();

    }
    public void createLocation(){
        getNavigator().createLocation();
    }
    public void getLatestPredefinedLocationData(){
        final ObservableArrayList<PredefinedLocation> predefinedLocationList = new ObservableArrayList<>();

        getCompositeDisposable().add(
                getDataManager().getAllPredefinedLocations(
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null){
                                predefinedLocationList.addAll(response);
                                renderList(predefinedLocationList);
                            }
                            else{

                            }
                        })
        );

    }

    private void renderList(List<PredefinedLocation> predefinedLocationList){
        getNavigator().createList(predefinedLocationList);
    }
    public void removePredefinedLocation(PredefinedLocation pred){
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
