package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import android.databinding.ObservableField;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;


public class AddGlobalMuteSettingViewModel extends BaseViewModel<AddGlobalMuteSettingNavigator> {
    public final ObservableField<String> globulMuteName = new ObservableField<>("");
    public final ObservableField<String> startTime = new ObservableField<>("");
    public final ObservableField<String> endTime = new ObservableField<>("");
    public final ObservableField<List<String>> tester = new ObservableField<>();
    private Long startTimeLong;
    private Long endTimeLong;
    private boolean settingStartTime = false;

    protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");


    public AddGlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
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
        Log.i("sw801.startTime", startTimeLong.toString());
        Log.i("sw801.endTime", endTimeLong.toString());
    }

    public void submitEventToDatabase() {
        //TODO NEED CORRECT PARAMETERS TO PASS TO DB
    }

}
