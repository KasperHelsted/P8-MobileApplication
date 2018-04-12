package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.ViewModelProviderFactory;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class AddSmartDeviceActivityModule {
    @Provides
    ViewModelProvider.Factory SettingsViewModelProvider(AddSmartDeviceViewModel AddSmartDeviceViewModel) {
        return new ViewModelProviderFactory<>(AddSmartDeviceViewModel);
    }

    @Provides
    AddSmartDeviceViewModel provideSettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddSmartDeviceViewModel(dataManager, schedulerProvider);
    }
}
