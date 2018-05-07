package p8project.sw801.ui.event.choosenotificationorsmartdevice;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ChooseNotificationOrSmartDeviceProvider {
    @ContributesAndroidInjector(modules = ChooseNotificationOrSmartDeviceModule.class)
    abstract ChooseNotificationOrSmartDevice provideChooseNotificationOrSmartDeviceFactory();
}
