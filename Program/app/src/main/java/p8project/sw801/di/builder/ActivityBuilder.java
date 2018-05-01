package p8project.sw801.di.builder;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingActivity;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.AddGlobalMuteSettingModule;
import p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog.TimePickerProvider;
import p8project.sw801.ui.Settings.EditGlobalMuteSetting.EditGlobalMuteSettingActivity;
import p8project.sw801.ui.Settings.EditGlobalMuteSetting.EditGlobalMuteSettingModule;
import p8project.sw801.ui.Settings.GlobalMuteSetting.GlobalMuteSettingActivity;
import p8project.sw801.ui.Settings.GlobalMuteSetting.GlobalMuteSettingModule;
import p8project.sw801.ui.Settings.Location.AddLocation.AddLocationSettingActivity;
import p8project.sw801.ui.Settings.Location.AddLocation.AddLocationSettingModule;
import p8project.sw801.ui.Settings.Location.EditLocation.EditLocationModule;
import p8project.sw801.ui.Settings.Location.EditLocation.EditLocationSettingActivity;
import p8project.sw801.ui.Settings.Location.LocationModule;
import p8project.sw801.ui.Settings.Location.LocationSettingActivity;
import p8project.sw801.ui.Settings.SettingsActivity;
import p8project.sw801.ui.Settings.SettingsActivityModule;
import p8project.sw801.ui.Settings.Shopping.ShoppingSettingActivity;
import p8project.sw801.ui.Settings.Shopping.ShoppingSettingModule;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceActivity;
import p8project.sw801.ui.SmartDevice.AddSmartDevice.AddSmartDeviceActivityModule;
import p8project.sw801.ui.SmartDevice.EditSmartDevice.EditSmartDeviceActivity;
import p8project.sw801.ui.SmartDevice.EditSmartDevice.EditSmartDeviceActivityModule;
import p8project.sw801.ui.event.addevent.AddEvent;
import p8project.sw801.ui.event.addevent.AddEventModule;
import p8project.sw801.ui.event.addeventaccessory.AddEventAccessory;
import p8project.sw801.ui.event.addeventaccessory.AddEventAccessoryModule;
import p8project.sw801.ui.event.addeventhue.AddEventHue;
import p8project.sw801.ui.event.addeventhue.AddEventHueModule;
import p8project.sw801.ui.event.addeventnest.AddEventNest;
import p8project.sw801.ui.event.addeventnest.AddEventNestModule;
import p8project.sw801.ui.event.addeventsmartdevice.AddEventSmartDevice;
import p8project.sw801.ui.event.addeventsmartdevice.AddEventSmartDeviceModule;
import p8project.sw801.ui.event.createeventmap.CreateEventMap;
import p8project.sw801.ui.event.createeventmap.CreateEventMapModule;
import p8project.sw801.ui.event.editevent.EditEvent;
import p8project.sw801.ui.event.editevent.EditEventModule;
import p8project.sw801.ui.event.notificationorsmartdevice.NotificationOrSmartdeviceProvider;
import p8project.sw801.ui.main.Fragments.HomeFragment.HomeFragmentProvider;
import p8project.sw801.ui.main.Fragments.MyEventsFragment.MyEventsFragmentProvider;
import p8project.sw801.ui.main.Fragments.MySmartDeviceFragment.MySmartDeviceProvider;
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
            HomeFragmentProvider.class,
            MyEventsFragmentProvider.class,
            MySmartDeviceProvider.class
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

    @ContributesAndroidInjector(modules = EditLocationModule.class)
    abstract EditLocationSettingActivity bindEditLocationSettingActivity();

    @ContributesAndroidInjector(modules = EditEventModule.class)
    abstract EditEvent bindEditEvent();

    @ContributesAndroidInjector(modules = CreateEventMapModule.class)
    abstract CreateEventMap bindCreateEventMap();

    @ContributesAndroidInjector(modules = AddEventSmartDeviceModule.class)
    abstract AddEventSmartDevice bindAddEventSmartDevice();

    @ContributesAndroidInjector(modules = AddEventAccessoryModule.class)
    abstract AddEventAccessory bindAddEventAccessory();

    @ContributesAndroidInjector(modules = AddEventHueModule.class)
    abstract AddEventHue bindAddEventHue();

    @ContributesAndroidInjector(modules = AddEventNestModule.class)
    abstract AddEventNest bindAddEventNest();

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {
            AddGlobalMuteSettingModule.class,
            TimePickerProvider.class
    })
    abstract AddGlobalMuteSettingActivity bindAddGlobalMuteSettingActivity();

    @ContributesAndroidInjector(modules = {
            EditGlobalMuteSettingModule.class,
            TimePickerProvider.class
    })
    abstract EditGlobalMuteSettingActivity bindEditGlobalMuteSettingActivity();

    @ContributesAndroidInjector(modules = GlobalMuteSettingModule.class)
    abstract GlobalMuteSettingActivity bindGlobalMuteSettingActivity();

    @ContributesAndroidInjector(modules = ShoppingSettingModule.class)
    abstract ShoppingSettingActivity bindShoppingSettingActivity();

    @ContributesAndroidInjector(modules = EditSmartDeviceActivityModule.class)
    abstract EditSmartDeviceActivity bindEditSmartDeviceActivity();

    @ContributesAndroidInjector(modules = AddSmartDeviceActivityModule.class)
    abstract AddSmartDeviceActivity bindAddSmartDeviceActivity();

}
