package p8project.sw801.ui.Settings.GlobalMuteSetting;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class GlobalMuteSettingViewModel extends BaseViewModel<GlobalMuteSettingNavigator>  {
    public GlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void showAddGlobalMuteSetting() {
        getNavigator().openAddGlobalMuteSettingActivity();
    }

}