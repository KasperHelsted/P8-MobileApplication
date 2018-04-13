package p8project.sw801.ui.Settings.Location.AddLocation;

public interface AddLocationNavigator {
    void handleError(Throwable throwable);

    void openCreateMapActivity();
    void submitLocationClick();
}
