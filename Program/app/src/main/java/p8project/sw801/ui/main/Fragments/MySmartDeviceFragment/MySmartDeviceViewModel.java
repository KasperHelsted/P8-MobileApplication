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

    public MySmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        mySmartDevicesListLiveData = new MutableLiveData<>();

        fetchMySmartDevices();
    }

    public void addMySmartDevicesItemsToList(List<SmartDevice> mySmartDevices) {
        mySmartDevicesObservableArrayList.clear();
        mySmartDevicesObservableArrayList.addAll(mySmartDevices);
    }

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
                    getNavigator().handleError(throwable);
                }));
    }

    public MutableLiveData<List<SmartDevice>> getMySmartDevicesListLiveData() {
        return mySmartDevicesListLiveData;
    }

    public ObservableList<SmartDevice> getMySmartDevicesObservableList() {
        return mySmartDevicesObservableArrayList;
    }


}
