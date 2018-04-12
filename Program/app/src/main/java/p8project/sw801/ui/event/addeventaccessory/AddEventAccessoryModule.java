package p8project.sw801.ui.event.addeventaccessory;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class AddEventAccessoryModule {
    @Provides
    AddEventAccessoryViewModel provideAddEventAccessoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddEventAccessoryViewModel(dataManager, schedulerProvider);
    }
}
