package p8project.sw801.ui.Settings.Location.EditLocation;

import p8project.sw801.data.model.db.Coordinate;
import p8project.sw801.data.model.db.PredefinedLocation;

public interface EditLocationNavigator {
    void submitEditLocationClick();
    void openCreateMapActivity();
    void renderFields(PredefinedLocation predefinedLocation, Coordinate coordinate);
    void openLocationActivty();
}
