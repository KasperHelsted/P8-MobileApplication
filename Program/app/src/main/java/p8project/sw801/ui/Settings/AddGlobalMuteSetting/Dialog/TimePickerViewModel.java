package p8project.sw801.ui.Settings.AddGlobalMuteSetting.Dialog;

import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class TimePickerViewModel extends BaseViewModel<TimePickerCallback> {
    protected SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public TimePickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        getNavigator().confirmDialog();
    }

    private Calendar getCalender() {
        return Calendar.getInstance();
    }

    public int currentHour() {
        return getCalender().get(Calendar.HOUR_OF_DAY);
    }

    public int currentMinute() {
        return getCalender().get(Calendar.MINUTE);
    }

    public void setTime(TimePicker view, int hourOfDay, int minute) {
        try {
            Date d = timeFormat.parse(hourOfDay + ":" + minute);

            getNavigator().setTime(d.getTime());
        } catch (ParseException e) {
            getNavigator().setTime(0);
        }
    }
}
