package p8project.sw801.ui.Settings.Location;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.ViewModelProviderFactory;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class LocationModule {
    @Provides
    ViewModelProvider.Factory LocationViewModelProvider(LocationViewModel locationViewModel) {
        return new ViewModelProviderFactory<>(locationViewModel);
    }

    @Provides
    LocationViewModel provideSettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new LocationViewModel(dataManager, schedulerProvider);
    }
}
