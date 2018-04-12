package p8project.sw801.ui.main.Fragments.HomeFragment;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class HomeFragmentModule {
    @Provides
    HomeFragmentViewModel provideHomeFragmentViewModel (DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new HomeFragmentViewModel(dataManager, schedulerProvider);
    }
}



