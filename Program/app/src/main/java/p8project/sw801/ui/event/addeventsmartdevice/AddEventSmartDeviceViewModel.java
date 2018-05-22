package p8project.sw801.ui.event.addeventsmartdevice;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventSmartDeviceViewModel extends BaseViewModel<AddEventSmartDeviceNavigator> {
    private final ObservableArrayList<SmartDevice> eventArrayList = new ObservableArrayList<>();

    /**
     * Constructor for the class.
     *
     * @param dataManager       The active instance of the datamanager.
     * @param schedulerProvider The active instance of the schedulerProvider.
     */
    public AddEventSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        getListFromDb();
    }

    /**
     * Fetches a list of all smart devices stored in the database
     */
    private void getListFromDb() {
        List<SmartDevice> a = new ArrayList<>();
        ArrayList<SmartDevice> arrayList = null;

        //Fetch list from database
        getCompositeDisposable().add(
                getDataManager().getAllSmartDevices().subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(list -> {
                            RenderList(list);
                        })
        );
    }

    /**
     * Method used to update the list used in the activity for smart devices.
     *
     * @param e The list of smart devices.
     */
    public void RenderList(List<SmartDevice> e) {
        eventArrayList.clear();
        eventArrayList.addAll(e);
        getNavigator().updatelist();
    }


    /**
     * Returns the observable list of smart devices.
     *
     * @return The observable list of smart devices
     */
    public ObservableList<SmartDevice> getEventObservableList() {
        return eventArrayList;
    }

}
