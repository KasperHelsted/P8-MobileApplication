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
    AddSmartDeviceViewModel provideAddSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddSmartDeviceViewModel(dataManager, schedulerProvider);
    }
}
