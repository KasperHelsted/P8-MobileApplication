package p8project.sw801.ui.event.addevent;

import android.view.View;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventViewModel extends BaseViewModel<AddEventNavigator> {
    public AddEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void showMapActivity() {
        getNavigator().openCreateMapActivity();
    }

    public void showNotificationOrSmartdevice() {
        getNavigator().showNotificationOrSmartdevice();
    }

    public void showTimePickerDialog(int i){getNavigator().showTimePickerDialog(i);}

    public void submitEventClick(){


    }
    public void submitEventToDatabase(){
        //TODO NEED CORRECT PARAMETERS TO PASS TO DB
    }

}
