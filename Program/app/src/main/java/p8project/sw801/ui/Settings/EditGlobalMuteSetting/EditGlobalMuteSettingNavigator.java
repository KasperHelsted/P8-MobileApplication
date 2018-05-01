package p8project.sw801.ui.Settings.EditGlobalMuteSetting;

import p8project.sw801.ui.base.BaseViewModel;

public interface EditGlobalMuteSettingNavigator {

    void handleError(Throwable throwable);

    void showTimePickerDialog(BaseViewModel viewModel);

    void sendNotification(String msg);

    void finish();
}

