package p8project.sw801.ui.event.addeventsmartdevice;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class AddEventSmartDeviceModule {
    @Provides
    AddEventSmartDeviceViewModel provideAddEventSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddEventSmartDeviceViewModel(dataManager, schedulerProvider);
    }
}
