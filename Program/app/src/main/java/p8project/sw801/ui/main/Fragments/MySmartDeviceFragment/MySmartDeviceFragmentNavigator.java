package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;

import java.util.ArrayList;

import p8project.sw801.data.model.db.SmartDevice;

public interface MySmartDeviceFragmentNavigator {
    void addNewSmartDevice();
    void updatelist();
    void createListView(ArrayList<SmartDevice> smartDevices);
}
