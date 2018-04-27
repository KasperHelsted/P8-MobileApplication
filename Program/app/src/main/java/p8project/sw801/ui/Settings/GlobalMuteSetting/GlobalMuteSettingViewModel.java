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
    private final ObservableArrayList<GlobalMute> t = new ObservableArrayList<>();

    public GlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        getListFromDb();
        //temp();
    }

    public void showAddGlobalMuteSetting() {
        getNavigator().openAddGlobalMuteSettingActivity();
    }

    public ObservableList<GlobalMute> getGlobalMuteObservableList() {
        return t;
    }

    public void getListFromDb() {
        List<GlobalMute> a = new ArrayList<>();
        ArrayList<GlobalMute> arrayList = null;

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

    public void RenderList(List<GlobalMute> g) {
        t.clear();
        t.addAll(g);
        getNavigator().updatelist();
    }

    public void deleteGlobalMute(GlobalMute globalMute) {
        getCompositeDisposable().add(
                getDataManager().deleteGlobalMute(globalMute).subscribeOn(
                        getSchedulerProvider().io()
                ).observeOn(getSchedulerProvider().ui())
                        .subscribe(response -> {
                        })
        );

        t.remove(globalMute);
        getNavigator().updatelist();
    }

}