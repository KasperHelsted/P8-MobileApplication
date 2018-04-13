package p8project.sw801.ui.Settings.Location.AddLocation;

import android.location.Address;

import p8project.sw801.data.DataManager;
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

    public void submitLocationToDatabase(String locName, Address address){
        //DoStuffWithDB
        System.out.println("Dette burde kontakte en database!");
    }
}
