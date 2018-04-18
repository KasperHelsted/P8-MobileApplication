package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
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
    public final ObservableInt predefinedLocation = new ObservableInt(0);
    public final ObservableField<String> comment = new ObservableField<>("");

    private Long startTimeLong = 0L;
    private Long endTimeLong = 0L;

    private boolean settingStartTime = false;

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.UK);


    public AddGlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        loadData();
    }

    private void loadData() {
        getCompositeDisposable().add(
                getDataManager().getAllPredefinedLocations().subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(this.predefinedLocations::addAll)
        );
    }


    public void pickStartTime() {
        settingStartTime = true;
        getNavigator().showTimePickerDialog(this);
    }

    public void pickEndTime() {
        settingStartTime = false;
        getNavigator().showTimePickerDialog(this);
    }

    private String timeFormatter(Long l) {
        return timeFormat.format(new Date(l));
    }

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

    public void submitGlobalMuteClick() {
        setIsLoading(true);

        getNavigator().sendNotification("Global Mute Inserted");
        getCompositeDisposable().add(
                getDataManager().insertGlobalMute(
                        new GlobalMute(
                                globulMuteName.get(),
                                startTimeLong,
                                endTimeLong,
                                predefinedLocations.get(predefinedLocation.get()).getId(),
                                comment.get()
                        )
                ).subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(success -> {
                    getNavigator().finish();
                    setIsLoading(false);
                })
        );
    }
}
