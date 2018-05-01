package p8project.sw801.ui.Settings.EditGlobalMuteSetting;

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

public class EditGlobalMuteSettingViewModel extends BaseViewModel<EditGlobalMuteSettingNavigator>  {

    public final ObservableList<PredefinedLocation> predefinedLocations = new ObservableArrayList<>();

    private GlobalMute globalMute = new GlobalMute();

    public final ObservableField<String> globulMuteName = new ObservableField<>("");
    public final ObservableField<String> startTime = new ObservableField<>("");
    public final ObservableField<String> endTime = new ObservableField<>("");
    public final ObservableInt predefinedLocation = new ObservableInt(0);
    public final ObservableField<String> comment = new ObservableField<>("");

    private Long startTimeLong = 0L;
    private Long endTimeLong = 0L;

    private boolean settingStartTime = false;

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.UK);

    public EditGlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        //loadData();

    }
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

    private void setUp(){
        globulMuteName.set(globalMute.getName());
        startTime.set(timeFormatter(globalMute.getStartTime()));
        endTime.set(timeFormatter(globalMute.getEndTime()));
        comment.set(globalMute.getNote());

        startTimeLong = globalMute.getStartTime();
        endTimeLong = globalMute.getEndTime();
    };

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
        if (globulMuteName.get() == null || globulMuteName.get().isEmpty()) {
            getNavigator().sendNotification("Name cannot be empty");
            return;
        } else if (startTimeLong == null || endTimeLong == null) {
            getNavigator().sendNotification("Time interval must be set");
            return;
        }

        setIsLoading(true);

        globalMute.setName(globulMuteName.get());
        globalMute.setStartTime(startTimeLong);
        globalMute.setEndTime(endTimeLong);
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