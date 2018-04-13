package p8project.sw801.ui.Settings.Location;

public interface LocationNavigator {
    void handleError(Throwable throwable);
    void onLocationClicked();
    void createLocation();
}
