package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MySmartDeviceFragmentProvider {
    @ContributesAndroidInjector(modules = MySmartDeviceFragmentModule.class)
    abstract MySmartDeviceFragment provideMySmartDeviceFactory();
}
