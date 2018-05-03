package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class MySmartDeviceViewModel extends BaseViewModel<MySmartDeviceNavigator> {

    public final ObservableList<SmartDevice> mySmartDevicesObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<SmartDevice>> mySmartDevicesListLiveData;

    /**
     * Constructor for the class. Calls a method that fetches all smart devices stored in the database.
     * @param dataManager The active instance of the datamanager.
     * @param schedulerProvider The active instance of the schedulerProvider.
     */
    public MySmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        mySmartDevicesListLiveData = new MutableLiveData<>();

        fetchMySmartDevices();
    }

    /**
     * Method used to refresh the list of smart devices displayed to the user.
     * @param mySmartDevices
     */
    public void addMySmartDevicesItemsToList(List<SmartDevice> mySmartDevices) {
        mySmartDevicesObservableArrayList.clear();
        mySmartDevicesObservableArrayList.addAll(mySmartDevices);
    }

    /**
     * Method used to get a list of all smart devices stored in the database.
     */
    protected void fetchMySmartDevices() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAllSmartDevices()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(mySmartDevices -> {
                    if (mySmartDevices != null) {
                        mySmartDevicesListLiveData.setValue(mySmartDevices);
                    }

                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                }));
    }

    /**
     * Returns the list of all smart devices.
     * @return The list of all smart devices.
     */
    public MutableLiveData<List<SmartDevice>> getMySmartDevicesListLiveData() {
        return mySmartDevicesListLiveData;
    }

    /**
     * Method called when the user clicks on the add new smart device button.
     * Uses the navigator to call a method in the MySmartDevice activity class.
     */
    public void addSmartDevice() {
        getNavigator().addSmartDevice();
    }

    /**
     * Method used to delete a hue bridge associated with a specific smart device id.
     * Called when the user deletes a smart device.
     * @param id The id of the deleted smart device.
     */
    protected void deleteHueBridge(Integer id) {
        getCompositeDisposable().add(getDataManager()
                .deleteHueBridgeBySmartDeviceId(id)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    /**
     * Method used to delete all white hue lights associated with a specific smart device id.
     * Called when the user deletes a smart device.
     * @param id The id of the deleted smart device.
     */
    protected void deleteHueLightbulbWhite(Integer id) {
        getCompositeDisposable().add(getDataManager()
                .deleteWhiteHueLightsBySmartDeviceId(id)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    /**
     * Method used to delete all RGB hue lights associated with a specific smart device id.
     * Called when the user deletes a smart device.
     * @param id The id of the deleted smart device.
     */
    protected void deleteHueLightbulbRGB(Integer id) {
        getCompositeDisposable().add(getDataManager()
                .deleteRGBHueLightsBySmartDeviceId(id)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    /**
     * Method used to delete a nest hub associated with a specific smart device id.
     * Called when the user deletes a smart device.
     * @param id The id of the deleted smart device.
     */
    protected void deleteNestHub(Integer id) {
        getCompositeDisposable().add(getDataManager()
                .deleteNestHubBySmartDeviceId(id)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    /**
     * Method used to delete all nest thermostats associated with a specific smart device id.
     * Called when the user deletes a smart device.
     * @param id The id of the deleted smart device.
     */
    protected void deleteNestThermostat(Integer id) {
        getCompositeDisposable().add(getDataManager()
                .deleteNestThermostatBySmartDeviceId(id)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    /**
     * Method used to delete all triggers associated with a specific smart device id.
     * Called when the user deletes a smart device.
     * @param id The id of the deleted smart device.
     */
    protected void deleteTriggers(Integer id) {
        getCompositeDisposable().add(getDataManager()
                .deleteTriggerBySmartDeviceId(id)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    /**
     * Method used to delete a specific smart device.
     * Called when the user deletes a smart device.
     * This method calls the other deletion methods to ensure that all data relevant to the smart device is deleted.
     * @param smartDevice The smart device object to be deleted.
     */
    public void deleteDevice(SmartDevice smartDevice) {
        setIsLoading(true);
        int id = smartDevice.getId();

        getCompositeDisposable().add(getDataManager()
                .deleteSmartDevice(
                        smartDevice
                )
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(mySmartDevices -> {
                    setIsLoading(false);

                    deleteHueBridge(id);
                    deleteHueLightbulbWhite(id);
                    deleteHueLightbulbRGB(id);
                    deleteNestHub(id);
                    deleteNestThermostat(id);

                    deleteTriggers(id);

                    fetchMySmartDevices();
                }, throwable -> {
                    setIsLoading(false);
                }));
    }
}
