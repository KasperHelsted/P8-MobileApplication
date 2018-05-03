package p8project.sw801.ui.event.editevent;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class EditEventProvider {
    @ContributesAndroidInjector(modules = EditEventModule.class)
    abstract EditEvent provideEditEventFactory();
}
