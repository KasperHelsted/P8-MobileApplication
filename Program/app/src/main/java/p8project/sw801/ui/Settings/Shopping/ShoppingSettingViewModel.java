package p8project.sw801.ui.Settings.Shopping;

import p8project.sw801.data.DataManager;
import p8project.sw801.data.model.db.Shopping;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class ShoppingSettingViewModel extends BaseViewModel<ShoppingSettingNavigator> {
    public ShoppingSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public Shopping s = new Shopping();


}
