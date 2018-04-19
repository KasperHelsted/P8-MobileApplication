package p8project.sw801.ui.event.addeventhue;

public interface AddEventHueNavigator {
    void handleError(Throwable throwable);
    void turnOn();
    void turnOff();
    void setBrightness();
}
