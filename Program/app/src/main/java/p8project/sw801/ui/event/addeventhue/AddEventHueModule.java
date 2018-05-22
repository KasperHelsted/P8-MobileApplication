package p8project.sw801.ui.event.addeventhue;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class AddEventHueModule {
    @Provides
    AddEventHueViewModel provideAddEventHueViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new AddEventHueViewModel(dataManager, schedulerProvider);
    }
}
