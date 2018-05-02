package p8project.sw801.ui.custom;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;

import java.util.ArrayList;

public class DayPicker extends BaseObservable {
    public ObservableBoolean monday = new ObservableBoolean(true);
    public ObservableBoolean tuesday = new ObservableBoolean(true);
    public ObservableBoolean wednesday = new ObservableBoolean(true);
    public ObservableBoolean thursday = new ObservableBoolean(true);
    public ObservableBoolean friday = new ObservableBoolean(true);
    public ObservableBoolean saturday = new ObservableBoolean(true);
    public ObservableBoolean sunday = new ObservableBoolean(true);

    public ArrayList<Integer> getDays() {
        ArrayList<Integer> days = new ArrayList<>();

        if (this.sunday.get())
            days.add(1);
        if (this.monday.get())
            days.add(2);
        if (this.tuesday.get())
            days.add(3);
        if (this.wednesday.get())
            days.add(4);
        if (this.thursday.get())
            days.add(5);
        if (this.friday.get())
            days.add(6);
        if (this.saturday.get())
            days.add(7);

        return days;
    }

    public void setDays(ArrayList<Integer> days) {
        sunday.set(days.contains(1));
        monday.set(days.contains(2));
        tuesday.set(days.contains(3));
        wednesday.set(days.contains(4));
        thursday.set(days.contains(5));
        friday.set(days.contains(6));
        saturday.set(days.contains(7));

        notifyChange();
    }
}
