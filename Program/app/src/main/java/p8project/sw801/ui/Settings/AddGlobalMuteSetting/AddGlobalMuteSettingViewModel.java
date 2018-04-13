package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import p8project.sw801.data.DataManager;
import p8project.sw801.ui.base.BaseViewModel;
import p8project.sw801.utils.rx.SchedulerProvider;

public class AddGlobalMuteSettingViewModel extends BaseViewModel<AddGlobalMuteSettingNavigator>  {
        public AddGlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
            super(dataManager, schedulerProvider);
        }
    public void showTimePickerDialog(int i){getNavigator().showTimePickerDialog(i);}

    public void submitGlobalMuteClick(){

    }

    public void submitEventToDatabase(){
        //TODO NEED CORRECT PARAMETERS TO PASS TO DB
    }

}
