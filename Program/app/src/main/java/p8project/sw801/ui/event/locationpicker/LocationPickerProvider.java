package p8project.sw801.ui.event.locationpicker;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class LocationPickerProvider {
    @ContributesAndroidInjector(modules = LocationPickerModule.class)
    abstract LocationPicker provideLocationPickerFactory();
}
