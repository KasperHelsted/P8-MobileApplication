package p8project.sw801.ui.Settings.Shopping;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class ShoppingSettingModule {

    @Provides
    ShoppingSettingViewModel provideSettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ShoppingSettingViewModel(dataManager, schedulerProvider);
    }
}
