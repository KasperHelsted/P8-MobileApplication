package p8project.sw801.ui.Settings.AddGlobalMuteSetting;

import p8project.sw801.ui.base.BaseViewModel;

public interface AddGlobalMuteSettingNavigator {

    void handleError(Throwable throwable);

    void showTimePickerDialog(BaseViewModel viewModel);

    void sendNotification(String msg);

    void finish();
}
