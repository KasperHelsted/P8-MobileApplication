package p8project.sw801.ui.event.createeventmap;


import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class CreateEventMapModule {
    @Provides
    CreateEventMapViewModel provideCreateEventMapViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new CreateEventMapViewModel(dataManager, schedulerProvider);
    }
}
