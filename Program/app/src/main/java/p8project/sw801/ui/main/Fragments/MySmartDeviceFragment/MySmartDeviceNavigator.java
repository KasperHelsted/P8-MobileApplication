package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;

import java.util.List;

import p8project.sw801.data.model.db.SmartDevice;

public interface MySmartDeviceNavigator {
    void handleError(Throwable throwable);

    void updateSmartDevice(List<SmartDevice> mySmartDeviceList);
}
