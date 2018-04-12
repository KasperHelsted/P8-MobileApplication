package p8project.sw801.ui.event.addeventnest;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class AddEventNestModule {
    @Provides
    AddEventNestViewModel provideAddEventNestViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddEventNestViewModel(dataManager, schedulerProvider);
    }
}
