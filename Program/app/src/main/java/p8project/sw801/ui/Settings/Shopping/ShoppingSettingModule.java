package p8project.sw801.ui.Settings.Shopping;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.ViewModelProviderFactory;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.Settings.Location.LocationViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class ShoppingSettingModule {
    @Provides
    ViewModelProvider.Factory LocationViewModelProvider(ShoppingSettingViewModel shoppingSettingViewModel) {
        return new ViewModelProviderFactory<>(shoppingSettingViewModel);
    }

    @Provides
    ShoppingSettingViewModel provideSettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ShoppingSettingViewModel(dataManager, schedulerProvider);
    }
}
