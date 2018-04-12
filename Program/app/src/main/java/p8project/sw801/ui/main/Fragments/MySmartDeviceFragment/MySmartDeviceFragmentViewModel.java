package p8project.sw801.ui.main.Fragments.MySmartDeviceFragment;


import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class MySmartDeviceFragmentViewModel extends BaseViewModel<MySmartDeviceFragmentNavigator> {
    public MySmartDeviceFragmentViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void addNewSmartDevice(){getNavigator().addNewSmartDevice();}

}