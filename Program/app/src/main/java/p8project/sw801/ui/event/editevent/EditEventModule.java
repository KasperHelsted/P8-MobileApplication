package p8project.sw801.ui.event.editevent;

import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import p8project.sw801.data.DataManager;
import p8project.sw801.ui.event.editevent.triggersList.TriggerListAdapter;
import p8project.sw801.utils.rx.SchedulerProvider;

@Module
public class EditEventModule {
    @Provides
    EditEventViewModel provideEditEventViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new EditEventViewModel(dataManager, schedulerProvider);
    }

    @Provides
    TriggerListAdapter provideTriggerListAdapter() {
        return new TriggerListAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(EditEvent activity) {
        return new LinearLayoutManager(activity);
    }

}
