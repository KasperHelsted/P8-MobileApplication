package p8project.sw801.ui.Settings;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.ViewModelProviderFactory;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class SettingsActivityModule {

    @Provides
    ViewModelProvider.Factory SettingsViewModelProvider(SettingsViewModel settingsViewModel) {
        return new ViewModelProviderFactory<>(settingsViewModel);
    }

    @Provides
    SettingsViewModel provideSettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new SettingsViewModel(dataManager, schedulerProvider);
    }
}