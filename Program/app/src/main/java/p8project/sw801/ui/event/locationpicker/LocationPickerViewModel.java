package p8project.sw801.ui.event.locationpicker;

import android.databinding.ObservableArrayList;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class LocationPickerViewModel extends BaseViewModel<LocationPickerNavigator> {
    public ObservableArrayList predefinedLocations = new ObservableArrayList<String>() {{
        add("No Predefined Location Selected");
    }};
    private List<PredefinedLocation> predefinedLocationList = new ArrayList<>();

    public LocationPickerViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        loadPredefinedLocations();
    }

    private void loadPredefinedLocations() {
        getCompositeDisposable().add(
                getDataManager().getAllPredefinedLocations().subscribeOn(
                        getSchedulerProvider().io()
                ).subscribe(predefinedLocations -> {
                    this.predefinedLocations.addAll(predefinedLocations);
                    this.predefinedLocationList.addAll(predefinedLocations);
                })
        );
    }

    public void createNewPredefinedLocation() {
        getNavigator().createNewPredefinedLocation();
    }

    public void createFromMap() {
        getNavigator().createFromMap();
    }

    public void setPredefinedLocation(AdapterView<?> parent, View v, int position, long id) {
        if (position > 0) {
            getNavigator().returnPredefinedLocation(this.predefinedLocationList.get(position - 1));
        }
    }
}
