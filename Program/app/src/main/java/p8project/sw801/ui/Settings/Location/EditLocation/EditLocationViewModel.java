package p8project.sw801.ui.Settings.Location.EditLocation;

import android.location.Address;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditLocationViewModel extends BaseViewModel<EditLocationNavigator> {
    public EditLocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void submitEditEventClick(){
        getNavigator().submitEditEventClick();
    }

    public void showMapActivity() {
        getNavigator().openCreateMapActivity();
    }

    public void submitLocationToDatabase(String locName, Address address){
        //DoStuffWithDB
        System.out.println("Dette burde kontakte en database!");
    }

}
