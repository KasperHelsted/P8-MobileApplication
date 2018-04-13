package p8project.sw801.ui.event.addevent;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Coordinate;
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
        /*
        getCompositeDisposable().add(
                getDataManager().insertCoordinate(
                        new Coordinate(0, 0)
                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            System.out.println("YEAH!");
                        })
        );
        */

        getCompositeDisposable().add(
                getDataManager().getAllCoordinates().subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(coordinates -> {
                    for (Coordinate coordinate : coordinates) {
                        System.out.println(coordinate);
                    }
                }, throwable -> {

                })
        );
    }

    public void submitEventToDatabase() {
        //TODO NEED CORRECT PARAMETERS TO PASS TO DB
    }

}
