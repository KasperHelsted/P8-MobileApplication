package p8project.sw801.ui.SmartDevice.EditSmartDevice;

import android.arch.lifecycle.ViewModelProvider;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.ViewModelProviderFactory;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class EditSmartDeviceActivityModule {
    @Provides
    ViewModelProvider.Factory EditSmartDeviceViewModelProvider(EditSmartDeviceViewModel EditSmartDeviceViewModel) {
        return new ViewModelProviderFactory<>(EditSmartDeviceViewModel);
    }

    @Provides
    EditSmartDeviceViewModel provideSettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new EditSmartDeviceViewModel(dataManager, schedulerProvider);
    }
}
