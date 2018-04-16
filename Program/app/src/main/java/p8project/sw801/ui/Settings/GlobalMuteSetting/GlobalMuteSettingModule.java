package p8project.sw801.ui.Settings.GlobalMuteSetting;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class GlobalMuteSettingModule {

    @Provides
    GlobalMuteSettingViewModel provideGlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new GlobalMuteSettingViewModel(dataManager, schedulerProvider);
    }
}