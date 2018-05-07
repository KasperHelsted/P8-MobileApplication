package p8project.sw801.ui.event.choosenotificationorsmartdevice;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class ChooseNotificationOrSmartDeviceModule {
    @Provides
    ChooseNotificationOrSmartDeviceViewModel provideChooseNotificationOrSmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ChooseNotificationOrSmartDeviceViewModel(dataManager, schedulerProvider);
    }
}
