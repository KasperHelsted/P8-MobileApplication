package p8project.sw801.ui.Settings.Location.EditLocation;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.ViewModelProviderFactory;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class EditLocationModule {
    @Provides
    ViewModelProvider.Factory LocationViewModelProvider(EditLocationViewModel editLocationViewModel) {
        return new ViewModelProviderFactory<>(editLocationViewModel);
    }

    @Provides
    EditLocationViewModel provideSettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new EditLocationViewModel(dataManager, schedulerProvider);
    }
}
