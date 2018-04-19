package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import java.util.List;

import p8project.sw801.data.model.db.SmartDevice;

public interface AddSmartDeviceNavigator {
    void handleError(Throwable throwable);
    void searchForBridge();
    void setupBridges(List<SmartDevice> smartDeviceList);
}
