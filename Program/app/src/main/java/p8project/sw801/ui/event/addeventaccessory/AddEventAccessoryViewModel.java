package p8project.sw801.ui.event.addeventaccessory;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventAccessoryViewModel extends BaseViewModel<AddEventAccessoryNavigator> {
    private final ObservableArrayList<HueLightbulbWhite> HueArrayList = new ObservableArrayList<>();
    private final ObservableArrayList<NestThermostat> NestArrayList = new ObservableArrayList<>();

    /**
     * Constructor for the class.
     *
     * @param dataManager       The active instance of the datamanager.
     * @param schedulerProvider The active instance of the schedulerProvider.
     */
    public AddEventAccessoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //temp2();
    }

    /**
     * Fetches either a list of hue light or nest thermostats from the database.
     *
     * @param smartDevice The smart devices chosen from the previous activity. The id of this is used to fetch all associated accessories for this smart device.
     */
    public void getListFromDb(SmartDevice smartDevice) {
        List<Trigger> a = new ArrayList<>();
        ArrayList<Trigger> arrayList = null;


        if (smartDevice.getInternalIdentifier() == 1) {
            //Fetch list from database
            getCompositeDisposable().add(
                    getDataManager().getWhiteHueLightsBySmartDeviceId(smartDevice.getId()).subscribeOn(
                            getSchedulerProvider().io()
                    ).observeOn(getSchedulerProvider().ui())
                            .subscribe(list -> {
                                RenderListHue(list);
                            })
            );
        } else if (smartDevice.getInternalIdentifier() == 2) {
            getCompositeDisposable().add(
                    getDataManager().getNestThermostatBySmartDeviceId(smartDevice.getId()).subscribeOn(
                            getSchedulerProvider().io()
                    ).observeOn(getSchedulerProvider().ui())
                            .subscribe(list -> {
                                RenderListNest(list);
                            })
            );
        }
    }

    /**
     * Method used to update the list used in the activity for hue lights.
     *
     * @param e The list of hue lights.
     */
    public void RenderListHue(List<HueLightbulbWhite> e) {
        HueArrayList.clear();
        HueArrayList.addAll(e);
        getNavigator().updatelist();
    }

    /**
     * Method used to update the list used in the activity for nest thermostats.
     *
     * @param e The list of nest thermostats.
     */
    public void RenderListNest(List<NestThermostat> e) {
        NestArrayList.clear();
        NestArrayList.addAll(e);
        getNavigator().updatelist();
    }

    /**
     * Returns the observable list of hue lights
     *
     * @return The observable list of hue lights
     */
    public ObservableList<HueLightbulbWhite> getHueObservableList() {
        return HueArrayList;
    }

    /**
     * Returns the observable list of nest thermostats
     *
     * @return The observable list of nest thermostats
     */
    public ObservableList<NestThermostat> getNestObservableList() {
        return NestArrayList;
    }

}
