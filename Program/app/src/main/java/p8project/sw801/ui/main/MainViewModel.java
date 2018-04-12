package p8project.sw801.ui.main;

import android.databinding.ObservableField;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

/**
 * Created by Kasper Helsted on 4/4/2018.
 */
public class MainViewModel extends BaseViewModel<MainNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;

    private final ObservableField<String> appVersion = new ObservableField<>();

    private int action = NO_ACTION;

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public int getAction() {
        return action;
    }

    public ObservableField<String> getAppVersion() {
        return appVersion;
    }

    public void updateAppVersion(String version) {
        appVersion.set(version);
    }
}
