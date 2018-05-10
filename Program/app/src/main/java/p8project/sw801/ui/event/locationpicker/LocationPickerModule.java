package p8project.sw801.ui.event.locationpicker;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class LocationPickerModule {
    @Provides
    LocationPickerViewModel provideLocationPickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new LocationPickerViewModel(dataManager, schedulerProvider);
    }
}
