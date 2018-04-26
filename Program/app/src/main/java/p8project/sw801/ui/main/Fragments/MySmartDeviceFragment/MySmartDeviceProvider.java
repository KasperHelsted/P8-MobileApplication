package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MySmartDeviceProvider {
    @ContributesAndroidInjector(modules = MySmartDeviceModule.class)
    abstract MySmartDeviceFragment provideBlogFragmentFactory();
}
