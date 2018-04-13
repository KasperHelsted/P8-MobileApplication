package p8project.sw801.ui.Settings.EditGlobalMuteSetting;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class EditGlobalMuteSettingModule {

    @Provides
    EditGlobalMuteSettingViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new EditGlobalMuteSettingViewModel(dataManager, schedulerProvider);
    }
}



