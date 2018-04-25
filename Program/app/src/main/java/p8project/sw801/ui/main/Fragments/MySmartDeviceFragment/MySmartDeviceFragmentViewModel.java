package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;


import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class MySmartDeviceFragmentViewModel extends BaseViewModel<MySmartDeviceFragmentNavigator> {
    //private ObservableArrayList<SmartDevice> smartDeviceArrayList = new ObservableArrayList<>();

    public MySmartDeviceFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //temp();
        //getListFromDb();
    }

    public void addNewSmartDevice(){getNavigator().addNewSmartDevice();}

    /**
     * Method used to fetch the list of smart devices from the database and calling a method to update the view
     */
    public void getListFromDb(){

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
     *Method used to update the view by calling updatelist function from MySmartDeviceFragment
     * @param e
     */
    public void RenderList(List<SmartDevice> e){
        //smartDeviceArrayList.clear();
        //smartDeviceArrayList.addAll(e);
        //updateList();

    }

    private void updateList(){
        getNavigator().updatelist();
    }

    public void createSmartDevices()
    {
        ArrayList<SmartDevice> smartDeviceList = new ArrayList<SmartDevice>();
        getCompositeDisposable().add(
                getDataManager().getAllSmartDevices(

                ).subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                            if (response != null) {
                                if(!response.isEmpty())
                                {
                                    smartDeviceList.addAll(response);
                                }
                            }
                            createSmartdeviceList(smartDeviceList);
                        })
        );
    }

    private void createSmartdeviceList(ArrayList<SmartDevice> smartDevices){
        getNavigator().createListView(smartDevices);
    }



    /**
     * Returns the observable list of smart devices
     * @return t
     */
    public ObservableList<SmartDevice> getSmartDeviceObservableList() {
        //return smartDeviceArrayList;
        return null;
    }

    /**
     *Deletes a smart device from the database and calls a method to update the view
     * @param sd
     */
    public void deleteSmartDevice(SmartDevice sd){
        getCompositeDisposable().add(
                getDataManager().deleteSmartDevice(sd).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response ->{})
        );

        //smartDeviceArrayList.remove(sd);
        getNavigator().updatelist();
    }

    private void temp(){
        /*
        HueBridge e = new HueBridge();
        e.setActive(true);
        e.setDeviceName("Hue");
        e.setInternalIdentifier(1);
        e.setDeviceIP("192.168.0.1");
        e.setUsername("taffern");

        //SmartDevice r = new SmartDevice();
        //r.setActive(true);
        //r.setDeviceName("Nest");
        //r.setInternalIdentifier(2);

        getCompositeDisposable().add(
                getDataManager().insertAllSmartDevices(
                        e
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                })
        );



*/
    }
}