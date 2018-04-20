package p8project.sw801.ui.event.addevent;

import android.databinding.ObservableField;
import android.text.Editable;
import android.util.Log;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Trigger;
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

    public void showTimePickerDialog(int i) {
        getNavigator().showTimePickerDialog(i);
    }

    public void submitEventClick() {
        getNavigator().submitEventClick();

    }

    public void submitEventToDatabase() {
        //TODO NEED CORRECT PARAMETERS TO PASS TO DB
    }


    public void temp(Trigger t){
        getCompositeDisposable().add(
                getDataManager().insertTrigger(t).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(list -> {
                        })
        );




    }

}
