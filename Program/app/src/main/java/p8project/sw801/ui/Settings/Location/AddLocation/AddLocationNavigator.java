package p8project.sw801.ui.Settings.Location.AddLocation;

import p8project.sw801.data.model.db.PredefinedLocation;

public interface AddLocationNavigator {
    void handleError(Throwable throwable);

    void openCreateMapActivity();
    void submitLocationClick();
    void openLocationActivty(PredefinedLocation predefinedLocation);
}
