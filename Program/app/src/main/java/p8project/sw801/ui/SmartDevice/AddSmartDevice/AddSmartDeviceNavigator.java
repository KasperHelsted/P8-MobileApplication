package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import java.util.List;

import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;

public interface AddSmartDeviceNavigator {
    void handleError(Throwable throwable);
    void searchForBridge();
    void setupBridges(List<HueBridge> smartDeviceList);
}
