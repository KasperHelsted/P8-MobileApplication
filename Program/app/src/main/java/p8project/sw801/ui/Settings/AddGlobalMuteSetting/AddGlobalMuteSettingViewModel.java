package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;


public class AddGlobalMuteSettingViewModel extends BaseViewModel<AddGlobalMuteSettingNavigator> {
    public final ObservableList<PredefinedLocation> predefinedLocations = new ObservableArrayList<>();

    public final ObservableField<String> globulMuteName = new ObservableField<>("");
    public final ObservableField<String> comment = new ObservableField<>("");

    public ObservableInt startTime = new ObservableInt();
    public ObservableInt endTime = new ObservableInt();

    /**
     * Constructor for the class.
     *
     * @param dataManager       The active instance of the datamanager.
     * @param schedulerProvider The active instance of the schedulerProvider.
     */
    public AddGlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    
    /**
     * Method called to submit the new global mute.
     * Multiple checks are performed to ensure that all required fields are filled.
     */
    public void submitGlobalMuteClick() {
        if (globulMuteName.get() == null || globulMuteName.get().isEmpty()) {
            getNavigator().sendNotification("Name cannot be empty");
            return;
        } else if (startTime.get() == 0 || endTime.get() == 0) {
            getNavigator().sendNotification("Time interval must be set");
            return;
        }

        getNavigator().sendNotification("Global Mute Inserted");

        save(
                globulMuteName.get(),
                (long) startTime.get(),
                (long) endTime.get(),
                comment.get()
        );
    }

    public void save(String name, Long startTime, Long endTime, String comment) {
        setIsLoading(true);

        getNavigator().sendNotification("Global Mute created");

        getCompositeDisposable().add(
                getDataManager().insertGlobalMute(
                        new GlobalMute(
                                name,
                                startTime,
                                endTime,
                                null,
                                comment
                        )).subscribeOn(getSchedulerProvider().io())
                        .subscribe(response -> {
                            getNavigator().finish();
                            setIsLoading(false);
                        }, throwable -> {
                            setIsLoading(false);
                        }));
    }
}
