package p8project.sw801.ui.event.addevent;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class AddEventModule {
    @Provides
    AddEventViewModel provideLoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddEventViewModel(dataManager, schedulerProvider);
    }
}
