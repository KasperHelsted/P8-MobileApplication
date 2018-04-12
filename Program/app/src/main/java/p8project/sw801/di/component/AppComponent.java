package p8project.sw801.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;
import p8project.sw801.MvvmApp;
import p8project.sw801.di.builder.ActivityBuilder;
import p8project.sw801.di.module.AppModule;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(MvvmApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
