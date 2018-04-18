package p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class TimePickerModule {
    @Provides
    TimePickerViewModel provideTimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new TimePickerViewModel(dataManager, schedulerProvider);
    }
}

