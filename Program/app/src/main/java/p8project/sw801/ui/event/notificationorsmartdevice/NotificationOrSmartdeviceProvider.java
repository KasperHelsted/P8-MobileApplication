package p8project.sw801.ui.event.notificationorsmartdevice;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NotificationOrSmartdeviceProvider {
    @ContributesAndroidInjector(modules = NotificationOrSmartdeviceModule.class)
    abstract NotificationOrSmartdevice provideNotificationOrSmartdeviceFactory();
}
