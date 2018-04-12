package p8project.sw801.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import p8project.sw801.ui.Settings.Location.AddLocation.AddLocationSettingActivity;
import p8project.sw801.ui.Settings.Location.AddLocation.AddLocationSettingModule;
import p8project.sw801.ui.Settings.Location.LocationModule;
import p8project.sw801.ui.Settings.Location.LocationSettingActivity;
import p8project.sw801.ui.Settings.SettingsActivity;
import p8project.sw801.ui.Settings.SettingsActivityModule;
import p8project.sw801.ui.event.addevent.AddEvent;
import p8project.sw801.ui.event.addevent.AddEventModule;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.ui.event.createeventmap.CreateEventMapModule;
import p8project.sw801.ui.event.notificationorsmartdevice.NotificationOrSmartdeviceProvider;
import p8project.sw801.ui.main.MainActivity;
import p8project.sw801.ui.main.MainActivityModule;
import p8project.sw801.ui.splash.SplashActivity;
import p8project.sw801.ui.splash.SplashActivityModule;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
            //SettingsActivityModule.class
            //AboutFragmentProvider.class
    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = SettingsActivityModule.class)
    abstract SettingsActivity bindSettingsActivity();

    @ContributesAndroidInjector(modules = LocationModule.class)
    abstract LocationSettingActivity bindLocationSettingActivity();

    @ContributesAndroidInjector(modules = AddLocationSettingModule.class)
    abstract AddLocationSettingActivity bindAddLocationSettingActivity();

    @ContributesAndroidInjector(modules = {
            AddEventModule.class,
            NotificationOrSmartdeviceProvider.class
    })
    abstract AddEvent bindAddEvent();

    @ContributesAndroidInjector(modules = CreateEventMapModule.class)
    abstract CreateEventMap bindCreateEventMap();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();
}
