package p8project.sw801.ui.event.addeventsmartdevice;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.ui.event.createeventmap.CreateEventMapNavigator;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventSmartDeviceViewModel  extends BaseViewModel<AddEventSmartDeviceNavigator> {
    private final ObservableArrayList<SmartDevice> eventArrayList = new ObservableArrayList<>();

    public AddEventSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //temp();
        getListFromDb();
    }

    private void getListFromDb(){
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


    public void RenderList(List<SmartDevice> e){
        eventArrayList.clear();
        eventArrayList.addAll(e);
        getNavigator().updatelist();
    }


    public ObservableList<SmartDevice> getEventObservableList() {
        return eventArrayList;
    }


    private void temp(){

        SmartDevice e = new SmartDevice();
        e.setActive(true);
        e.setDeviceName("Hue");
        e.setInternalIdentifier(1);

        SmartDevice r = new SmartDevice();
        r.setActive(true);
        r.setDeviceName("Nest");
        r.setInternalIdentifier(2);

        getCompositeDisposable().add(
                getDataManager().insertAllSmartDevices(
                        e,r
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("SmartDevice", "Smart devices inserted");
                })
        );

    }


}
