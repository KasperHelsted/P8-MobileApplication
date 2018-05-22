package p8project.sw801.ui.Settings.Location;

import java.util.List;

import p8project.sw801.data.model.db.PredefinedLocation;

public interface LocationNavigator {
    void onLocationClicked(PredefinedLocation predefinedLocation);

    void createLocation();

    void createList(List<PredefinedLocation> predefinedLocationList);
}
