package p8project.sw801.ui.Settings.Location.AddLocation;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.ViewModelProviderFactory;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.Settings.Location.LocationViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class AddLocationSettingModule {
    @Provides
    ViewModelProvider.Factory LocationViewModelProvider(AddLocationViewModel addLocationViewModel) {
        return new ViewModelProviderFactory<>(addLocationViewModel);
    }

    @Provides
    AddLocationViewModel provideAddLocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddLocationViewModel(dataManager, schedulerProvider);
    }
}
