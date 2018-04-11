package p8project.sw801.ui.event.notificationorsmartdevice;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class NotificationOrSmartdeviceModule {
    @Provides
    NotificationOrSmartdeviceViewModel provideAboutViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new NotificationOrSmartdeviceViewModel(dataManager, schedulerProvider);
    }
}
