package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.ViewModelProviderFactory;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class MySmartDeviceModule {
    @Provides
    MySmartDeviceViewModel mySmartDeviceViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new MySmartDeviceViewModel(dataManager, schedulerProvider);
    }

    @Provides
    MySmartDeviceAdapter provideMySmartDeviceAdapter() {
        return new MySmartDeviceAdapter(new ArrayList<>());
    }

//    @Provides
//    ViewModelProvider.Factory provideMySmartDeviceViewModel(MySmartDeviceViewModel mySmartDeviceViewModel) {
//        return new ViewModelProviderFactory<>(mySmartDeviceViewModel);
//    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(MySmartDeviceFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
