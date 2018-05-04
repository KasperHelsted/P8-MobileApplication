package p8project.sw801.ui.Settings.GlobalMuteSetting;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class GlobalMuteSettingViewModel extends BaseViewModel<GlobalMuteSettingNavigator> {
    private final ObservableArrayList<GlobalMute> globalMuteObservableArrayList = new ObservableArrayList<>();

    public GlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //Get newest data from db
        getListFromDb();
    }

    /**
     * Clickevent Navigator for opening addglobalmute
     */
    public void showAddGlobalMuteSetting() {
        getNavigator().openAddGlobalMuteSettingActivity();
    }

    /**
     * Observable field of global mutes
     * @return
     */
    public ObservableList<GlobalMute> getGlobalMuteObservableList() {
        return globalMuteObservableArrayList;
    }

    /**
     * Gets globalmutes from db
     */
    public void getListFromDb() {
        //Fetch list from database
        getCompositeDisposable().add(
                getDataManager().getAllGlobalMutes().subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(list -> {
                            RenderList(list);
                        })
        );
    }

    /**
     * Updates the list of globalmutes and sends it for rendering
     * @param globalMutes
     */
    public void RenderList(List<GlobalMute> globalMutes) {
        globalMuteObservableArrayList.clear();
        globalMuteObservableArrayList.addAll(globalMutes);
        getNavigator().updatelist();
    }

    /**
     * Deletes the globalmute from the db
     * @param globalMute globalmute to delete
     */
    public void deleteGlobalMute(GlobalMute globalMute) {
        getCompositeDisposable().add(
                getDataManager().deleteGlobalMute(globalMute).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                        })
        );

        globalMuteObservableArrayList.remove(globalMute);
        getNavigator().updatelist();
    }

}