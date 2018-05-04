package p8project.sw801.ui.SmartDevice.AddSmartDevice;

import java.util.List;

import p8project.sw801.data.model.db.PredefinedLocation;
import p8project.sw801.data.model.db.SmartDevice;
import p8project.sw801.data.model.db.Smartdevice.Controllers.HueBridge;
import p8project.sw801.data.model.db.Smartdevice.Controllers.NestHub;

public interface AddSmartDeviceNavigator {
    void searchForBridge();
    void setupBridges(List<HueBridge> smartDeviceList);
    void searchForNest(List<NestHub> nestHubs);
    void ChangeToSmartDevice();
}
