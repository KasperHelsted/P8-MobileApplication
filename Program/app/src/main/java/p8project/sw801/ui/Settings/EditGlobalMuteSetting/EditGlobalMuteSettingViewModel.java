package p8project.sw801.ui.Settings.EditGlobalMuteSetting;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class EditGlobalMuteSettingViewModel extends BaseViewModel<EditGlobalMuteSettingNavigator> {

    public final ObservableList<PredefinedLocation> predefinedLocations = new ObservableArrayList<>();
    public final ObservableField<String> globulMuteName = new ObservableField<>("");
    public final ObservableInt predefinedLocation = new ObservableInt(0);
    public final ObservableField<String> comment = new ObservableField<>("");
    public final ObservableInt startTime = new ObservableInt();
    public final ObservableInt endTime = new ObservableInt();
    private GlobalMute globalMute = new GlobalMute();

    public EditGlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    /**
     * Receives a globalmute from an id
     *
     * @param id
     */
    public void loadData(Integer id) {
        getCompositeDisposable().add(
                getDataManager().getGlobalMuteById(id).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(response -> {
                    this.globalMute = response;
                    setUp();
                })
        );
    }

    /**
     * Sets the observable data with the data received from loadData
     */
    private void setUp() {
        globulMuteName.set(globalMute.getName());
        comment.set(globalMute.getNote());

        startTime.set(globalMute.getStartTime().intValue());
        endTime.set(globalMute.getEndTime().intValue());
    }

    /**
     * Submits a globalmute
     */
    public void submitGlobalMuteClick() {
        if (globulMuteName.get() == null || globulMuteName.get().isEmpty()) {
            getNavigator().sendNotification("Name cannot be empty");
            return;
        } else if (startTime.get() == 0 || endTime.get() == 0) {
            getNavigator().sendNotification("Time interval must be set");
            return;
        }

        setIsLoading(true);

        globalMute.setName(globulMuteName.get());
        globalMute.setStartTime((long) startTime.get());
        globalMute.setEndTime((long) endTime.get());
        globalMute.setNote(comment.get());

        getNavigator().sendNotification("Global Mute Updated");
        getCompositeDisposable().add(
                getDataManager().updateGlobalMute(globalMute
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(success -> {
                    getNavigator().finish();
                    setIsLoading(false);
                })
        );
    }

}