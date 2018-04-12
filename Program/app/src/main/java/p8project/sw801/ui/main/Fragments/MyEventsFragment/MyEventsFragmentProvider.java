package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyEventsFragmentProvider {
    @ContributesAndroidInjector(modules = MyEventsFragmentModule.class)
    abstract MyEventsFragment provideMyEventsFactory();
}