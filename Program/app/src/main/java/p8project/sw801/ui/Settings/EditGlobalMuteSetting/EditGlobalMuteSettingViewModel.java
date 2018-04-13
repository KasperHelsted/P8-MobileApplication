package p8project.sw801.ui.Settings.EditGlobalMuteSetting;

        import p8project.sw801.data.DataManager;
        import p8project.sw801.ui.base.BaseViewModel;
        import p8project.sw801.utils.rx.SchedulerProvider;

public class EditGlobalMuteSettingViewModel extends BaseViewModel<EditGlobalMuteSettingNavigator>  {
    public EditGlobalMuteSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
    public void showTimePickerDialog(int i){getNavigator().showTimePickerDialog(i);}

    public void submitGlobalMuteChangeClick(){

    }

    public void submitEventToDatabase(){
        //TODO NEED CORRECT PARAMETERS TO PASS TO DB
    }

}