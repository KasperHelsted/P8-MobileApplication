package p8project.sw801.ui.event.locationpicker;

import p8project.sw801.data.model.db.PredefinedLocation;

public interface LocationPickerNavigator {
    void createNewPredefinedLocation();

    void returnPredefinedLocation(PredefinedLocation predefinedLocation);

    void createFromMap();
}
