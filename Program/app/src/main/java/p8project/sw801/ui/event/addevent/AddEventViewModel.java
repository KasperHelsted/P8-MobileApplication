package p8project.sw801.ui.event.addevent;

import android.databinding.ObservableField;
import android.text.Editable;
import android.util.Log;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventViewModel extends BaseViewModel<AddEventNavigator> {
    public final ObservableField<String> eventName = new ObservableField<>("kage1240");

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
        Log.i("sw801", this.eventName.get());

        /*
        getCompositeDisposable().add(
                getDataManager().insertCoordinate(
                        new Coordinate(0, 0)
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                })
        );

        getCompositeDisposable().add(
                getDataManager().getCoordinateCount().subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(coordinateCount -> {
                    System.out.println(coordinateCount);
                })
        );
        */
    }

    public void submitEventToDatabase() {
        //TODO NEED CORRECT PARAMETERS TO PASS TO DB
    }

}
