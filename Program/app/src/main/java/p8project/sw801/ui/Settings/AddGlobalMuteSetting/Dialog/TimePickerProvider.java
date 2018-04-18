package p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TimePickerProvider {

    @ContributesAndroidInjector(modules = TimePickerModule.class)
    abstract TimePickerDialog provideTimePickerDialogFactory();
}
