package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class AddGlobalMuteSettingModule {

    @Provides
    AddGlobalMuteSettingViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddGlobalMuteSettingViewModel(dataManager, schedulerProvider);
    }
}
