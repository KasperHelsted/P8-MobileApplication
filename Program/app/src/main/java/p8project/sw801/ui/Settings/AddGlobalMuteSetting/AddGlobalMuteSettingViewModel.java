package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.GlobalMute;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;


public class AddGlobalMuteSettingViewModel extends BaseViewModel<AddGlobalMuteSettingNavigator> {
    public final ObservableList<PredefinedLocation> predefinedLocations = new ObservableArrayList<>();

    public final ObservableField<String> globulMuteName = new ObservableField<>("");
    public final ObservableField<String> startTime = new ObservableField<>("");
    public final ObservableField<String> endTime = new ObservableField<>("");
    public final ObservableField<String> comment = new ObservableField<>("");

    private Long startTimeLong = 0L;
    private Long endTimeLong = 0L;

    private boolean settingStartTime = false;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.UK);

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
     * Method called when the user clicks on the field for start time.
     * Calls a method in the activity that renders a time picker fragment
     */
    public void pickStartTime() {
        settingStartTime = true;
        getNavigator().showTimePickerDialog(this);
    }

    /**
     * Method called when the user clicks on the field for end time.
     * Calls a method in the activity that renders a time picker fragment
     */
    public void pickEndTime() {
        settingStartTime = false;
        getNavigator().showTimePickerDialog(this);
    }

    /**
     * A method used to format the chosen time to another format used in other parts of the application.
     *
     * @param l The time to be converted.
     * @return The converted time.
     */
    private String timeFormatter(Long l) {
        return timeFormat.format(new Date(l));
    }

    /**
     * Method used when a time picker value is submitted.
     *
     * @param l The time submitted.
     */
    @Override
    public void callbackTimePicker(Long l) {
        if (settingStartTime) {
            startTimeLong = l;
            startTime.set(timeFormatter(l));
        } else {
            endTimeLong = l;
            endTime.set(timeFormatter(l));
        }
    }

    /**
     * Method called to submit the new global mute.
     * Multiple checks are performed to ensure that all required fields are filled.
     */
    public void submitGlobalMuteClick() {
        if (globulMuteName.get() == null || globulMuteName.get().isEmpty()) {
            getNavigator().sendNotification("Name cannot be empty");
            return;
        } else if (startTimeLong == null || endTimeLong == null) {
            getNavigator().sendNotification("Time interval must be set");
            return;
        }

        getNavigator().sendNotification("Global Mute Inserted");

        save(
                globulMuteName.get(),
                startTimeLong,
                endTimeLong,
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
