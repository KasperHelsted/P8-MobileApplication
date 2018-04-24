package p8project.sw801.ui.event.addeventaccessory;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Event;
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

    public void getListFromDb(SmartDevice smartDevice){
        List<Trigger> a = new ArrayList<>();
        ArrayList<Trigger> arrayList = null;


        if (smartDevice.getInternalIdentifier() == 1) {
            //Fetch list from database
            getCompositeDisposable().add(
                    getDataManager().getHueLightsBySmartDeviceId(smartDevice.getId()).subscribeOn(
                            getSchedulerProvider().io()
                    ).observeOn(getSchedulerProvider().ui())
                            .subscribe(list -> {
                                RenderListHue(list);
                            })
            );
        }else if (smartDevice.getInternalIdentifier() == 2){
            getCompositeDisposable().add(
                    getDataManager().getNestThermoBySmartDeviceId(smartDevice.getId()).subscribeOn(
                            getSchedulerProvider().io()
                    ).observeOn(getSchedulerProvider().ui())
                            .subscribe(list -> {
                                RenderListNest(list);
                            })
            );
        }
    }

    /**
     *Method used to update the view by calling updatelist function from MyEventFragment
     * @param e
     */
    public void RenderListHue(List<HueLightbulbWhite> e){
        HueArrayList.clear();
        HueArrayList.addAll(e);
        getNavigator().updatelist();
    }

    public void RenderListNest(List<NestThermostat> e){
        NestArrayList.clear();
        NestArrayList.addAll(e);
        getNavigator().updatelist();
    }

    /**
     * Returns the observable list of events
     * @return t
     */
    public ObservableList<HueLightbulbWhite> getHueObservableList() {
        return HueArrayList;
    }
    public ObservableList<NestThermostat> getNestObservableList() {
        return NestArrayList;
    }


}
