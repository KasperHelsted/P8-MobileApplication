package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;


import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class MySmartDeviceFragmentModule {
    @Provides
    MySmartDeviceFragmentViewModel provideMySmartDeviceFragmentViewModel (DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new MySmartDeviceFragmentViewModel(dataManager, schedulerProvider);
    }
}
