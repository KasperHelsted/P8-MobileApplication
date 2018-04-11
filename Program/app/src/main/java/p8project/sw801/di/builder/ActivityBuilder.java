package p8project.sw801.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
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
            MainActivityModule.class
            //AboutFragmentProvider.class
    })
    abstract MainActivity bindMainActivity();

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
