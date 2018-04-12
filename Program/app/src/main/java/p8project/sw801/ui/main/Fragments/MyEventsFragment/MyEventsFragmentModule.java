package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class MyEventsFragmentModule {
    @Provides
    MyEventsFragmentViewModel provideMyEventsViewModel (DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new MyEventsFragmentViewModel(dataManager, schedulerProvider);
    }
}

