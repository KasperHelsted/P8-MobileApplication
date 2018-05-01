package p8project.sw801.ui.event.addeventaccessory;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Accessories.HueLightbulbWhite;
import p8project.sw801.data.model.db.Smartdevice.Accessories.NestThermostat;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;
import p8project.sw801.data.model.db.Trigger;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddEventAccessoryViewModel extends BaseViewModel<AddEventAccessoryNavigator> {
    private final ObservableArrayList<HueLightbulbWhite> HueArrayList = new ObservableArrayList<>();
    private final ObservableArrayList<NestThermostat> NestArrayList = new ObservableArrayList<>();

    public AddEventAccessoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //temp2();
    }

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
     * Method used to update the view by calling updatelist function from MyEventFragment
     *
     * @param e
     */
    public void RenderListHue(List<HueLightbulbWhite> e) {
        HueArrayList.clear();
        HueArrayList.addAll(e);
        getNavigator().updatelist();
    }

    public void RenderListNest(List<NestThermostat> e) {
        NestArrayList.clear();
        NestArrayList.addAll(e);
        getNavigator().updatelist();
    }

    /**
     * Returns the observable list of events
     *
     * @return t
     */
    public ObservableList<HueLightbulbWhite> getHueObservableList() {
        return HueArrayList;
    }

    public ObservableList<NestThermostat> getNestObservableList() {
        return NestArrayList;
    }

    private void tempNest() {
        NestThermostat t = new NestThermostat();
        t.setName("Kitchen term");
        t.setNestHubId(1);
        t.setDeviceId("12321");
        t.setSmartDeviceId(2);
        NestThermostat y = new NestThermostat();
        y.setName("Living room term");
        y.setNestHubId(1);
        y.setDeviceId("123344");
        y.setSmartDeviceId(2);

        getCompositeDisposable().add(
                getDataManager().insertAllNestThermostats(
                        t, y
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("NEST THERMO", "NEST THERMO INSERTED");
                })
        );
    }


    private void temp2() {

        HueBridge h = new HueBridge();
        NestHub n = new NestHub();

        h.setDeviceIP("192.167.0.1");
        h.setSmartDeviceId(1);

        n.setBearerToken("123745782345yfgvgyfvdfwtyfys");
        n.setSmartDeviceId(2);


        getCompositeDisposable().add(
                getDataManager().insertNestHub(
                        n
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("nest", "NEST INSERTED");
                    tempNest();
                })
        );

        getCompositeDisposable().add(
                getDataManager().insertHueBridge(
                        h
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    Log.i("Hue", "HUE INSERTED");
                    //temphue();
                })
        );

    }
}
