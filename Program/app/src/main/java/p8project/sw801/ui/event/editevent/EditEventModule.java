package p8project.sw801.ui.event.editevent;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class EditEventModule {
    @Provides
    EditEventViewModel provideEditEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new EditEventViewModel(dataManager, schedulerProvider);
    }
}
