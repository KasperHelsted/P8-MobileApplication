package p8project.sw801.ui.main.Fragments.MyEventsFragment;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class MyEventsFragmentViewModel extends BaseViewModel<MyEventsFragmentNavigator> {
    public MyEventsFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void addNewEvent(){getNavigator().addNewEvent();}
}